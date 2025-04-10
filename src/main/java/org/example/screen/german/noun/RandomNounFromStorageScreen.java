package org.example.screen.german.noun;

import org.example.MainFrame;
import org.example.constants.screen.german.GermanScreenConstants;
import org.example.enums.WordType;
import org.example.repository.german.noun.NounRepo;
import org.example.utils.ActionPerformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class RandomNounFromStorageScreen extends NounParent {

    private JButton correctButton;
    private JButton incorrectButton;

    private JTextField nounTextField;
    private JLabel nounEnglishLabel;
    JCheckBox shouldShowEnglishCheckBox;

    List<String> nouns;

    public RandomNounFromStorageScreen(MainFrame frame, int width, int height) {
        super(frame, width, height);

    }

    @Override
    protected void initilizer() {
        super.initilizer();
        nouns = NounRepo.getAllNounFromFile();

    }

    @Override
    protected void materials() {

        nounTextFieldInit();
        nounEnglishLabelInit();
        shouldShowEnglishCheckBoxInit();
        correctButtonInit();
        incorrectButtonInit();


        whenClickButton(0);
    }

    protected void nounTextFieldInit() {
        nounTextField = new JTextField();
        nounTextField.setHorizontalAlignment(SwingConstants.CENTER);
        nounTextField.setEnabled(false);
        nounTextField.setDisabledTextColor(Color.BLACK);
        nounTextField.setBackground(Color.WHITE);
        nounTextField.setFont(nounTextField.getFont().deriveFont(Font.BOLD, 16f)); // 16f = font size
        nounTextField.setBounds(width / 2 - buttonWidth * 2 - buttonMargin / 2, height / 2 - 15, buttonWidth * 4 + buttonMargin, buttonHeight * 2);

        add(nounTextField);
    }

    protected void nounEnglishLabelInit() {
        nounEnglishLabel = new JLabel();
        nounEnglishLabel.setBounds(width / 2 - labelWidth / 2, nounTextField.getY() - buttonHeight - 10,
                labelWidth, buttonHeight);
        add(nounEnglishLabel);
    }

    protected void shouldShowEnglishCheckBoxInit() {
        shouldShowEnglishCheckBox = new JCheckBox("Show Meaning", true);
        shouldShowEnglishCheckBox.setBounds(nounEnglishLabel.getX() + nounEnglishLabel.getWidth() + 20, nounEnglishLabel.getY(), buttonWidth * 2, buttonHeight);
        shouldShowEnglishCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // perform another operation here
                nounEnglishLabel.setVisible(shouldShowEnglishCheckBox.isSelected());
            }
        });
        add(RandomNounFromStorageScreen.this.shouldShowEnglishCheckBox);
    }


    protected void correctButtonInit() {
        correctButton = new JButton("Correct");
        correctButton.setBounds(width / 2 - buttonWidth - buttonMargin, nounTextField.getY() + nounTextField.getHeight() + 10,
                buttonWidth, buttonHeight);

        correctButton.addActionListener(e -> {
            score++;
            whenClickButton(score);
        });

        add(correctButton);
    }

    protected void incorrectButtonInit() {
        incorrectButton = new JButton("Incorrect");
        incorrectButton.setBounds(width / 2 + buttonMargin, nounTextField.getY() + nounTextField.getHeight() + 10,
                buttonWidth, buttonHeight);

        incorrectButton.addActionListener(e -> {
            score = 0;
            whenClickButton(score);
        });

        add(incorrectButton);
    }

    @Override
    protected void whenClickButton(int scoreNumber) {
        if (!nouns.isEmpty()) {
            int randomNum = generateRandomNumber(nouns.size());
            nounTextField.setText(getWordFromCombineWord(nouns.get(randomNum), WordType.NOUN));
            nounEnglishLabel.setText(getWordFromCombineWord(nouns.get(randomNum), WordType.ENGLISH));
            scoreLabel.setText(String.format(scoreLabelTemplate, scoreNumber));
        }
    }

    @Override
    public void setLabelWidth(int labelWidth) {
        super.setLabelWidth(300);
    }

    @Override
    protected ActionPerformer backButtonPathSetter(String path) {
        return new ActionPerformer(frame, GermanScreenConstants.NOUN_PAGE);
    }
}


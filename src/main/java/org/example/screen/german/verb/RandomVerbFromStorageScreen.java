package org.example.screen.german.verb;

import org.example.MainFrame;
import org.example.constants.screen.german.GermanScreenConstants;
import org.example.enums.WordType;
import org.example.repository.german.verb.VerbRepo;
import org.example.utils.ActionPerformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class RandomVerbFromStorageScreen extends VerbParent {

    private JButton correctButton;
    private JButton incorrectButton;

    private JTextField verbTextField;
    private JLabel verbEnglishLabel;
    JCheckBox shouldShowEnglishCheckBox;

    List<String> verbs;

    public RandomVerbFromStorageScreen(MainFrame frame, int width, int height) {
        super(frame, width, height);

    }

    @Override
    protected void initilizer() {
        super.initilizer();
        verbs = VerbRepo.getAllVerbsFromFile();

    }

    @Override
    protected void materials() {

        verbTextFieldInit();
        verbEnglishLabelInit();
        shouldShowEnglishCheckBoxInit();
        correctButtonInit();
        incorrectButtonInit();


        whenClickButton(0);
    }

    protected void verbTextFieldInit() {
        verbTextField = new JTextField();
        verbTextField.setHorizontalAlignment(SwingConstants.CENTER);
        verbTextField.setEnabled(false);
        verbTextField.setDisabledTextColor(Color.BLACK);
        verbTextField.setBackground(Color.WHITE);
        verbTextField.setFont(verbTextField.getFont().deriveFont(Font.BOLD, 16f)); // 16f = font size
        verbTextField.setBounds(width / 2 - buttonWidth * 2 - buttonMargin / 2, height / 2 - 15, buttonWidth * 4 + buttonMargin, buttonHeight * 2);

        add(verbTextField);
    }

    protected void verbEnglishLabelInit() {
        verbEnglishLabel = new JLabel();
        verbEnglishLabel.setBounds(width / 2 - labelWidth / 2, verbTextField.getY() - buttonHeight - 10,
                labelWidth, buttonHeight);
        add(verbEnglishLabel);
    }

    protected void shouldShowEnglishCheckBoxInit() {
        shouldShowEnglishCheckBox = new JCheckBox("Show Meaning", true);
        shouldShowEnglishCheckBox.setBounds(verbEnglishLabel.getX() + verbEnglishLabel.getWidth() + 20, verbEnglishLabel.getY(), buttonWidth * 2, buttonHeight);
        shouldShowEnglishCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // perform another operation here
                verbEnglishLabel.setVisible(shouldShowEnglishCheckBox.isSelected());
            }
        });
        add(RandomVerbFromStorageScreen.this.shouldShowEnglishCheckBox);
    }


    protected void correctButtonInit() {
        correctButton = new JButton("Correct");
        correctButton.setBounds(width / 2 - buttonWidth - buttonMargin, verbTextField.getY() + verbTextField.getHeight() + 10,
                buttonWidth, buttonHeight);

        correctButton.addActionListener(e -> {
            score++;
            whenClickButton(score);
        });

        add(correctButton);
    }

    protected void incorrectButtonInit() {
        incorrectButton = new JButton("Incorrect");
        incorrectButton.setBounds(width / 2 + buttonMargin, verbTextField.getY() + verbTextField.getHeight() + 10,
                buttonWidth, buttonHeight);

        incorrectButton.addActionListener(e -> {
            score = 0;
            whenClickButton(score);
        });

        add(incorrectButton);
    }

    @Override
    protected void whenClickButton(int scoreNumber) {
        if (!verbs.isEmpty()) {
            int randomNum = generateRandomNumber(verbs.size());
            verbTextField.setText(getWordFromCombineWord(verbs.get(randomNum), WordType.VERB));
            verbEnglishLabel.setText(getWordFromCombineWord(verbs.get(randomNum), WordType.ENGLISH));
            scoreLabel.setText(String.format(scoreLabelTemplate, scoreNumber));
        }
    }

    @Override
    protected ActionPerformer backButtonPathSetter(String path) {
        return new ActionPerformer(frame, GermanScreenConstants.VERB_PAGE);
    }
}


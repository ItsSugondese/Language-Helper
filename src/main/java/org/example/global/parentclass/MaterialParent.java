package org.example.global.parentclass;

import lombok.Getter;
import lombok.Setter;
import org.example.MainFrame;
import org.example.constants.DelimiterConstants;
import org.example.enums.WordType;
import org.example.utils.uihelper.CustomPopUp;
import org.example.utils.uihelper.CustomTextFieldDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public class MaterialParent extends GlobalParent {


    protected JLabel scoreLabel;
    protected String scoreLabelTemplate;

    protected JLabel totalWordLabel;
    protected String totalWordLabelTemplate;
    protected int score = 0;
    protected int buttonMargin;
    protected int labelWidth;

    protected JCheckBox shouldRandomizeCheckBox;

    protected JButton correctButton;
    protected JButton incorrectButton;

    protected JTextField randomValueTextField;
    protected JLabel englishLabel;
    protected JCheckBox shouldShowEnglishCheckBox;

    private JComboBox<String> translateFromToDropdown;

    protected int randomNum;

    protected JButton setTargetButton;
    protected int target = 0;

    protected List<String> genericValuesList;


    public MaterialParent(MainFrame frame, int width, int height) {
        super(frame, width, height);
    }


    @Override
    protected void initilizer() {
        setLabelWidth(200);
        random = new Random();
        buttonMargin = 10;
        scoreLabelTemplate = "<html><b>Score: %d</b></html>";
        totalWordLabelTemplate = "<html><b>Total Words: %d</b></html>";
        genericValuesList =  new ArrayList<>();

    }

    @Override
    protected void materials() {
        super.materials();
        translateFromToInit();
        scoreLabelInit();
        totalWordLabelInit();
        setTargetButtonInit();
        randomValueTextFieldInit();
        englishLabelInit();
        shouldShowEnglishCheckBoxInit();
        correctButtonInit();
        incorrectButtonInit();
        shouldRandomizeCheckBoxInit();
    }

    @Override
    protected void conclusion() {
        super.conclusion();
        if(!genericValuesList.isEmpty()){
            setGenericValueOnField();
        }
    }

    protected void translateFromToInit(){
        String[] items = {"German To English", "English To German"};
        translateFromToDropdown = new JComboBox<>(items);
        translateFromToDropdown.setBounds(width / 2 - buttonWidth, backButton.getY() + backButton.getHeight() + 10,
                buttonWidth * 2, buttonHeight);

        translateFromToDropdown.addActionListener(e -> {
            setGenericValueOnField();
        });
        add(translateFromToDropdown);
    }

    protected void scoreLabelInit() {
        scoreLabel = new JLabel();
        setFormattedScoreLabel(score);
        scoreLabel.setBounds(width / 2 - labelWidth / 2, translateFromToDropdown.getY() + translateFromToDropdown.getHeight() + 10,
                labelWidth, buttonHeight);
        add(scoreLabel);
    }

    protected String getWordFromCombineWord(String combineWord, WordType wordType) {
        String[] splitWord = combineWord.split(DelimiterConstants.regexPipSeperator);
        if (wordType == WordType.NOUN || wordType == WordType.VERB || wordType == WordType.RANDOM) {
            return splitWord[0].trim();
        } else if (wordType == WordType.ENGLISH) {
            return splitWord[1].trim();
        }
        return null;
    }

    protected void randomValueTextFieldInit() {
        randomValueTextField = new JTextField();
        randomValueTextField.setHorizontalAlignment(SwingConstants.CENTER);
        randomValueTextField.setEnabled(false);
        randomValueTextField.setDisabledTextColor(Color.BLACK);
        randomValueTextField.setBackground(Color.WHITE);
        randomValueTextField.setFont(randomValueTextField.getFont().deriveFont(Font.BOLD, 16f)); // 16f = font size
        randomValueTextField.setBounds(width / 2 - buttonWidth * 2 - buttonMargin / 2, height / 2 - 15, buttonWidth * 4 + buttonMargin, buttonHeight * 2);

        add(randomValueTextField);
    }

    protected void englishLabelInit() {
        englishLabel = new JLabel();
        englishLabel.setBounds(width / 2 - labelWidth / 2, randomValueTextField.getY() - buttonHeight - 30,
                labelWidth, buttonHeight);
        add(englishLabel);
    }

    protected void shouldShowEnglishCheckBoxInit() {
        shouldShowEnglishCheckBox = new JCheckBox("Show Meaning", true);
        shouldShowEnglishCheckBox.setBounds(englishLabel.getX() + englishLabel.getWidth() + 20, englishLabel.getY(), buttonWidth * 2, buttonHeight);
        shouldShowEnglishCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // perform another operation here
                englishLabel.setVisible(shouldShowEnglishCheckBox.isSelected());
            }
        });
        add(shouldShowEnglishCheckBox);
    }


    protected void correctButtonInit() {
        correctButton = new JButton("Correct");
        correctButton.setBounds(width / 2 - buttonWidth - buttonMargin, randomValueTextField.getY() + randomValueTextField.getHeight() + 10,
                buttonWidth, buttonHeight);

        correctButton.addActionListener(e -> {
            score++;
            whenClickButton(score);

            if(target>0 && score >= target){
                CustomPopUp.showPopUpMessage(frame, "Target Completed");
                setFormattedScoreLabel(0);
            }
        });

        add(correctButton);
    }

    protected void incorrectButtonInit() {
        incorrectButton = new JButton("Incorrect");
        incorrectButton.setBounds(width / 2 + buttonMargin, randomValueTextField.getY() + randomValueTextField.getHeight() + 10,
                buttonWidth, buttonHeight);

        incorrectButton.addActionListener(e -> {
            score = 0;
            whenClickButton(score);
        });

        add(incorrectButton);
    }

    protected void totalWordLabelInit() {
        totalWordLabel = new JLabel();
        setFormattedTotalWordLabel();
        totalWordLabel.setBounds(backButton.getX(), backButton.getY() + backButton.getHeight() + 10,
                labelWidth, buttonHeight);
        add(totalWordLabel);
    }

    protected void setTargetButtonInit() {
        setTargetButton = new JButton(String.valueOf(target));
        setTargetButton.setBounds(scoreLabel.getX() + scoreLabel.getWidth() + 20, scoreLabel.getY(),
                buttonWidth, buttonHeight);
        setTargetButton.addActionListener(e -> {
            String valuesFromDialog = CustomTextFieldDialog.showCustomDialog(frame, "Insert Target you want to set", String.valueOf(target));
            if(valuesFromDialog != null && !valuesFromDialog.isBlank()) {
                setFormattedScoreLabel(0);
                target = Integer.parseInt(valuesFromDialog);
                setTargetButton.setText(valuesFromDialog);
            }

        });
        add(setTargetButton);
    }

    protected void shouldRandomizeCheckBoxInit() {
        shouldRandomizeCheckBox = new JCheckBox("Randomize?", true);
        shouldRandomizeCheckBox.setBounds(width/2 - buttonWidth, correctButton.getY() + correctButton.getHeight() + 10,
                buttonWidth *2, buttonHeight);
        shouldRandomizeCheckBox.addItemListener(e -> {
            // perform another operation here
            onShouldRandomizeChanged(true);
        });
        add(shouldRandomizeCheckBox);
    }

    protected void whenClickButton(int scoreNumber) {
        if (!genericValuesList.isEmpty()) {
            onShouldRandomizeChanged(false);
            setGenericValueOnField();
            setFormattedScoreLabel(scoreNumber);
        }
    }

    protected void setGenericValueOnField(){
        if(translateFromToDropdown.getSelectedIndex() == 0) {
            randomValueTextField.setText(getWordFromCombineWord(genericValuesList.get(randomNum), WordType.RANDOM));
            englishLabel.setText(getWordFromCombineWord(genericValuesList.get(randomNum), WordType.ENGLISH));
        } else if(translateFromToDropdown.getSelectedIndex() == 1) {
            randomValueTextField.setText(getWordFromCombineWord(genericValuesList.get(randomNum), WordType.ENGLISH));
            englishLabel.setText(getWordFromCombineWord(genericValuesList.get(randomNum), WordType.RANDOM));
        }
    }

    protected void setFormattedScoreLabel(int scoreNumber) {
        score = scoreNumber;
        scoreLabel.setText(String.format(scoreLabelTemplate, scoreNumber));
    }

    protected void setFormattedTotalWordLabel() {
        totalWordLabel.setText(String.format(totalWordLabelTemplate, getTillNumberValue()));
    }

    protected void onShouldRandomizeChanged(boolean isToggledRandomize) {
        if(!genericValuesList.isEmpty()) {

            randomNum = shouldRandomizeCheckBox.isSelected() ? generateRandomNumber(getTillNumberValue()) : (!isToggledRandomize? randomNum + 1 : randomNum);

            if (randomNum > getTillNumberValue() - 1) {
                randomNum = 0;
            }
        }
    }



    @Override
    protected int getTillNumberValue() {
        return genericValuesList.size();
    }


}

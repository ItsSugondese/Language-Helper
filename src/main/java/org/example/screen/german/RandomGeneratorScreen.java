package org.example.screen.german;

import org.example.MainFrame;
import org.example.constants.screen.ScreenConstants;
import org.example.enums.WordType;
import org.example.repository.german.noun.NounRepo;
import org.example.utils.ActionPerformer;
import org.example.utils.uihelper.CustomTextDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomGeneratorScreen extends ScoreParent {

    private JButton insertValuesButton;

    private JButton correctButton;
    private JButton incorrectButton;

    private JTextField randomValueTextField;
    private JLabel nounEnglishLabel;
    JCheckBox shouldShowEnglishCheckBox;

    List<String> insertedValues;

    public RandomGeneratorScreen(MainFrame frame, int width, int height) {
        super(frame, width, height);

    }

    @Override
    protected void initilizer() {
        super.initilizer();
        insertedValues =  new ArrayList<>();


    }

    @Override
    protected void materials() {

        insertValuesButtonInit();

        randomValueTextFieldInit();
        randomValueEnglishLabelInit();
        shouldShowEnglishCheckBoxInit();
        correctButtonInit();
        incorrectButtonInit();


        whenClickButton(0);

        whenValuesInInsertValueList();
    }

    protected void insertValuesButtonInit(){
        insertValuesButton = new JButton("Insert Values");
        insertValuesButton.setBounds(width / 2 - buttonWidth - buttonMargin, scoreLabel.getY() + scoreLabel.getHeight() + 10,
                buttonWidth * 2, buttonHeight);

        insertValuesButton.addActionListener(e -> {
            String valuesFromDialog = CustomTextDialog.showCustomDialog(frame, "Insert Text to randomize", mergeStringFromList(insertedValues));
            if(valuesFromDialog != null && !valuesFromDialog.isBlank()) {
                insertedValues = new ArrayList<>(Arrays.asList(valuesFromDialog.split("\n")));
                whenClickButton(0);
            } else {
                insertedValues.clear();
            }

            whenValuesInInsertValueList();
        });

        add(insertValuesButton);
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

    protected void randomValueEnglishLabelInit() {
        nounEnglishLabel = new JLabel();
        nounEnglishLabel.setBounds(width / 2 - labelWidth / 2, randomValueTextField.getY() - buttonHeight - 10,
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
        add(shouldShowEnglishCheckBox);
    }


    protected void correctButtonInit() {
        correctButton = new JButton("Correct");
        correctButton.setBounds(width / 2 - buttonWidth - buttonMargin, randomValueTextField.getY() + randomValueTextField.getHeight() + 10,
                buttonWidth, buttonHeight);

        correctButton.addActionListener(e -> {
            score++;
            whenClickButton(score);
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



    protected void whenValuesInInsertValueList(){
        if (!insertedValues.isEmpty()){
            correctButton.setEnabled(true);
            incorrectButton.setEnabled(true);
        } else {
            correctButton.setEnabled(false);
            incorrectButton.setEnabled(false);
            setFormattedScoreLabel(0);
        }
    }

    protected String mergeStringFromList(List<String> listOfString){
        if(!listOfString.isEmpty()){
            return String.join("\n", listOfString);
        }
        return "";
    }

    @Override
    protected void whenClickButton(int scoreNumber) {
        if (!insertedValues.isEmpty()) {
            int randomNum = generateRandomNumber(insertedValues.size());
            randomValueTextField.setText(getWordFromCombineWord(insertedValues.get(randomNum), WordType.RANDOM));
            nounEnglishLabel.setText(getWordFromCombineWord(insertedValues.get(randomNum), WordType.ENGLISH));
            setFormattedScoreLabel(scoreNumber);
        }
    }



    @Override
    protected ActionPerformer backButtonPathSetter(String path) {
        return new ActionPerformer(frame, ScreenConstants.HOME_PAGE);
    }
}


package org.example.global.parentclass;

import lombok.Getter;
import lombok.Setter;
import org.apache.batik.swing.JSVGCanvas;
import org.example.ApiGateway;
import org.example.MainFrame;
import org.example.constants.DelimiterConstants;
import org.example.constants.filepath.FilePathConstants;
import org.example.constants.properties.PropertiesGetterConstants;
import org.example.enums.LanguageNameEnums;
import org.example.enums.TranslateEnums;
import org.example.enums.WordType;
import org.example.utils.files.FilePathDecider;
import org.example.utils.files.FileSaveUtils;
import org.example.utils.misc.AudioUtils;
import org.example.utils.misc.StringUtils;
import org.example.utils.uihelper.CustomPopUp;
import org.example.utils.uihelper.CustomTextFieldDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
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

    protected JTextField valueTextField;
    protected JLabel englishLabel;
    protected JCheckBox shouldShowEnglishCheckBox;

    private JComboBox<String> translateFromToDropdown;

    protected int randomNum;

    protected JButton setTargetButton;
    protected int target = 0;

    protected List<String> genericValuesList;
    protected JSVGCanvas valueAudioCanvas;
//    protected Screen

    public MaterialParent(MainFrame frame, int width, int height) throws Exception {
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
    protected void materials() throws Exception {
        super.materials();
        translateFromToInit();
        scoreLabelInit();
        totalWordLabelInit();
        setTargetButtonInit();
        valueTextFieldInit();
        valueAudioInit();
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
        String[] items = Arrays.stream(TranslateEnums.values())
                .map(TranslateEnums::getText) // Using method reference
                .toArray(String[]::new);

        translateFromToDropdown = new JComboBox<>(items);
        translateFromToDropdown.setBounds(width / 2 - buttonWidth, backButton.getY() + backButton.getHeight() + 10,
                buttonWidth * 2, buttonHeight);

        translateFromToDropdown.addActionListener(e -> setGenericValueOnField());
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

    protected void valueTextFieldInit() {
        valueTextField = new JTextField();
        valueTextField.setHorizontalAlignment(SwingConstants.CENTER);
        valueTextField.setEnabled(false);
        valueTextField.setDisabledTextColor(Color.BLACK);
        valueTextField.setBackground(Color.WHITE);
        valueTextField.setFont(valueTextField.getFont().deriveFont(Font.BOLD, 16f)); // 16f = font size
        valueTextField.setBounds(width / 2 - buttonWidth * 2 - buttonMargin / 2, height / 2 - 15, buttonWidth * 4 + buttonMargin, buttonHeight * 2);

        add(valueTextField);
    }

    protected void valueAudioInit() throws Exception {
        valueAudioCanvas = new JSVGCanvas();
        valueAudioCanvas.setURI(getSvgUri("volume").toString());

        valueAudioCanvas.setEnabled(false);
        valueAudioCanvas.setFont(valueTextField.getFont().deriveFont(Font.BOLD, 16f)); // 16f = font size
        valueAudioCanvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    LanguageNameEnums language = LanguageNameEnums.getFromTranslateEnums(translateFromToDropdown.getSelectedItem().toString());

                    byte[] audioData = ApiGateway.elevenLabsTextToSpeechAudioBytes(PropertiesGetterConstants.elevenLabsApiKeyGetter(), valueTextField.getText());

//                    FileSaveUtils.saveBytesAsFile(audioData, FilePathDecider.getFilePathByLanguageForAudio(language) + File.separator + "output.mp3");
                    AudioUtils.playAudio(audioData);
                }catch (Exception ex){
                    throw new RuntimeException(ex);
                }

            }
        });
        valueAudioCanvas.setBounds(valueTextField.getX() + valueTextField.getWidth() + 10, valueTextField.getY(), buttonWidth/3, buttonHeight);

        add(valueAudioCanvas);
    }

    protected void englishLabelInit() {
        englishLabel = new JLabel();
        englishLabel.setBounds(width / 2 - labelWidth / 2, valueTextField.getY() - buttonHeight - 30,
                labelWidth, buttonHeight);
        add(englishLabel);
    }

    protected void shouldShowEnglishCheckBoxInit() {
        shouldShowEnglishCheckBox = new JCheckBox("Show Meaning", true);
        shouldShowEnglishCheckBox.setBounds(englishLabel.getX() + englishLabel.getWidth() + 20, englishLabel.getY(), buttonWidth * 2, buttonHeight);
        shouldShowEnglishCheckBox.addItemListener(e -> {
            // perform another operation here
            englishLabel.setVisible(shouldShowEnglishCheckBox.isSelected());
        });
        add(shouldShowEnglishCheckBox);
    }


    protected void correctButtonInit() {
        correctButton = new JButton("Correct");
        correctButton.setBounds(width / 2 - buttonWidth - buttonMargin, valueTextField.getY() + valueTextField.getHeight() + 10,
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
        incorrectButton.setBounds(width / 2 + buttonMargin, valueTextField.getY() + valueTextField.getHeight() + 10,
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
            valueTextField.setText(getWordFromCombineWord(genericValuesList.get(randomNum), WordType.RANDOM));
            englishLabel.setText(getWordFromCombineWord(genericValuesList.get(randomNum), WordType.ENGLISH));
        } else if(translateFromToDropdown.getSelectedIndex() == 1) {
            valueTextField.setText(getWordFromCombineWord(genericValuesList.get(randomNum), WordType.ENGLISH));
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

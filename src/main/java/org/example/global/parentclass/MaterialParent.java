package org.example.global.parentclass;

import lombok.Getter;
import lombok.Setter;
import org.apache.batik.swing.JSVGCanvas;
import org.example.ApiGateway;
import org.example.MainFrame;
import org.example.constants.DelimiterConstants;
import org.example.constants.properties.PropertiesGetterConstants;
import org.example.enums.LanguageNameEnums;
import org.example.enums.TranslateEnums;
import org.example.enums.WordScreenType;
import org.example.utils.VariableHelper;
import org.example.utils.files.FilePathDecider;
import org.example.utils.files.FileUtils;
import org.example.utils.misc.AudioUtils;
import org.example.utils.uihelper.CustomPopUp;
import org.example.utils.uihelper.CustomTextFieldDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
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
    protected JLabel meaningLabel;
    protected JCheckBox shouldShowMeaningCheckBox;

    private JComboBox<String> translateFromToDropdown;

    protected int randomNum;

    protected JButton setTargetButton;
    protected int target = 0;

    protected List<String> genericValuesList;
    protected JSVGCanvas valueAudioCanvas;
    protected JSVGCanvas meaningAudioCanvas;

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
        meaningLabelInit();
        meaningAudioInit();
        shouldShowMeaningCheckBoxInit();
        correctButtonInit();
        incorrectButtonInit();
        shouldRandomizeCheckBoxInit();
    }

    @Override
    protected void conclusion() {
        super.conclusion();
        if(!genericValuesList.isEmpty()){
            setGenericValueOnField();
        } else {
            setAudioCanvasVisibility();
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

    protected String getWordFromCombineWord(String combineWord, WordScreenType wordScreenType) {
        String[] splitWord = combineWord.split(DelimiterConstants.regexPipSeperator);
        if (wordScreenType == WordScreenType.GERMAN_NOUN || wordScreenType == WordScreenType.GERMAN_VERB || wordScreenType == WordScreenType.GERMAN_RANDOM) {
            return splitWord[0].trim();
        } else if (wordScreenType == WordScreenType.ENGLISH) {
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

        valueAudioCanvas.setFont(valueTextField.getFont().deriveFont(Font.BOLD, 16f)); // 16f = font size
        valueAudioCanvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                    try {
                        String wordSearch = valueTextField.getText().trim();
                        WordScreenType fromLangDetails;

                        LanguageNameEnums from = TranslateEnums.getTranslateEnumsFromVal(translateFromToDropdown.getSelectedItem().toString()).getFrom();

                        if(from != LanguageNameEnums.ENGLISH){
                            fromLangDetails = getWordScreenType();
                        } else {
                            fromLangDetails = WordScreenType.valueOf(from.name());
                        }

                        byte[] audioData = null;

                        for (String filePath : FilePathDecider.getAudioFolderByScreen(fromLangDetails)) {
                            audioData = FileUtils.getBytesAsFile(filePath + File.separator + wordSearch.toLowerCase() + ".mp3");
                            if (VariableHelper.isNotEmptyByteArray(audioData))
                                break;
                        }

                        if (!VariableHelper.isNotEmptyByteArray(audioData)) {
                            audioData = ApiGateway.elevenLabsTextToSpeechAudioBytes(PropertiesGetterConstants.elevenLabsApiKeyGetter(), wordSearch);
                            FileUtils.saveBytesAsFile(audioData, fromLangDetails.getAudioPath() + File.separator + wordSearch.toLowerCase() + ".mp3");
                        }

                        AudioUtils.playAudio(audioData);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
            }
        });
        valueAudioCanvas.setBounds(valueTextField.getX() + valueTextField.getWidth() + 10, valueTextField.getY(), buttonWidth/3, buttonHeight);

        add(valueAudioCanvas);
    }

    protected void meaningLabelInit() {
        meaningLabel = new JLabel();
        meaningLabel.setBounds(width / 2 - labelWidth / 2, valueTextField.getY() - buttonHeight - 30,
                labelWidth, buttonHeight);
        add(meaningLabel);
    }

    protected void meaningAudioInit() throws Exception {
        meaningAudioCanvas = new JSVGCanvas();
        meaningAudioCanvas.setURI(getSvgUri("volume").toString());


        meaningAudioCanvas.setFont(valueTextField.getFont().deriveFont(Font.BOLD, 16f)); // 16f = font size
        meaningAudioCanvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    try {
                        String wordSearch = meaningLabel.getText().trim();

                        WordScreenType toLangDetails;

                        LanguageNameEnums to = TranslateEnums.getTranslateEnumsFromVal(translateFromToDropdown.getSelectedItem().toString()).getTo();

                        if(to != LanguageNameEnums.ENGLISH){
                            toLangDetails = getWordScreenType();
                        } else {
                            toLangDetails = WordScreenType.valueOf(to.name());
                        }

                        byte[] audioData = null;

                        for (String filePath : FilePathDecider.getAudioFolderByScreen(toLangDetails)) {
                            audioData = FileUtils.getBytesAsFile(filePath + File.separator + wordSearch.toLowerCase() + ".mp3");
                            if (VariableHelper.isNotEmptyByteArray(audioData))
                                break;
                        }

                        if (!VariableHelper.isNotEmptyByteArray(audioData)) {
                            audioData = ApiGateway.elevenLabsTextToSpeechAudioBytes(PropertiesGetterConstants.elevenLabsApiKeyGetter(), wordSearch);
                            FileUtils.saveBytesAsFile(audioData, toLangDetails.getAudioPath() + File.separator + wordSearch.toLowerCase() + ".mp3");
                        }

                        AudioUtils.playAudio(audioData);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }


            }
        });
        meaningAudioCanvas.setBounds(meaningLabel.getX() + meaningLabel.getWidth() + 20, meaningLabel.getY(), buttonWidth/3, buttonHeight);

        add(meaningAudioCanvas);
    }

    protected void shouldShowMeaningCheckBoxInit() {
        shouldShowMeaningCheckBox = new JCheckBox("Show Meaning", true);
        shouldShowMeaningCheckBox.setBounds(meaningAudioCanvas.getX() + meaningAudioCanvas.getWidth() + 20, meaningAudioCanvas.getY(), buttonWidth * 2, buttonHeight);
        shouldShowMeaningCheckBox.addItemListener(e -> {
            // perform another operation here
            meaningLabel.setVisible(shouldShowMeaningCheckBox.isSelected());
            meaningAudioCanvas.setVisible(shouldShowMeaningCheckBox.isSelected() && !meaningLabel.getText().isBlank());
        });
        add(shouldShowMeaningCheckBox);
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
        } else {
            valueTextField.setText("");
            meaningLabel.setText("");
            setAudioCanvasVisibility();
        }
    }

    protected void setGenericValueOnField(){
        if(translateFromToDropdown.getSelectedIndex() == 0) {
            valueTextField.setText(getWordFromCombineWord(genericValuesList.get(randomNum), getWordScreenType()));
            meaningLabel.setText(getWordFromCombineWord(genericValuesList.get(randomNum), WordScreenType.ENGLISH));
        } else if(translateFromToDropdown.getSelectedIndex() == 1) {
            valueTextField.setText(getWordFromCombineWord(genericValuesList.get(randomNum), WordScreenType.ENGLISH));
            meaningLabel.setText(getWordFromCombineWord(genericValuesList.get(randomNum), getWordScreenType()));
        }

        setAudioCanvasVisibility();
    }

    protected void setAudioCanvasVisibility(){
        valueAudioCanvas.setVisible(
                valueTextField.getText() != null && !valueTextField.getText().isBlank()
        );

        meaningAudioCanvas.setVisible(
                meaningLabel.getText() != null && !meaningLabel.getText().isBlank()
        );
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

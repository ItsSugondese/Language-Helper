package org.example.screen.german;

import org.example.ApiGateway;
import org.example.MainFrame;
import org.example.constants.properties.PropertiesGetterConstants;
import org.example.constants.screen.ScreenConstants;
import org.example.enums.LanguageNameEnums;
import org.example.enums.WordScreenType;
import org.example.global.parentclass.MaterialParent;
import org.example.global.parentinterface.Refreshable;
import org.example.repository.german.generic.GenericRepo;
import org.example.utils.ActionPerformer;
import org.example.utils.VariableHelper;
import org.example.utils.files.FileUtils;
import org.example.utils.uihelper.CustomPopUp;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.io.File;
import java.util.List;

public class GermanMiscScreen extends MaterialParent implements Refreshable {
    private JComboBox<String> miscItemsFromPathDropdown;
    private JButton audioLoaderButton;

    public GermanMiscScreen(MainFrame frame, int width, int height) throws Exception {
        super(frame, width, height);
    }

    @Override
    protected ActionPerformer backButtonPathSetter(String path) {
        return new ActionPerformer(frame, ScreenConstants.GERMAN_HOME_PAGE);
    }

    @Override
    protected void materials() throws Exception {
        super.materials();
        miscItemsFromPathDropdownInit();
        audioLoaderButtonInit();
    }

    protected void miscItemsFromPathDropdownInit() {

        miscItemsFromPathDropdown = new JComboBox<>();
        setValuesInMiscItems();

        miscItemsFromPathDropdown.setBounds(translateFromToDropdown.getX(), scoreLabel.getY() + scoreLabel.getHeight() + 10,
                buttonWidth * 2, buttonHeight);


        miscItemsFromPathDropdown.addActionListener(e -> {
            if (miscItemsFromPathDropdown.getSelectedItem() != null) {
                genericValuesList = GenericRepo.getAllFromFileAsList(getSelectedDropDownPath());
                whenValuesInInsertValueList();
            }
        });
        add(miscItemsFromPathDropdown);
    }

    protected void audioLoaderButtonInit() {
        audioLoaderButton = new JButton("Load Audio");
        audioLoaderButton.setBounds(miscItemsFromPathDropdown.getX() + buttonWidth / 2, miscItemsFromPathDropdown.getY() + miscItemsFromPathDropdown.getHeight() + 10,
                buttonWidth, buttonHeight);

        audioLoaderButton.setEnabled(!genericValuesList.isEmpty());
        audioLoaderButton.addActionListener(actionEvent -> {
            audioLoaderButton.setEnabled(false);

            List<String> allValues = GenericRepo.getAllFromFileAsList(getSelectedDropDownPath());
            allValues.forEach(
                    e -> {
                        try {
                            saveAudio(LanguageNameEnums.GERMAN, getWordFromCombineWord(e, getWordScreenType()));
                        } catch (Exception ex) {
                            audioLoaderButton.setEnabled(true);
                            throw new RuntimeException(ex);
                        }
                    }
            );


            CustomPopUp.showPopUpMessage(frame, "Audio Loaded Successfully");

            audioLoaderButton.setEnabled(true);
        });

        add(audioLoaderButton);
    }

    @Override
    protected byte[] saveAudio(LanguageNameEnums languageNameEnums, String wordSearch) throws Exception {
        WordScreenType langScreenDetails;
        String audioPath;
        if (languageNameEnums != LanguageNameEnums.ENGLISH) {
            langScreenDetails = getWordScreenType();
            audioPath = langScreenDetails.getAudioPath() + File.separator + String.valueOf(miscItemsFromPathDropdown.getSelectedItem()).toLowerCase();
            FileUtils.createDirectoryIfNotExists(audioPath);
        } else {
            langScreenDetails = WordScreenType.valueOf(languageNameEnums.name());
            audioPath = langScreenDetails.getAudioPath();
        }

        byte[] audioData = null;


        audioData = FileUtils.getBytesAsFile(audioPath + File.separator + wordSearch.toLowerCase() + ".mp3");


        if (!VariableHelper.isNotEmptyByteArray(audioData)) {
            audioData = ApiGateway.elevenLabsTextToSpeechAudioBytes(PropertiesGetterConstants.elevenLabsApiKeyGetter(), wordSearch);
            FileUtils.saveBytesAsFile(audioData, audioPath + File.separator + wordSearch.toLowerCase() + ".mp3");
        }
        return audioData;
    }

    @Override
    protected WordScreenType getWordScreenType() {
        return WordScreenType.GERMAN_MISC;
    }

    @Override
    public void refresh() {
        genericValuesList.clear();
        setValuesInMiscItems();
        whenValuesInInsertValueList();
    }

    protected void setValuesInMiscItems() {
        String[] items = FileUtils.getAllFileNamesFromFolder(WordScreenType.GERMAN_MISC.getWordPath())
                .toArray(new String[0]);
        miscItemsFromPathDropdown.setModel(new DefaultComboBoxModel(items));
        miscItemsFromPathDropdown.setSelectedItem(null);
        AutoCompleteDecorator.decorate(miscItemsFromPathDropdown);
    }

    @Override
    protected void whenValuesInInsertValueList() {
        correctButton.setEnabled(!genericValuesList.isEmpty());
        incorrectButton.setEnabled(!genericValuesList.isEmpty());

        audioLoaderButton.setEnabled(!genericValuesList.isEmpty());
        setFormattedTotalWordLabel();
        whenClickCorrectIncorrectButton(0);
    }

    protected String getSelectedDropDownPath() {
        return WordScreenType.GERMAN_MISC.getWordPath() + File.separator + miscItemsFromPathDropdown.getSelectedItem();
    }
}

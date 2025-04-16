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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GermanAllWordScreen extends MaterialParent implements Refreshable {
    protected JComboBox<String> allItemsFromPathDropdown;
    protected JButton audioLoaderButton;
    protected List<String> itemsList;


    public GermanAllWordScreen(MainFrame frame, int width, int height) throws Exception {
        super(frame, width, height);
    }

    @Override
    protected void initilizer() {
        super.initilizer();
        itemsList = new ArrayList<>();
    }

    @Override
    protected void materials() throws Exception {
        super.materials();
        allItemsFromPathDropdownInit();
        audioLoaderButtonInit();
    }

    protected void allItemsFromPathDropdownInit() {

        allItemsFromPathDropdown = new JComboBox<>();
        setValuesInMiscItems();

        allItemsFromPathDropdown.setBounds(translateFromToDropdown.getX(), scoreLabel.getY() + scoreLabel.getHeight() + 10,
                buttonWidth * 2, buttonHeight);


        allItemsFromPathDropdown.addActionListener(e -> {
            if (allItemsFromPathDropdown.getSelectedItem() != null && itemsList.contains(allItemsFromPathDropdown.getSelectedItem().toString())) {
                genericValuesList = GenericRepo.getAllFromFileAsList(getSelectedDropDownPath());
                whenValuesInInsertValueList();
            }
        });
        add(allItemsFromPathDropdown);
    }

    protected void audioLoaderButtonInit() {
        audioLoaderButton = new JButton("Load Audio");
        audioLoaderButton.setBounds(allItemsFromPathDropdown.getX() + buttonWidth / 2, allItemsFromPathDropdown.getY() + allItemsFromPathDropdown.getHeight() + 10,
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
            audioPath = langScreenDetails.getAudioPath() + File.separator + String.valueOf(allItemsFromPathDropdown.getSelectedItem()).toLowerCase();
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


    protected void setValuesInMiscItems() {
        String[] items = FileUtils.getAllFileNamesFromFolder(WordScreenType.GERMAN_ALL_WORD.getWordPath())
                .toArray(new String[0]);
        itemsList = Arrays.asList(items);
        allItemsFromPathDropdown.setModel(new DefaultComboBoxModel<>(items));
        allItemsFromPathDropdown.setSelectedItem(null);
        AutoCompleteDecorator.decorate(allItemsFromPathDropdown);
    }

    protected String getSelectedDropDownPath() {
        return WordScreenType.GERMAN_ALL_WORD.getWordPath() + File.separator + allItemsFromPathDropdown.getSelectedItem();
    }

    @Override
    public void refresh() {
        genericValuesList.clear();
        setValuesInMiscItems();
        whenValuesInInsertValueList();
    }

    @Override
    protected void whenValuesInInsertValueList() {
        correctButton.setEnabled(!genericValuesList.isEmpty());
        incorrectButton.setEnabled(!genericValuesList.isEmpty());

        audioLoaderButton.setEnabled(!genericValuesList.isEmpty());
        setFormattedTotalWordLabel();
        whenClickCorrectIncorrectButton(0);
    }


    @Override
    protected ActionPerformer backButtonPathSetter(String path) {
        return new ActionPerformer(frame, ScreenConstants.GERMAN_HOME_PAGE);
    }

    @Override
    protected WordScreenType getWordScreenType() {
        return WordScreenType.GERMAN_ALL_WORD;
    }
}

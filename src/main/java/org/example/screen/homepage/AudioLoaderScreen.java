package org.example.screen.homepage;

import org.example.ApiGateway;
import org.example.MainFrame;
import org.example.constants.filepath.german.GermanFilePathConstants;
import org.example.constants.properties.PropertiesGetterConstants;
import org.example.constants.screen.ScreenConstants;
import org.example.enums.WordScreenType;
import org.example.global.parentclass.GlobalParent;
import org.example.repository.german.generic.GenericRepo;
import org.example.utils.ActionPerformer;
import org.example.utils.VariableHelper;
import org.example.utils.files.FilePathDecider;
import org.example.utils.files.FileUtils;
import org.example.utils.uihelper.CustomPopUp;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AudioLoaderScreen extends GlobalParent {

    protected JComboBox<String> languageTypeDropdown;
    protected JButton generateButton;


    public AudioLoaderScreen(MainFrame frame, int width, int height) throws Exception {
        super(frame, width, height);
    }

    @Override
    protected void materials() throws Exception {
        super.materials();
        languageTypeDropdownInit();
        generateButtonInit();
    }

    protected void languageTypeDropdownInit() {
        String[] items = Arrays.stream(WordScreenType.values())
                .map(WordScreenType::getText) // Using method reference
                .toArray(String[]::new);

        languageTypeDropdown = new JComboBox<>(items);
        languageTypeDropdown.setBounds(width / 2 - buttonWidth, backButton.getY() + backButton.getHeight() + 10,
                buttonWidth * 2, buttonHeight);

        add(languageTypeDropdown);
    }

    protected void generateButtonInit() {
        generateButton = new JButton(startHtml + "Generate" + endHtml);
        generateButton.setBounds(width / 2 - buttonWidth / 2, height / 2 - buttonHeight - 5, buttonWidth * 2, buttonHeight);
        generateButton.addActionListener(actionEvent -> {
            generateButton.setEnabled(false);

            WordScreenType wordScreenType = WordScreenType.getWordScreenTypeFromVal(String.valueOf(languageTypeDropdown.getSelectedItem()));
            JFileChooser fileChooser = new JFileChooser(GermanFilePathConstants.SAMPLE_FOLDER_PATH);
            fileChooser.setAcceptAllFileFilterUsed(false); // Disable "All files"
            fileChooser.setMultiSelectionEnabled(true); // ✅ Enable multiple selection

            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File[] selectedFiles = fileChooser.getSelectedFiles(); // ✅ Get selected files

                List<String> allValues = new ArrayList<>();
                for (File selectedFile : selectedFiles) {
                    allValues.addAll(GenericRepo.getAllFromFileAsList(selectedFile.getPath()));
                }


                allValues.forEach(
                        e -> {
                            try {
                                String value = getWordFromCombineWord(e, wordScreenType);

                                if (value != null) {
                                    byte[] audioData = null;
                                    String lowerCaseValue = value.toLowerCase();

                                    for (String filePath : FilePathDecider.getAudioFolderByScreen(wordScreenType)) {
                                        audioData = FileUtils.getBytesAsFile(filePath + File.separator + lowerCaseValue + ".mp3");
                                        if (VariableHelper.isNotEmptyByteArray(audioData))
                                            break;
                                    }

                                    if (!VariableHelper.isNotEmptyByteArray(audioData)) {
                                        audioData = ApiGateway.elevenLabsTextToSpeechAudioBytes(PropertiesGetterConstants.elevenLabsApiKeyGetter(), value);
                                        FileUtils.saveBytesAsFile(audioData, wordScreenType.getAudioPath() + File.separator + lowerCaseValue + ".mp3");
                                    }
                                }
                            } catch (Exception ex) {
                                generateButton.setEnabled(true);
                                throw new RuntimeException(ex);
                            }
                        }
                );
            }

            CustomPopUp.showPopUpMessage(frame, "Audio Loaded Successfully");

            generateButton.setEnabled(true);
        });
        add(generateButton);
    }

    @Override
    protected ActionPerformer backButtonPathSetter(String path) {
        return new ActionPerformer(frame, ScreenConstants.HOME_PAGE);
    }
}

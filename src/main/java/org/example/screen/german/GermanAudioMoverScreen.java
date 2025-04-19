package org.example.screen.german;

import org.example.ApiGateway;
import org.example.MainFrame;
import org.example.constants.DelimiterConstants;
import org.example.constants.properties.PropertiesGetterConstants;
import org.example.constants.screen.ScreenConstants;
import org.example.enums.GetType;
import org.example.enums.LanguageNameEnums;
import org.example.enums.WordScreenType;
import org.example.global.parentclass.TotalWordsParent;
import org.example.global.parentinterface.Refreshable;
import org.example.repository.german.generic.GenericRepo;
import org.example.utils.ActionPerformer;
import org.example.utils.VariableHelper;
import org.example.utils.files.FileUtils;
import org.example.utils.files.FolderUtils;
import org.example.utils.misc.StringUtils;
import org.example.utils.uihelper.CustomPopUp;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class GermanAudioMoverScreen extends TotalWordsParent implements Refreshable {
    protected JComboBox<String> allItemsFromPathDropdown;
    protected JTextArea textArea;
    protected JButton audioLoaderButton;
    protected JButton matchNameButton;
    protected List<String> itemsList;
    protected JScrollPane scrollPane;
    protected Map<String, String> genericValueMap;

    protected boolean isClickedMatch;

    public GermanAudioMoverScreen(MainFrame frame, int width, int height) throws Exception {
        super(frame, width, height);
    }

    @Override
    protected void initilizer() {
        super.initilizer();
        itemsList = new ArrayList<>();
        genericValueMap = new HashMap<>();
    }

    @Override
    protected void materials() throws Exception {
        super.materials();
        allItemsFromPathDropdownInit();
        audioLoaderButtonInit();
        matchNameButtonInit();
        textAreaInit();
    }

    protected void allItemsFromPathDropdownInit() {

        allItemsFromPathDropdown = new JComboBox<>();
        setValuesInMiscItems();

        allItemsFromPathDropdown.setBounds(width / 2 - buttonWidth, backButton.getY() + backButton.getHeight() + 10,
                buttonWidth * 2, buttonHeight);


        allItemsFromPathDropdown.addActionListener(actionEvent -> {
            if (allItemsFromPathDropdown.getSelectedItem() != null && itemsList.contains(allItemsFromPathDropdown.getSelectedItem().toString())) {

                genericValueMap.clear();
                textArea.setText("");
                List<String> subDirectories = FolderUtils.getSubdirectoriesPath(WordScreenType.GERMAN_ALL_WORD.getAudioPath());

                subDirectories.parallelStream().forEach(
                        e -> {
                            if (FolderUtils.getLastPathSegment(e) != null && !FolderUtils.getLastPathSegment(e).equalsIgnoreCase(StringUtils.convertStringFromObject(allItemsFromPathDropdown.getSelectedItem())))
                                genericValueMap.putAll(FileUtils.getAllFileNamesAndPathFromFolder(e, true));
                        }
                );

                List<String> values = genericValueMap.entrySet()
                        .parallelStream()
                        .map(entry -> entry.getValue().trim())
                        .collect(Collectors.toList());

                SwingUtilities.invokeLater(() -> values.forEach(value -> textArea.append(value + "\n")));

                setFormattedTotalWordLabel();
                audioLoaderButton.setEnabled(false);
                matchNameButton.setEnabled(true);
            }
        });
        add(allItemsFromPathDropdown);
    }


    protected void audioLoaderButtonInit() {
        audioLoaderButton = new JButton("Load Audio");
        audioLoaderButton.setBounds(width / 2 - buttonWidth - 10, allItemsFromPathDropdown.getY() + allItemsFromPathDropdown.getHeight() + 10,
                buttonWidth, buttonHeight);

        audioLoaderButton.setEnabled(isClickedMatch);
        audioLoaderButton.addActionListener(actionEvent -> {
            audioLoaderButton.setEnabled(isClickedMatch);

            genericValueMap.forEach((key, value) -> {
                String folderName = FolderUtils.getLastDirectoryName(key);

                if (!folderName.equalsIgnoreCase("random")) {


                    File file = FileUtils.findFileCaseInsensitive(WordScreenType.GERMAN_ALL_WORD.getWordPath() + File.separator + folderName);

                    if (file != null) {
                        List<String> dataInPath = GenericRepo.getAllFromFileAsList(file.getPath());
                        List<String> newValueToInsert = dataInPath.stream().filter(valueInPath -> !genericValueMap.containsValue(getWordFromCombineWord(valueInPath, WordScreenType.GERMAN_RANDOM).toLowerCase())).collect(Collectors.toUnmodifiableList());

                        if (dataInPath.size() != newValueToInsert.size()) {
                            String valueToOverride = StringUtils.mergeStringFromList(newValueToInsert, DelimiterConstants.lineBreak);
                            FileUtils.writeStringToFile(valueToOverride, file.getPath());
                        }
                    }
                }
                String destination = getSelectedDropDownAudioPath() + File.separator + value + ".mp3";
                FileUtils.moveFile(key, destination);

            });
            CustomPopUp.showPopUpMessage(frame, "Loaded Successfully");
        });

        add(audioLoaderButton);
    }

    protected void matchNameButtonInit() {
        matchNameButton = new JButton("Match String");
        matchNameButton.setBounds(width / 2 + 10, audioLoaderButton.getY(),
                buttonWidth, buttonHeight);

        matchNameButton.setEnabled(false);
        matchNameButton.addActionListener(actionEvent -> {
            matchNameButton.setEnabled(false);

            textArea.setText("");

            List<String> selectedItemFileValues = GenericRepo.getAllFromFileAsList(getSelectedDropDownWordPath()).stream()
                    .map(e -> getWordFromCombineWord(e.trim(), WordScreenType.GERMAN_RANDOM)).collect(Collectors.toList());


            Set<String> lowerCaseSelectedValues = selectedItemFileValues.stream()
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());

            List<String> matchString = new ArrayList<>();

            Map<String, String> filteredMap = genericValueMap.entrySet().stream()
                    .filter(entry -> {
                        String word = getWordFromCombineWord(entry.getValue(), WordScreenType.GERMAN_RANDOM);
                        boolean isMatch = lowerCaseSelectedValues.contains(word.toLowerCase());
                        if (isMatch) {
                            matchString.add(word + " : " + FolderUtils.getLastDirectoryName(entry.getKey()));  // collect matched word
                        }
                        return isMatch;
                    })
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


            matchString.forEach(value -> textArea.append(value + "\n"));

            genericValueMap = filteredMap;
            setFormattedTotalWordLabel();
            matchNameButton.setEnabled(true);
            audioLoaderButton.setEnabled(getTillNumberValue() > 0);
        });

        add(matchNameButton);
    }

    protected void textAreaInit() {
        textArea = new JTextArea();
//        textArea.setBounds(10, audioLoaderButton.getY() + audioLoaderButton.getHeight() + 10, width - 20, height - 200);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(true);
        textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(10, audioLoaderButton.getY() + audioLoaderButton.getHeight() + 10, width - 20, height - 200);
        add(scrollPane);
    }


    protected byte[] saveAudio(LanguageNameEnums languageNameEnums, String wordSearch) throws Exception {
        WordScreenType langScreenDetails;
        String audioPath;
        if (languageNameEnums != LanguageNameEnums.ENGLISH) {
            langScreenDetails = getWordScreenType();
            audioPath = langScreenDetails.getAudioPath() + File.separator + String.valueOf(allItemsFromPathDropdown.getSelectedItem()).toLowerCase();
            FolderUtils.createDirectoryIfNotExists(audioPath);
        } else {
            langScreenDetails = WordScreenType.valueOf(languageNameEnums.name());
            audioPath = langScreenDetails.getAudioPath();
        }

        byte[] audioData = null;


        audioData = FileUtils.getBytesAsFile(audioPath + File.separator + wordSearch.toLowerCase() + ".mp3");


        if (!VariableHelper.isNotEmptyByteArray(audioData)) {
            audioData = ApiGateway.elevenLabsTextToSpeechAudioBytes(PropertiesGetterConstants.elevenLabsApiKeyGetter(), wordSearch);
            if (audioData != null)
                FileUtils.saveBytesAsFile(audioData, audioPath + File.separator + wordSearch.toLowerCase() + ".mp3");
        }
        return audioData;
    }


    protected void setValuesInMiscItems() {
        String[] items = FileUtils.getAllFileNamesFromFolder(WordScreenType.GERMAN_ALL_WORD.getWordPath(), GetType.NAME)
                .toArray(new String[0]);
        itemsList = Arrays.asList(items);
        allItemsFromPathDropdown.setModel(new DefaultComboBoxModel<>(items));
        allItemsFromPathDropdown.setSelectedItem(null);
        AutoCompleteDecorator.decorate(allItemsFromPathDropdown);
    }

    protected String getSelectedDropDownWordPath() {
        return WordScreenType.GERMAN_ALL_WORD.getWordPath() + File.separator + allItemsFromPathDropdown.getSelectedItem();
    }

    protected String getSelectedDropDownAudioPath() {
        return WordScreenType.GERMAN_ALL_WORD.getAudioPath() + File.separator + StringUtils.convertStringFromObject(allItemsFromPathDropdown.getSelectedItem()).toLowerCase();
    }

    @Override
    public void refresh() {
//        genericValuesList.clear();
//        setValuesInMiscItems();
//        whenValuesInInsertValueList();
    }


    protected void whenValuesInInsertValueList() {
//        correctButton.setEnabled(!genericValuesList.isEmpty());
//        incorrectButton.setEnabled(!genericValuesList.isEmpty());
//
//        audioLoaderButton.setEnabled(!genericValuesList.isEmpty());
//        setFormattedTotalWordLabel();
//        whenClickCorrectIncorrectButton(0);
//        previousIndex = null;
//        setWordBackCanvasVisibility();
    }


    @Override
    protected List<String> getAudioFolderByScreen(WordScreenType wordScreenType) {
        return super.getAudioFolderByScreen(wordScreenType);
    }

    @Override
    protected ActionPerformer backButtonPathSetter(String path) {
        return super.backButtonPathSetter(ScreenConstants.GERMAN_HOME_PAGE);
    }


    protected WordScreenType getWordScreenType() {
        return WordScreenType.GERMAN_ALL_WORD;
    }

    @Override
    protected int getTillNumberValue() {
        return genericValueMap.size();
    }
}

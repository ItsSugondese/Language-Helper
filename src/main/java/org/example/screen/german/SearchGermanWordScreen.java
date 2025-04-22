package org.example.screen.german;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.TextFilterator;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import org.apache.batik.swing.JSVGCanvas;
import org.example.MainFrame;
import org.example.constants.DelimiterConstants;
import org.example.constants.screen.ScreenConstants;
import org.example.enums.GetType;
import org.example.enums.LanguageNameEnums;
import org.example.enums.TranslateEnums;
import org.example.enums.WordScreenType;
import org.example.global.parentclass.MaterialParent;
import org.example.global.parentinterface.Refreshable;
import org.example.repository.german.generic.GenericRepo;
import org.example.utils.ActionPerformer;
import org.example.utils.files.FileUtils;
import org.example.utils.misc.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SearchGermanWordScreen extends MaterialParent implements Refreshable {
    protected Map<String, String> genericValueGermanMap;
    protected Map<String, String> genericValueEnglishMap;
    protected JButton searchButton;
    protected EventList<String> glazedList;
    protected JComboBox<String> searchDropdown;


    public SearchGermanWordScreen(MainFrame frame, int width, int height) throws Exception {
        super(frame, width, height);
    }

    @Override
    protected void initilizer() {
        glazedList = new BasicEventList<>();
        genericValueGermanMap = new ConcurrentHashMap<>();
        genericValueEnglishMap = new ConcurrentHashMap<>();
        genericValuesList = new ArrayList<>();
        appendValueInCollections();


    }

    @Override
    protected void materials() throws Exception {
        translateFromToDropdownInit();
        meaningLabelInit();
        meaningAudioInit();
        searchDropdownInit();
        valueAudioInit();
        searchButtonInit();
    }

    @Override
    protected void conclusion() {
        setAudioCanvasVisibility();
    }

    protected void appendValueInCollections() {
        List<String> files = FileUtils.getAllFileNamesFromFolder(WordScreenType.GERMAN_ALL_WORD.getWordPath(), GetType.WHOLE_PATH);

        List<String> values = files.parallelStream()
                .flatMap(file -> GenericRepo.getAllFromFileAsList(file).stream())
                .toList();


        values.parallelStream().forEach(
                e -> {
                    String[] splittedWord = e.split(DelimiterConstants.regexPipSeperator);
                    genericValueGermanMap.put(splittedWord[0].trim(), splittedWord[1].trim());
                    genericValueEnglishMap.put(splittedWord[1].trim(), splittedWord[0].trim());
                }
        );

        genericValuesList = genericValueGermanMap.keySet().stream().toList();
    }





    @Override
    protected void meaningLabelInit() {
        meaningLabel = new JLabel();
        meaningLabel.setBounds(width / 2 - buttonWidth * 2, translateFromToDropdown.getY() + translateFromToDropdown.getHeight() + 20, buttonWidth * 4, buttonHeight);
        meaningLabel.setFont(meaningLabel.getFont().deriveFont(Font.BOLD, 14f)); // 16f = font size
        add(meaningLabel);
    }


    protected void searchDropdownInit() {
        searchDropdown = new JComboBox<>();
        searchDropdown.setSelectedItem(null);

        searchDropdown.setFont(searchDropdown.getFont().deriveFont(Font.BOLD, 16f)); // 16f = font size
        searchDropdown.setBounds(width / 2 - buttonWidth * 2 - buttonMargin / 2, meaningLabel.getY() + meaningLabel.getHeight() + 20, buttonWidth * 4 + buttonMargin, buttonHeight * 2);

        SwingUtilities.invokeLater(() -> {
            glazedList.addAll(genericValuesList);

            TextFilterator<String> textFilterator = (baseList, item) -> {
                // Add original and normalized forms of each word
                for (String word : item.split("\\s+")) {
                    baseList.add(word); // original
                    baseList.add(StringUtils.normalizeUmlauts(word)); // normalized
                }

                // Also add full string (original and normalized)
                baseList.add(item);
                baseList.add(StringUtils.normalizeUmlauts(item));
            };


            AutoCompleteSupport.install(searchDropdown, glazedList, textFilterator);
        });


        searchDropdown.addActionListener(e -> {
            String selected = (String) searchDropdown.getSelectedItem();

            if (selected != null) {
                if (translateFromToDropdown.getSelectedIndex() == 0) {
                    meaningLabel.setText(genericValueGermanMap.get(selected));
                } else if (translateFromToDropdown.getSelectedIndex() == 1) {
                    meaningLabel.setText(genericValueEnglishMap.get(selected));
                }
            }

            setAudioCanvasVisibility();
        });


        add(searchDropdown);
    }

    @Override
    protected void valueAudioInit() throws Exception {
        valueAudioCanvas = new JSVGCanvas();
        valueAudioCanvas.setURI(getSvgUri("volume").toString());

        valueAudioCanvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String selected = (String) searchDropdown.getSelectedItem();

                if(selected != null) {
                    try {
                        String wordSearch = selected.trim();

                        LanguageNameEnums from = TranslateEnums.getTranslateEnumsFromVal(translateFromToDropdown.getSelectedItem().toString()).getFrom();

                        audioSaveAndPlay(from, wordSearch);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        valueAudioCanvas.setBounds(searchDropdown.getX() + searchDropdown.getWidth() + 10, searchDropdown.getY() + searchDropdown.getHeight()/2, buttonWidth / 3, buttonHeight);

        add(valueAudioCanvas);
    }

    protected void searchButtonInit() {
        searchButton = new JButton("Search");
        searchButton.setBounds(width / 2 - buttonWidth / 2, searchDropdown.getY() + searchDropdown.getHeight() + 10,
                buttonWidth, buttonHeight);

        searchButton.addActionListener(e -> {

        });

        add(searchButton);
    }


    // Let's assume this is your method that runs when the page is revisited
    protected void refreshDropdown(List<String> newValues) {
        SwingUtilities.invokeLater(() -> {
            // Clear old items
            glazedList.clear();

            // Add new items
            glazedList.addAll(newValues);
        });
    }


    @Override
    protected void setGenericValueOnField() {
        if (getTillNumberValue() > 0) {
            if (translateFromToDropdown.getSelectedIndex() == 0) {
                genericValuesList = genericValueGermanMap.keySet().stream().toList();
            } else if (translateFromToDropdown.getSelectedIndex() == 1) {
                genericValuesList = genericValueEnglishMap.keySet().stream().toList();
            }
            this.refreshDropdown(genericValuesList);
        }


        setAudioCanvasVisibility();
    }

    @Override
    protected byte[] getOrSaveAudio(LanguageNameEnums languageNameEnums, String wordSearch) throws Exception {
        WordScreenType langScreenDetails;
        if (languageNameEnums != LanguageNameEnums.ENGLISH) {
            langScreenDetails = getWordScreenType();
        } else {
            langScreenDetails = WordScreenType.valueOf(languageNameEnums.name());
        }

        byte[] audioData = null;

        audioData = getAudioData(wordSearch, langScreenDetails, audioData);
        return audioData;
    }

    @Override
    protected ActionPerformer backButtonPathSetter(String path) {
        return super.backButtonPathSetter(ScreenConstants.GERMAN_HOME_PAGE);
    }


    @Override
    public void refresh() {
        appendValueInCollections();
        this.refreshDropdown(genericValuesList);
    }

    @Override
    protected WordScreenType getWordScreenType() {
        return WordScreenType.GERMAN_RANDOM;
    }

    @Override
    protected void setAudioCanvasVisibility() {
        String selected = (String) searchDropdown.getSelectedItem();

        valueAudioCanvas.setVisible(
                selected != null
        );

        meaningAudioCanvas.setVisible(
                meaningLabel.getText() != null && !meaningLabel.getText().isBlank()
        );
    }


}

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
import org.example.global.parentclass.QuizOptionsParent;
import org.example.global.parentinterface.Refreshable;
import org.example.repository.german.generic.GenericRepo;
import org.example.utils.ActionPerformer;
import org.example.utils.files.FileUtils;
import org.example.utils.misc.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class GermanQuizScreen extends QuizOptionsParent {
    protected JComboBox<String> translateFromToDropdown;
    protected JLabel questionLabel;
    protected Map<String, String> questionAnswer;

    public GermanQuizScreen(MainFrame frame, int width, int height) throws Exception {
        super(frame, width, height);
    }

    @Override
    protected void initilizer() {
        super.initilizer();
        random = new Random();
        questionAnswer= new HashMap<>();
        appendValueInCollections();
    }

    @Override
    protected void materials() throws Exception {
        super.materials();
        translateFromToDropdownInit();
        questionLabelInit();
    }

    @Override
    protected void conclusion() {
        super.conclusion();
        checkAction(null);
    }

    protected void questionLabelInit(){
        questionLabel = new JLabel();
        questionLabel.setBounds(width/2 - labelWidth, translateFromToDropdown.getY() + translateFromToDropdown.getHeight() + 20, labelWidth * 2, buttonHeight);
        add(questionLabel);
    }

    @Override
    protected ActionPerformer backButtonPathSetter(String path) {
        return super.backButtonPathSetter(ScreenConstants.GERMAN_HOME_PAGE);
    }

    protected void translateFromToDropdownInit() {
        String[] items = Arrays.stream(TranslateEnums.values())
                .map(TranslateEnums::getText) // Using method reference
                .toArray(String[]::new);

        translateFromToDropdown = new JComboBox<>(items);
        translateFromToDropdown.setBounds(width / 2 - buttonWidth, backButton.getY() + backButton.getHeight() + 10,
                buttonWidth * 2, buttonHeight);

        translateFromToDropdown.addActionListener(e -> dropDownExchange());
        add(translateFromToDropdown);
    }

    protected void appendValueInCollections() {
        List<String> files = FileUtils.getAllFileNamesFromFolder(WordScreenType.GERMAN_ALL_WORD.getWordPath(), GetType.WHOLE_PATH);

        genericValuesList = files.parallelStream()
                .flatMap(file -> GenericRepo.getAllFromFileAsList(file).stream())
                .toList();
    }

    protected void dropDownExchange(){

    }

    @Override
    protected void setQuizPanelBounds(){
        mainPanel.setBounds(backButton.getX(), questionLabel.getY() + questionLabel.getHeight() + 20, width - backButton.getX() * 2, height/2);
    }

    @Override
    protected void checkAction(String value){

        if(value != null){
            if(questionAnswer.containsKey(value))
                System.out.println("Correct Answer");
            else
                System.out.println("Incorrect Answer");
        }

        int index = 1;
        int answerIndex = random.nextInt(4) + 1;
        for (Map.Entry<JButton, JLabel> entry : buttonLabelMap.entrySet()) {
            int listRandomIndex = random.nextInt(genericValuesList.size());

            String answerOption = getWordFromCombineWord(genericValuesList.get(listRandomIndex), WordScreenType.ENGLISH);
            JLabel label = entry.getValue();

            label.setText(answerOption);
            if(index == answerIndex){
                questionAnswer.clear();
                questionLabel.setText(getWordFromCombineWord(genericValuesList.get(listRandomIndex), WordScreenType.GERMAN_RANDOM));
                questionAnswer.put(answerOption, questionLabel.getText());
            }
            index ++;
        }
    }
}

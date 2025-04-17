package org.example.global.parentclass;

import lombok.Getter;
import lombok.Setter;
import org.example.MainFrame;
import org.example.constants.DelimiterConstants;
import org.example.constants.filepath.FilePathConstants;
import org.example.constants.screen.ScreenConstants;
import org.example.constants.variables.VariableConstants;
import org.example.enums.LanguageNameEnums;
import org.example.enums.WordScreenType;
import org.example.utils.ActionPerformer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Getter
@Setter
public class GlobalParent extends JPanel {

    //variable for panel dimensions
    protected int width, height;

    //variable for sizing buttons
    protected int buttonWidth, buttonHeight;

    //instance of MainFrame class
    protected MainFrame frame;


    protected JButton backButton;

    //variable for designing using html
    protected String startHtml, endHtml;

    protected Random random;


    public GlobalParent(MainFrame frame, int width, int height) throws Exception {
        setButtonWidth(120);
        setButtonHeight(30);

        this.frame = frame;
        this.width = width;
        this.height = height;

        startHtml = "<html> <style> h2{font-family: \"Comic Sans MS\", \"Comic Sans\", cursive;}span{font-family: fantasy}</style> <center><h2>";
        endHtml = "</h2> </center> </html>";

        //this method contains all the panel properties
        panelFeatures();

        if (haveBackButton()) {
            backButtonInit();
        }

        initilizer();
        materials();
        conclusion();

    }


    protected void backButtonInit() {
        backButton = new JButton(startHtml + "Back" + endHtml);
        backButton.setBounds(VariableConstants.BACK_BUTTON_X, VariableConstants.BACK_BUTTON_Y, VariableConstants.BACK_BUTTON_WIDTH, VariableConstants.BACK_BUTTON_HEIGHT);
        backButton.addActionListener(backButtonPathSetter(null));
        add(backButton);
    }

    // for initizaling value that will be later used in UI element initialization
    protected void initilizer() {
    }

    // for initializing UI elements
    protected void materials() throws Exception {
    }

    // value to initialize after everything mainly UI elements is done
    protected void conclusion() {
    }

    // screen to redirect when back button is clicked (compulsory)
    protected ActionPerformer backButtonPathSetter(String path) {
        return new ActionPerformer(frame, path);
    }

    // for deciding if backButton should be in the screen or not
    protected boolean haveBackButton() {
        return Boolean.TRUE;
    }

    // helper for generating random number
    protected int generateRandomNumber(int till) {
        return random.nextInt(till);
    }

    // for setting size of main List
    protected int getTillNumberValue() {
        return 0;
    }

    // for getting svg icon from assset folder
    protected URI getSvgUri(String svg) throws URISyntaxException {
        return new URI("file:///" + FilePathConstants.ASSETS_FOLDER_ABSOLUTE_PATH + File.separator + svg + ".svg");
    }


    // get One side of the word from seperated string
    protected String getWordFromCombineWord(String combineWord, WordScreenType wordScreenType) {
        String[] splitWord = combineWord.split(DelimiterConstants.regexPipSeperator);
        if (wordScreenType != WordScreenType.ENGLISH) {
            return splitWord[0].trim();
        } else if (wordScreenType == WordScreenType.ENGLISH) {
            return splitWord[1].trim();
        }
        return null;
    }


    // for deciding audio path
    protected List<String> getAudioFolderByScreen(WordScreenType wordScreenType) {
        if(wordScreenType.getLanguageNameEnum() == LanguageNameEnums.ENGLISH) {
            return Collections.singletonList(wordScreenType.getAudioPath());
        } else if(wordScreenType.getLanguageNameEnum() == LanguageNameEnums.GERMAN) {
            if(wordScreenType != WordScreenType.GERMAN_RANDOM){
                return Collections.singletonList(wordScreenType.getAudioPath());
            }

            File[] files = new File(WordScreenType.GERMAN_ALL_WORD.getAudioPath()).listFiles();

            List<String> folderName = new ArrayList<>();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        folderName.add(file.getPath());
                    }
                }
            }
            return folderName;
        }
        return Collections.emptyList();
    }



    void panelFeatures() {
        setSize(new Dimension(width, height));
        setLayout(null);
        setOpaque(false);
    }
}

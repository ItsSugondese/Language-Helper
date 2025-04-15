package org.example.global.parentclass;

import lombok.Getter;
import lombok.Setter;
import org.example.MainFrame;
import org.example.constants.DelimiterConstants;
import org.example.constants.filepath.FilePathConstants;
import org.example.constants.variables.VariableConstants;
import org.example.enums.WordScreenType;
import org.example.utils.ActionPerformer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
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

    protected void initilizer() {
    }

    protected void materials() throws Exception {
    }

    protected void conclusion() {
    }

    protected ActionPerformer backButtonPathSetter(String path) {
        return null;
    }

    protected boolean haveBackButton() {
        return Boolean.TRUE;
    }

    protected int generateRandomNumber(int till) {
        return random.nextInt(till);
    }

    protected int getTillNumberValue() {
        return 0;
    }

    protected URI getSvgUri(String svg) throws URISyntaxException {
        return new URI("file:///" + FilePathConstants.ASSETS_FOLDER_ABSOLUTE_PATH + File.separator + svg + ".svg");
    }

    protected String getWordFromCombineWord(String combineWord, WordScreenType wordScreenType) {
        String[] splitWord = combineWord.split(DelimiterConstants.regexPipSeperator);
        if (wordScreenType != WordScreenType.ENGLISH) {
            return splitWord[0].trim();
        } else if (wordScreenType == WordScreenType.ENGLISH) {
            return splitWord[1].trim();
        }
        return null;
    }



    void panelFeatures() {
        setSize(new Dimension(width, height));
        setLayout(null);
        setOpaque(false);
    }
}

package org.example.screen.homepage;

import org.example.MainFrame;
import org.example.constants.screen.ScreenConstants;
import org.example.constants.screen.german.GermanScreenConstants;
import org.example.global.parentclass.MenuGlobalParent;
import org.example.utils.ActionPerformer;

import javax.swing.*;

public class GermanHomepageScreen extends MenuGlobalParent {

    private JButton allWordsButton;
    private JButton randomGeneratorButton;

    public GermanHomepageScreen(MainFrame frame, int width, int height) throws Exception {
        super(frame, width, height);
        materials();
    }

    @Override
    protected void materials(){
        allWordsButtonInit();
        askLabelInit();
        randomGeneratorButtonInit();
    }

    protected void askLabelInit(){
        asking = new JLabel(startHtml + "<span> Select operations: </span>" + endHtml);
        asking.setBounds(allWordsButton.getX(), allWordsButton.getY()-30, 250, 20);
        add(asking);
    }

    protected void allWordsButtonInit(){
        allWordsButton = new JButton(startHtml + "All Words" + endHtml);
        allWordsButton.setBounds(width/2 - buttonWidth/2, height/2 - buttonHeight *3 , buttonWidth, buttonHeight);
        allWordsButton.addActionListener(new ActionPerformer(frame, GermanScreenConstants.GERMAN_MISC_PAGE));
        add(allWordsButton);
    }

    protected void randomGeneratorButtonInit(){
        randomGeneratorButton = new JButton(startHtml + "Random Generator" + endHtml);
        randomGeneratorButton.setBounds(allWordsButton.getX(), allWordsButton.getY() + allWordsButton.getHeight() + 20 , buttonWidth, buttonHeight);
        randomGeneratorButton.addActionListener(new ActionPerformer(frame, GermanScreenConstants.RANDOM_GENERATOR_PAGE));
        add(randomGeneratorButton);
    }

    @Override
    protected ActionPerformer backButtonPathSetter(String path) {
        return new ActionPerformer(frame, ScreenConstants.HOME_PAGE);
    }
    
}


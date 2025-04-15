package org.example.screen.homepage;

import org.example.MainFrame;
import org.example.constants.screen.ScreenConstants;
import org.example.constants.screen.german.GermanScreenConstants;
import org.example.global.parentclass.MenuGlobalParent;
import org.example.utils.ActionPerformer;

import javax.swing.*;

public class GermanHomepageScreen extends MenuGlobalParent {

    private JButton nounButton;
    private JButton verbButton;
    private JButton wFrageButton;
    private JButton randomGeneratorButton;

    public GermanHomepageScreen(MainFrame frame, int width, int height) throws Exception {
        super(frame, width, height);
        materials();
    }

    @Override
    protected void materials(){
        nounButtonInit();
        askLabelInit();
        verbButtonInit();
        wFrageButtonInit();
        randomGeneratorButtonInit();
    }

    protected void askLabelInit(){
        asking = new JLabel(startHtml + "<span> Select operations: </span>" + endHtml);
        asking.setBounds(nounButton.getX(), nounButton.getY()-30, 250, 20);
        add(asking);
    }

    protected void nounButtonInit(){
        nounButton = new JButton(startHtml + "Noun" + endHtml);
        nounButton.setBounds(width/2 - buttonWidth/2, height/2 - buttonHeight - 5 , buttonWidth, buttonHeight);
        nounButton.addActionListener(new ActionPerformer(frame, GermanScreenConstants.NOUN_PAGE));
        add(nounButton);
    }

    protected void verbButtonInit(){
        verbButton = new JButton(startHtml + "Verb" + endHtml);
        verbButton.setBounds(nounButton.getX(), nounButton.getY() + nounButton.getHeight() + 20 , buttonWidth, buttonHeight);
        verbButton.addActionListener(new ActionPerformer(frame, GermanScreenConstants.VERB_PAGE));
        add(verbButton);
    }

    protected void wFrageButtonInit(){
        wFrageButton = new JButton(startHtml + "W-Frage" + endHtml);
        wFrageButton.setBounds(verbButton.getX(), verbButton.getY() + verbButton.getHeight() + 20 , buttonWidth, buttonHeight);
        wFrageButton.addActionListener(new ActionPerformer(frame, GermanScreenConstants.W_FRAGE_PAGE));
        add(wFrageButton);
    }

    protected void randomGeneratorButtonInit(){
        randomGeneratorButton = new JButton(startHtml + "Random Generator" + endHtml);
        randomGeneratorButton.setBounds(wFrageButton.getX(), wFrageButton.getY() + wFrageButton.getHeight() + 20 , buttonWidth, buttonHeight);
        randomGeneratorButton.addActionListener(new ActionPerformer(frame, GermanScreenConstants.RANDOM_GENERATOR_PAGE));
        add(randomGeneratorButton);
    }

    @Override
    protected ActionPerformer backButtonPathSetter(String path) {
        return new ActionPerformer(frame, ScreenConstants.HOME_PAGE);
    }
    
}


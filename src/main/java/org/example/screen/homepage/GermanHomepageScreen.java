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

    public GermanHomepageScreen(MainFrame frame, int width, int height){
        super(frame, width, height);
        materials();
    }

    @Override
    protected void materials(){
        nounButtonInit();
        askLabelInit();
        verbButtonInit();
    }

    void askLabelInit(){
        asking = new JLabel(startHtml + "<span> Select operations: </span>" + endHtml);
        asking.setBounds(nounButton.getX(), nounButton.getY()-30, 250, 20);
        add(asking);
    }

    void nounButtonInit(){
        nounButton = new JButton(startHtml + "Noun" + endHtml);
        nounButton.setBounds(width/2 - buttonWidth/2, height/2 - buttonHeight - 5 , buttonWidth, buttonHeight);
        nounButton.addActionListener(new ActionPerformer(frame, GermanScreenConstants.NOUN_PAGE));
        add(nounButton);
    }

    void verbButtonInit(){
        verbButton = new JButton(startHtml + "Verb" + endHtml);
        verbButton.setBounds(nounButton.getX(), nounButton.getY() + nounButton.getHeight() + 20 , buttonWidth, buttonHeight);
        verbButton.addActionListener(new ActionPerformer(frame, GermanScreenConstants.VERB_PAGE));
        add(verbButton);
    }

    @Override
    protected ActionPerformer backButtonPathSetter(String path) {
        return new ActionPerformer(frame, ScreenConstants.HOME_PAGE);
    }
}


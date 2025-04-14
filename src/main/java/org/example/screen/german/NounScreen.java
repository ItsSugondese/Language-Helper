package org.example.screen.german;

import org.example.MainFrame;
import org.example.constants.screen.ScreenConstants;
import org.example.constants.screen.german.GermanScreenConstants;
import org.example.global.parentclass.MenuGlobalParent;
import org.example.utils.ActionPerformer;

import javax.swing.*;

public class NounScreen extends MenuGlobalParent {
    private JButton randomFromStorageButton;


    public NounScreen(MainFrame frame, int width, int height) throws Exception {
        super(frame, width, height);
    }

    @Override
    protected void materials(){


        //for golang button declaration, properties and panel adding
        randomFromStorageInit();

        //for asking label declaration, properties and panel adding
        askLabelInit();

        backButtonInit();

    }

    void randomFromStorageInit(){
        randomFromStorageButton = new JButton(startHtml + "Random" + endHtml);
        randomFromStorageButton.setBounds(width/2 - buttonWidth/2, height/2 - buttonHeight - 5 , buttonWidth, buttonHeight);
        randomFromStorageButton.addActionListener(new ActionPerformer(frame, GermanScreenConstants.RANDOM_NOUN_PAGE));
        add(randomFromStorageButton);
    }


    void askLabelInit(){
        asking = new JLabel(startHtml + "<span> Select operations: </span>" + endHtml);
        asking.setBounds(randomFromStorageButton.getX(), randomFromStorageButton.getY()-30, 250, 20);
        add(asking);
    }

    @Override
    protected ActionPerformer backButtonPathSetter(String path) {
        return new ActionPerformer(frame, ScreenConstants.GERMAN_HOME_PAGE);
    }
}


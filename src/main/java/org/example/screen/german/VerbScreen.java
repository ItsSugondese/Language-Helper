package org.example.screen.german;

import org.example.MainFrame;
import org.example.constants.screen.german.GermanScreenConstants;
import org.example.global.parentclass.MenuGlobalParent;
import org.example.global.parentclass.german.GlobalGermanParent;
import org.example.utils.ActionPerformer;

import javax.swing.*;

public class VerbScreen extends MenuGlobalParent {
    private JButton randomFromStorageButton;
    private JButton randomFromInternetButton;


    public VerbScreen(MainFrame frame, int width, int height){
        super(frame, width, height);

        //this method contains all the components like label and buttons declaration
        materials();
    }

    void materials(){


        //for golang button declaration, properties and panel adding
        randomFromStorageInit();
        randomFromInternetInit();

        //for asking label declaration, properties and panel adding
        askLabelInit();

        backButtonInit();

    }

    void randomFromStorageInit(){
        randomFromStorageButton = new JButton(startHtml + "Random" + endHtml);
        randomFromStorageButton.setBounds(width/2 - buttonWidth/2, height/2 - buttonHeight - 5 , buttonWidth, buttonHeight);
        randomFromStorageButton.addActionListener(new ActionPerformer(frame, GermanScreenConstants.RANDOM_VERB_PAGE));
        add(randomFromStorageButton);
    }

    void randomFromInternetInit(){
        randomFromInternetButton = new JButton(startHtml + "Random From Internet" + endHtml);
        randomFromInternetButton.setBounds(randomFromStorageButton.getX(), randomFromStorageButton.getY() + randomFromStorageButton.getHeight() + 10 , buttonWidth, buttonHeight);
//        getButton.addActionListener(new ActionPerformer(frame, ScreenConstants.GET_API_GENERATE_GOLANG));
        add(randomFromInternetButton);
    }

    void askLabelInit(){
        asking = new JLabel(startHtml + "<span> Select operations: </span>" + endHtml);
        asking.setBounds(randomFromStorageButton.getX(), randomFromStorageButton.getY()-30, 250, 20);
        add(asking);
    }
}


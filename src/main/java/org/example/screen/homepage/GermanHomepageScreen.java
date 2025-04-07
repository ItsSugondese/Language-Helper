package org.example.screen.homepage;

import org.example.MainFrame;
import org.example.constants.filepath.german.noun.NounGermanFilePathConstants;
import org.example.constants.variables.VariableConstants;
import org.example.repository.german.projectname.ProjectNameRepo;
import org.example.utils.ActionPerformer;
import org.example.constants.screen.ScreenConstants;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class GermanHomepageScreen extends JPanel {

    //variable for panel dimensions
    private int width, height;

    //variable for sizing buttons
    private int buttonWidth, buttonHeight;

    //instance of MainFrame classMFra
    private MainFrame frame;

    //All the buttons and label
    private JLabel asking;

    private JButton backButton;
    private JButton nounButton;
    private JButton verbButton;

    //variable for designing using html
    private String startHtml, endHtml;

    //instance for event Listener
    private ActionPerformer actionPerformer;

    public GermanHomepageScreen(MainFrame frame, int width, int height){

//        actionPerformer = new ActionPerformer(frame);

        buttonWidth = 200;
        buttonHeight = 60;

        this.frame = frame;
        this.width = width;
        this.height = height;

        startHtml = "<html> <style> h2{font-family: \"Comic Sans MS\", \"Comic Sans\", cursive;}span{font-family: fantasy}</style> <center><h2>";
        endHtml = "</h2> </center> </html>";

        //this method contains all the panel properties
        panelFeatures();

        //this method contains all the components like label and buttons declaration
        materials();
    }

    void materials(){

        backButtonInit();

        //for golang button declaration, properties and panel adding
        nounButtonInit();
        askLabelInit();

        verbButtonInit();

        //for asking label declaration, properties and panel adding


    }

    void backButtonInit(){
        backButton = new JButton(startHtml + "Back" + endHtml);
        backButton.setBounds(VariableConstants.BACK_BUTTON_X, VariableConstants.BACK_BUTTON_Y, VariableConstants.BACK_BUTTON_WIDTH, VariableConstants.BACK_BUTTON_HEIGHT);
        backButton.addActionListener(new ActionPerformer(frame, ScreenConstants.HOME_PAGE));
        add(backButton);
    }

    void askLabelInit(){
        asking = new JLabel(startHtml + "<span> Select operations: </span>" + endHtml);
        asking.setBounds(nounButton.getX(), nounButton.getY()-30, 250, 20);
        add(asking);
    }

    void nounButtonInit(){
        nounButton = new JButton(startHtml + "Noun" + endHtml);
        nounButton.setBounds(width/2 - buttonWidth/2, height/2 - buttonHeight - 5 , buttonWidth, buttonHeight);
        nounButton.addActionListener(new ActionPerformer(frame, ScreenConstants.MAKE_PACKAGE_GOLANG));
        add(nounButton);
    }

    void verbButtonInit(){
        verbButton = new JButton(startHtml + "Verb" + endHtml);
        verbButton.setBounds(nounButton.getX(), nounButton.getY() + nounButton.getHeight() + 20 , buttonWidth, buttonHeight);
        verbButton.addActionListener(new ActionPerformer(frame, ScreenConstants.API_GOLANG));
        add(verbButton);
    }


    void panelFeatures() {
        setSize(new Dimension(width, height));
        setLayout(null);
        setOpaque(false);
    }
}


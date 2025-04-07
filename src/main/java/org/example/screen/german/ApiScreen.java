package org.example.screen.german;

import org.example.MainFrame;
import org.example.constants.variables.VariableConstants;
import org.example.utils.ActionPerformer;
import org.example.constants.screen.ScreenConstants;

import javax.swing.*;
import java.awt.*;

public class ApiScreen extends JPanel {

    //variable for panel dimensions
    private int width, height;

    //variable for sizing buttons
    private int buttonWidth, buttonHeight;

    //instance of MainFrame class
    private MainFrame frame;

    //All the buttons and label
    private JLabel asking;

    private JButton saveButton;
    private JButton getButton;
    private JButton paginateButton;
    private JButton backButton;

    //variable for designing using html
    private String startHtml, endHtml;

    //instance for event Listener
    private ActionPerformer actionPerformer;

    public ApiScreen(MainFrame frame, int width, int height){

//        actionPerformer = new ActionPerformer(frame);

        buttonWidth = 200;
        buttonHeight = 90;

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


        //for golang button declaration, properties and panel adding
        saveButtonInit();
        getButtonInit();
        paginatedButtonInit();

        //for asking label declaration, properties and panel adding
        askLabelInit();

        backButtonInit();

    }

    void saveButtonInit(){
        saveButton = new JButton(startHtml + "Save" + endHtml);
        saveButton.setBounds(width/2 - buttonWidth/2, height/2 - buttonHeight - 5 , buttonWidth, buttonHeight);
        saveButton.addActionListener(new ActionPerformer(frame, ScreenConstants.SAVE_API_GENERATE_GOLANG));
        add(saveButton);
    }

    void getButtonInit(){
        getButton = new JButton(startHtml + "Get" + endHtml);
        getButton.setBounds(saveButton.getX(), saveButton.getY() + saveButton.getHeight() + 10 , buttonWidth, buttonHeight);
        getButton.addActionListener(new ActionPerformer(frame, ScreenConstants.GET_API_GENERATE_GOLANG));
        add(getButton);
    }

    void paginatedButtonInit(){
        paginateButton = new JButton(startHtml + "Paginate" + endHtml);
        paginateButton.setBounds(getButton.getX(), getButton.getY() + getButton.getHeight() + 10 , buttonWidth, buttonHeight);
        paginateButton.addActionListener(new ActionPerformer(frame, ScreenConstants.PAGINATE_API_GENERATE_GOLANG));
        add(paginateButton);
    }

    void askLabelInit(){
        asking = new JLabel(startHtml + "<span> Select operations: </span>" + endHtml);
        asking.setBounds(saveButton.getX(), saveButton.getY()-30, 250, 20);
        add(asking);
    }

    void backButtonInit(){
        backButton = new JButton(startHtml + "Back" + endHtml);
        backButton.setBounds(VariableConstants.BACK_BUTTON_X, VariableConstants.BACK_BUTTON_Y, VariableConstants.BACK_BUTTON_WIDTH, VariableConstants.BACK_BUTTON_HEIGHT);
        backButton.addActionListener(new ActionPerformer(frame, ScreenConstants.GERMAN_HOME_PAGE));
        add(backButton);
    }




    void panelFeatures() {
        setSize(new Dimension(width, height));
        setLayout(null);
        setOpaque(false);
    }
}


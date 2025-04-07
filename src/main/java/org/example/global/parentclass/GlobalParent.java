package org.example.global.parentclass;

import org.example.MainFrame;
import org.example.constants.screen.german.GermanScreenConstants;
import org.example.constants.variables.VariableConstants;
import org.example.utils.ActionPerformer;

import javax.swing.*;
import java.awt.*;

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


    public GlobalParent(MainFrame frame, int width, int height) {
        buttonWidth = 120;
        buttonHeight = 30;

        this.frame = frame;
        this.width = width;
        this.height = height;

        startHtml = "<html> <style> h2{font-family: \"Comic Sans MS\", \"Comic Sans\", cursive;}span{font-family: fantasy}</style> <center><h2>";
        endHtml = "</h2> </center> </html>";

        //this method contains all the panel properties
        panelFeatures();
        backButtonInit();

    }


    protected void backButtonInit() {
        backButton = new JButton(startHtml + "Back" + endHtml);
        backButton.setBounds(VariableConstants.BACK_BUTTON_X, VariableConstants.BACK_BUTTON_Y, VariableConstants.BACK_BUTTON_WIDTH, VariableConstants.BACK_BUTTON_HEIGHT);
        backButton.addActionListener(new ActionPerformer(frame, GermanScreenConstants.VERB_PAGE));
        add(backButton);
    }




    void panelFeatures() {
        setSize(new Dimension(width, height));
        setLayout(null);
        setOpaque(false);
    }
}

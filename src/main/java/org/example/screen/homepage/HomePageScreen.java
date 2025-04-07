package org.example.screen.homepage;

import org.example.MainFrame;
import org.example.utils.ActionPerformer;
import org.example.constants.screen.ScreenConstants;

import javax.swing.*;
import java.awt.*;

public class HomePageScreen extends JPanel {

    //variable for panel dimensions
    private int width, height;

    //variable for sizing buttons
    private int buttonWidth, buttonHeight;

    //instance of MainFrame class
    private MainFrame frame;

    //All the buttons and label
    private JLabel asking;
    private JButton germanButton;

    //variable for designing using html
    private String startHtml, endHtml;

    public HomePageScreen(MainFrame frame, int width, int height){

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
        germanInit();


        //for asking label declaration, properties and panel adding
        ask();

    }

    void ask(){
        asking = new JLabel(startHtml + "<span> Select Language: </span>" + endHtml);
        asking.setBounds(germanButton.getX(), germanButton.getY()-30, 250, 20);
        add(asking);
    }

    void germanInit(){
        germanButton = new JButton(startHtml + "German" + endHtml);
        germanButton.setBounds(width/2 - buttonWidth/2, height/2 - buttonHeight - 5 , buttonWidth, buttonHeight);
        germanButton.addActionListener(new ActionPerformer(frame, ScreenConstants.GERMAN_HOME_PAGE));
        add(germanButton);
    }




    void panelFeatures() {
        setSize(new Dimension(width, height));
        setLayout(null);
        setOpaque(false);
    }
}

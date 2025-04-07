package org.example.utils;

import org.example.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionPerformer implements ActionListener {

    //instrance of mainframe class
    private MainFrame frame;
    private String panelName;


    // Constructor to pass the card layout, main panel, and the panel name
    public ActionPerformer(MainFrame frame, String panelName) {
        this.frame = frame;
        this.panelName = panelName;
    }

    // Override actionPerformed to switch between panels
    @Override
    public void actionPerformed(ActionEvent e) {
        frame.getCardLayout().show(frame.getMainPanel(), panelName); // Show the specified panel
    }

}

package org.example.utils;

import org.example.MainFrame;
import org.example.global.parentinterface.Refreshable;

import java.awt.*;
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
        Component comp = getPanelByName(panelName); // however you retrieve it

        if (comp instanceof Refreshable refreshable) {
            refreshable.refresh(); // call before showing
        }

        frame.getCardLayout().show(frame.getMainPanel(), panelName); // Show the specified panel
    }

    public Component getPanelByName(String name) {
        for (Component comp : frame.getMainPanel().getComponents()) {
            if (name.equals(comp.getName())) {
                return comp;
            }
        }
        return null;
    }


}

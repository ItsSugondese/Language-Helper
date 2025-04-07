package org.example.global.parentclass;

import org.example.MainFrame;

import javax.swing.*;

public class MenuGlobalParent extends GlobalParent{
    protected JLabel asking;

    public MenuGlobalParent(MainFrame frame, int width, int height) {
        super(frame, width, height);
        buttonWidth = 200;
        buttonHeight = 90;
    }
}

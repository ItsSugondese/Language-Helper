package org.example.global.parentclass;

import org.example.MainFrame;

import javax.swing.*;

public class MenuGlobalParent extends GlobalParent{
    protected JLabel asking;

    public MenuGlobalParent(MainFrame frame, int width, int height) {
        super(frame, width, height);
    }

    @Override
    public void setButtonWidth(int buttonWidth) {
        super.setButtonWidth(200);
    }

    @Override
    public void setButtonHeight(int buttonHeight) {
        super.setButtonHeight(90);
    }
}

package org.example.utils.uihelper;

import org.example.MainFrame;

import javax.swing.*;

public class CustomPopUp {
    public static void showPopUpMessage(MainFrame frame, String message){
        JOptionPane.showMessageDialog(frame.getMainPanel(), message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}

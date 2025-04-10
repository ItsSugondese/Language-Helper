package org.example.utils.uihelper;

import org.example.MainFrame;

import javax.swing.*;

public class CustomPopUp {
    public static void showPopUpMessage(MainFrame frame, String message){
        JOptionPane.showMessageDialog(frame.getMainPanel(), message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showPopUpMessageWithTextArea(MainFrame frame) {
        JTextArea textArea = new JTextArea(20, 50); // 20 rows, 50 columns
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(frame.getMainPanel(), scrollPane, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

}

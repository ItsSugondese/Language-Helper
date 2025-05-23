package org.example.utils.uihelper;

import org.example.constants.DelimiterConstants;
import org.example.utils.misc.StringUtils;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class CustomTextAreaDialog extends JDialog {
    private RSyntaxTextArea textArea;
    private boolean confirmed = false;

    // The dialog is created only once and reused for every pop-up
    private static CustomTextAreaDialog instance;

    // Private constructor to prevent direct instantiation
    private CustomTextAreaDialog(Frame owner, String title, String initialText) {
        super(owner, title, true);

        // Set up the dialog layout
        setLayout(new BorderLayout());

        // Create the JTextArea and JScrollPane
        textArea = new RSyntaxTextArea(20, 50);
        textArea.setText(initialText);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(true);

        RTextScrollPane scrollPane = new RTextScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Create the buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());


        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            confirmed = true;
            setVisible(false);  // Close the dialog
        });

        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener(e -> {
            Map<String, String> mapValues = StringUtils.mapOfStringFromString(textArea.getText(), DelimiterConstants.regexPipSeperator, DelimiterConstants.lineBreak);
            textArea.setText(StringUtils.stringFromMapOfString(mapValues, DelimiterConstants.lineBreak, DelimiterConstants.pipeSeperator));

        });

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> textArea.setText(""));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            confirmed = false;
            setVisible(false);  // Close the dialog
        });

        buttonPanel.add(okButton);
        buttonPanel.add(filterButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(cancelButton);

        // Add the buttons to the dialog
        add(buttonPanel, BorderLayout.SOUTH);

        // Set dialog size and location
        setSize(600, 400);
        setLocationRelativeTo(owner);  // Center the dialog
    }

    // Get the entered text from the JTextArea
    public String getText() {
        return textArea.getText();
    }



    // Static method to get the singleton instance of the dialog
    private static CustomTextAreaDialog getInstance(Frame owner, String title, String initialText) {
        if (instance == null) {
            instance = new CustomTextAreaDialog(owner, title, initialText);
        } else {
            // Reset the text area to avoid showing stale data
            instance.textArea.setText(initialText);
        }
        return instance;
    }

    // Static method to call the dialog from anywhere
    public static String showCustomDialog(Frame owner, String title, String initialText) {
        CustomTextAreaDialog dialog = getInstance(owner, title, initialText);
        dialog.setVisible(true);

        if(!dialog.getText().isEmpty() && dialog.confirmed){
            return dialog.getText();
        } else if(dialog.confirmed){
            return null;
        } else {
            return initialText;
        }
    }
}

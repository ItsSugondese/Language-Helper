package org.example.utils.uihelper;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomTextFieldDialog extends JDialog {
    private JTextField textField;
    private boolean confirmed = false;

    // The dialog is created only once and reused for every pop-up
    private static CustomTextFieldDialog instance;

    // Private constructor to prevent direct instantiation
    private CustomTextFieldDialog(Frame owner, String title, String initialText) {
        super(owner, title, true);

        // Set up the dialog layout
        setLayout(new BorderLayout());

        // Create the JTextArea and JScrollPane
        textField = new JTextField(initialText);

        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("\\d+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("\\d+")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        textField.setEditable(true);

        JScrollPane scrollPane = new JScrollPane(textField);
        add(scrollPane, BorderLayout.CENTER);

        // Create the buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            confirmed = true;
            setVisible(false);  // Close the dialog
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            confirmed = false;
            setVisible(false);  // Close the dialog
        });

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        // Add the buttons to the dialog
        add(buttonPanel, BorderLayout.SOUTH);

        // Set dialog size and location
        setSize(200, 100);
        setLocationRelativeTo(owner);  // Center the dialog
    }

    // Get the entered text from the JTextArea
    public String getText() {
        return textField.getText();
    }

    // Check if the user clicked OK
    public boolean isConfirmed() {
        return confirmed;
    }

    // Static method to get the singleton instance of the dialog
    private static CustomTextFieldDialog getInstance(Frame owner, String title, String initialText) {
        if (instance == null) {
            instance = new CustomTextFieldDialog(owner, title, initialText);
        } else {
            // Reset the text area to avoid showing stale data
            instance.textField.setText(initialText);
        }
        return instance;
    }

    // Static method to call the dialog from anywhere
    public static String showCustomDialog(Frame owner, String title, String initialText) {
        CustomTextFieldDialog dialog = getInstance(owner, title, initialText);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            return dialog.getText();  // User clicked OK, return the entered text
        } else {
            return null;  // User clicked Cancel, return null
        }
    }
}

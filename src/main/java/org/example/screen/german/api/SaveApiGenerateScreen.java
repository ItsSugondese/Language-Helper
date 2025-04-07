package org.example.screen.german.api;

import org.example.MainFrame;
import org.example.constants.filepath.german.noun.NounGermanFilePathConstants;
import org.example.constants.screen.ScreenConstants;
import org.example.constants.variables.VariableConstants;
import org.example.utils.ActionPerformer;
import org.example.utils.helper.FileWriterHelper;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class SaveApiGenerateScreen extends JPanel {

    //variable for panel dimensions
    private int width, height;

    //variable for sizing buttons
    private int buttonWidth, buttonHeight;

    //instance of MainFrame class
    private MainFrame frame;


    private JButton backButton;
    private JButton generateButton;

    private JTextField moduleNameTextField;

    private JRadioButton saveRadioButton;
    private JRadioButton saveWithImageRadioButton;
    private JRadioButton withUpdateSaveRadioButton;
    private JRadioButton withImageAndUpateSaveRadioButton;
    private ButtonGroup group;

    private JTextPane codeGeneratedTextArea;

    private JScrollPane scrollPane;
    //variable for designing using html
    private String startHtml, endHtml;

    public SaveApiGenerateScreen(MainFrame frame, int width, int height){

        buttonWidth = 200;
        buttonHeight = 30;

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
        backButtonInit();


        radioButtonsInit();
        moduleNameTextFieldInit();
        generateButtonInit();

        codeGeneratedTextAreaInit();

        //for asking label declaration, properties and panel adding


    }

    void radioButtonsInit(){

        saveRadioButton = new JRadioButton("Save");
        saveRadioButton.setSelected(true);
        saveRadioButton.setBounds(width / 2 - buttonWidth/2, backButton.getY() + backButton.getHeight() + 10,
                buttonWidth, buttonHeight);
        add(saveRadioButton);

        withUpdateSaveRadioButton = new JRadioButton("With Update");
        withUpdateSaveRadioButton.setBounds(saveRadioButton.getX(), saveRadioButton.getY() + saveRadioButton.getHeight() + 5,
                buttonWidth, buttonHeight);
        add(withUpdateSaveRadioButton);

        saveWithImageRadioButton = new JRadioButton("Save With Image");
        saveWithImageRadioButton.setBounds(withUpdateSaveRadioButton.getX(), withUpdateSaveRadioButton.getY() + withUpdateSaveRadioButton.getHeight() + 5,
                buttonWidth, buttonHeight);
        add(saveWithImageRadioButton);

        withImageAndUpateSaveRadioButton = new JRadioButton("Save With update and Image");
        withImageAndUpateSaveRadioButton.setBounds(saveWithImageRadioButton.getX(), saveWithImageRadioButton.getY() + saveWithImageRadioButton.getHeight() + 5, 300, buttonHeight);
        add(withImageAndUpateSaveRadioButton);

        group = new ButtonGroup();
        group.add(saveRadioButton);
        group.add(saveWithImageRadioButton);
        group.add(withUpdateSaveRadioButton);
        group.add(withImageAndUpateSaveRadioButton);


    }

    void backButtonInit(){
        backButton = new JButton(startHtml + "Back" + endHtml);
        backButton.setBounds(VariableConstants.BACK_BUTTON_X, VariableConstants.BACK_BUTTON_Y, VariableConstants.BACK_BUTTON_WIDTH, VariableConstants.BACK_BUTTON_HEIGHT);
        backButton.addActionListener(new ActionPerformer(frame, ScreenConstants.API_GOLANG));
        add(backButton);
    }

    void codeGeneratedTextAreaInit(){
        codeGeneratedTextArea = new JTextPane();
        codeGeneratedTextArea.setBorder((new LineBorder(Color.BLACK)));

        // Set JTextArea alignment
        codeGeneratedTextArea.setMargin(new Insets(10, 10, 10, 10));
        scrollPane = new JScrollPane(codeGeneratedTextArea);
        scrollPane.setBounds(10, generateButton.getY() + generateButton.getHeight() + 30, width - 20, height/2);
        add(scrollPane);
    }

    void moduleNameTextFieldInit(){
        moduleNameTextField = new JTextField();
        moduleNameTextField.setBounds(width/2 - buttonWidth/2, withImageAndUpateSaveRadioButton.getY() + withImageAndUpateSaveRadioButton.getHeight() + 10, buttonWidth, buttonHeight);

        // Add DocumentListener to track changes on every keystroke
        moduleNameTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLabel();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLabel();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLabel(); // Handles attribute changes, not commonly used for plain text
            }

            private void updateLabel() {
                generateButton.setEnabled(verifyGenerateButtonClickable());
            }
        });
        add(moduleNameTextField);
    }

    void generateButtonInit(){
        int generateButtonWidth = 120;
        generateButton = new JButton("Generate");
        generateButton.setBounds((moduleNameTextField.getX() + moduleNameTextField.getWidth()/2) - generateButtonWidth/2, moduleNameTextField.getY() + moduleNameTextField.getHeight() + 10,
                generateButtonWidth, buttonHeight);
        generateButton.setEnabled(verifyGenerateButtonClickable());

        generateButton.addActionListener(e -> {
            String moduleName = moduleNameTextField.getText().trim().replace(" ", "-").toLowerCase();

            ButtonModel selectedModel = group.getSelection();
            if(selectedModel == saveRadioButton.getModel()){
                String text = FileWriterHelper.readAndWriteFromStorageFileToTextArea(NounGermanFilePathConstants.RESOURCE_SAVE_API_PATH, moduleName);
                codeGeneratedTextArea.setText(text);
            } else if(selectedModel == saveWithImageRadioButton.getModel()){
                String text = FileWriterHelper.readAndWriteFromStorageFileToTextArea(NounGermanFilePathConstants.RESOURCE_SAVE_API_WITH_IMAGE_PATH, moduleName);
                codeGeneratedTextArea.setText(text);
            } else if(selectedModel == withUpdateSaveRadioButton.getModel()){
                String text = FileWriterHelper.readAndWriteFromStorageFileToTextArea(NounGermanFilePathConstants.RESOURCE_SAVE_API_WITH_UPDATE_PATH, moduleName);
                codeGeneratedTextArea.setText(text);
            } else if(selectedModel == withImageAndUpateSaveRadioButton.getModel()){
                String text = FileWriterHelper.readAndWriteFromStorageFileToTextArea(NounGermanFilePathConstants.RESOURCE_SAVE_API_WITH_UPDATE_IMAGE_PATH, moduleName);
                codeGeneratedTextArea.setText(text);
            }

        });

        add(generateButton);
    }

    private boolean verifyGenerateButtonClickable(){
        return (moduleNameTextField.getText() != null && !moduleNameTextField.getText().isBlank());
    }

    void panelFeatures() {
        setSize(new Dimension(width, height));
        setLayout(null);
        setOpaque(false);
    }
}


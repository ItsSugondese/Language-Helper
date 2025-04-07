package org.example.screen.german.api;

import org.example.MainFrame;
import org.example.constants.filepath.german.noun.NounGermanFilePathConstants;
import org.example.constants.screen.ScreenConstants;
import org.example.constants.variables.VariableConstants;
import org.example.enums.FolderNameEnums;
import org.example.utils.ActionPerformer;
import org.example.utils.helper.FileWriterHelper;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class GetApiGenerateScreen extends JPanel {

    //variable for panel dimensions
    private int width, height;

    //variable for sizing buttons
    private int buttonWidth, buttonHeight;

    //instance of MainFrame class
    private MainFrame frame;


    private JButton backButton;
    private JButton generateButton;

    private JTextField moduleNameTextField;

    private JRadioButton getByIdRadioButton;
    private JRadioButton getAllRadioButton;
    private ButtonGroup group;

    // Create a JComboBox (dropdown) with the items
    private JComboBox<String> serviceOrientedDropdown;
    private JTextPane codeGeneratedTextArea;

    private JScrollPane scrollPane;
    //variable for designing using html
    private String startHtml, endHtml;
    private int generateButtonWidth = 120;

    public GetApiGenerateScreen(MainFrame frame, int width, int height){

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
        serviceOrientedInit();
        moduleNameTextFieldInit();
        generateButtonInit();

        codeGeneratedTextAreaInit();

        //for asking label declaration, properties and panel adding


    }

    void radioButtonsInit(){
        getByIdRadioButton = new JRadioButton("Get by Id");
        getByIdRadioButton.setBounds(width / 2 - buttonWidth/2, backButton.getY() + backButton.getHeight() + 10,
                buttonWidth, buttonHeight);
        add(getByIdRadioButton);

        getAllRadioButton = new JRadioButton("Get All");
        getAllRadioButton.setBounds(getByIdRadioButton.getX() + getByIdRadioButton.getWidth() , getByIdRadioButton.getY(), buttonWidth, buttonHeight);
        getAllRadioButton.setSelected(true);
        add(getAllRadioButton);

        group = new ButtonGroup();

        group.add(getByIdRadioButton);
        group.add(getAllRadioButton);
    }

    void serviceOrientedInit(){
        String[] items = {FolderNameEnums.SERVICE.getName(), FolderNameEnums.CONTROLLER.getName(), FolderNameEnums.ROUTE.getName()};
        serviceOrientedDropdown = new JComboBox<>(items);
        serviceOrientedDropdown.setBounds(width / 2 - buttonWidth/2, getByIdRadioButton.getY() + getByIdRadioButton.getHeight() + 10,
                buttonWidth, buttonHeight);

        add(serviceOrientedDropdown);
    }

    void backButtonInit(){
        backButton = new JButton(startHtml + "Back" + endHtml);
        backButton.setBounds(VariableConstants.BACK_BUTTON_X, VariableConstants.BACK_BUTTON_Y, VariableConstants.BACK_BUTTON_WIDTH, VariableConstants.BACK_BUTTON_HEIGHT);
        backButton.addActionListener(new ActionPerformer(frame, ScreenConstants.API_GOLANG));
        add(backButton);
    }

    void moduleNameTextFieldInit(){
        moduleNameTextField = new JTextField();
        moduleNameTextField.setBounds(width/2 - buttonWidth/2, serviceOrientedDropdown.getY() + serviceOrientedDropdown.getHeight() + 10, buttonWidth, buttonHeight);

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
        generateButtonWidth = 120;
        generateButton = new JButton("Generate");
        generateButton.setBounds((moduleNameTextField.getX() + moduleNameTextField.getWidth()/2) - generateButtonWidth/2, moduleNameTextField.getY() + moduleNameTextField.getHeight() + 10,
                generateButtonWidth, buttonHeight);
        generateButton.setEnabled(verifyGenerateButtonClickable());

        generateButton.addActionListener(e -> {
            String moduleName = moduleNameTextField.getText().trim().replace(" ", "-").toLowerCase();

            ButtonModel selectedModel = group.getSelection();
            String text;
            if(String.valueOf(serviceOrientedDropdown.getSelectedItem()).equals(FolderNameEnums.CONTROLLER.getName())){
                text = FileWriterHelper.readAndWriteFromStorageFileToTextArea(NounGermanFilePathConstants.RESOURCE_GET_ALL_API_CONTROLLER_PATH, moduleName);
                codeGeneratedTextArea.setText(text);
            } else if(String.valueOf(serviceOrientedDropdown.getSelectedItem()).equals(FolderNameEnums.ROUTE.getName())){
                text = FileWriterHelper.readAndWriteFromStorageFileToTextArea(NounGermanFilePathConstants.RESOURCE_GET_ALL_API_ROUTE_PATH, moduleName);
                codeGeneratedTextArea.setText(text);
            } else if(String.valueOf(serviceOrientedDropdown.getSelectedItem()).equals(FolderNameEnums.SERVICE.getName())){
                text = FileWriterHelper.readAndWriteFromStorageFileToTextArea(NounGermanFilePathConstants.RESOURCE_GET_ALL_API_SERVICE_PATH, moduleName);
                codeGeneratedTextArea.setText(text);
            }


        });

        add(generateButton);
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


    private boolean verifyGenerateButtonClickable(){
        return (moduleNameTextField.getText() != null && !moduleNameTextField.getText().isBlank());
    }

    private void setRadioButtonVisibility(boolean isVisible){
        getByIdRadioButton.setVisible(isVisible);
        getAllRadioButton.setVisible(isVisible);
    }

    void panelFeatures() {
        setSize(new Dimension(width, height));
        setLayout(null);
        setOpaque(false);
    }
}


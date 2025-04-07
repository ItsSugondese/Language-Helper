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

public class PaginateApiGenerateScreen extends JPanel {

    //variable for panel dimensions
    private int width, height;

    //variable for sizing buttons
    private int buttonWidth, buttonHeight;

    //instance of MainFrame class
    private MainFrame frame;


    private JButton backButton;
    private JButton generateButton;

    private JTextField moduleNameTextField;

    private JRadioButton normalServiceRadioButton;
    private JRadioButton withModificationServiceRadioButton;
    private ButtonGroup group;

    // Create a JComboBox (dropdown) with the items
    private JComboBox<String> serviceOrientedDropdown;
    private JTextPane codeGeneratedTextArea;

    private JScrollPane scrollPane;
    //variable for designing using html
    private String startHtml, endHtml;
    private int generateButtonWidth = 120;

    public PaginateApiGenerateScreen(MainFrame frame, int width, int height){

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
        normalServiceRadioButton = new JRadioButton("Normal");
        normalServiceRadioButton.setSelected(true);
        normalServiceRadioButton.setBounds(width / 2 - buttonWidth/2, backButton.getY() + backButton.getHeight() + 10,
                buttonWidth, buttonHeight);
        add(normalServiceRadioButton);

        withModificationServiceRadioButton = new JRadioButton("Modified");
        withModificationServiceRadioButton.setBounds(normalServiceRadioButton.getX() + normalServiceRadioButton.getWidth() , normalServiceRadioButton.getY(), buttonWidth, buttonHeight);
        add(withModificationServiceRadioButton);

        setRadioButtonVisibility(false);

        group = new ButtonGroup();

        group.add(normalServiceRadioButton);
        group.add(withModificationServiceRadioButton);
    }

    void serviceOrientedInit(){
        String[] items = {FolderNameEnums.REPOSITORY.getName(), FolderNameEnums.SERVICE.getName(), FolderNameEnums.CONTROLLER.getName(), FolderNameEnums.ROUTE.getName()};
        serviceOrientedDropdown = new JComboBox<>(items);
        serviceOrientedDropdown.setBounds(width / 2 - buttonWidth/2, backButton.getY() + backButton.getHeight() + 10,
                buttonWidth, buttonHeight);

        serviceOrientedDropdown.addActionListener(e -> {
            if(String.valueOf(serviceOrientedDropdown.getSelectedItem()).equals(FolderNameEnums.SERVICE.getName())) {
                setRadioButtonVisibility(true);
                serviceOrientedDropdown.setBounds(width / 2 - buttonWidth/2, normalServiceRadioButton.getY() + normalServiceRadioButton.getHeight() + 10,
                        buttonWidth, buttonHeight);
            }else {
                setRadioButtonVisibility(false);
                serviceOrientedDropdown.setBounds(width / 2 - buttonWidth/2, backButton.getY() + backButton.getHeight() + 10,
                        buttonWidth, buttonHeight);
            }
            moduleNameTextField.setBounds(width/2 - buttonWidth/2, serviceOrientedDropdown.getY() + serviceOrientedDropdown.getHeight() + 10, buttonWidth, buttonHeight);
            generateButton.setBounds((moduleNameTextField.getX() + moduleNameTextField.getWidth()/2) - generateButtonWidth/2, moduleNameTextField.getY() + moduleNameTextField.getHeight() + 10,
                    generateButtonWidth, buttonHeight);
            scrollPane.setBounds(10, generateButton.getY() + generateButton.getHeight() + 30, width - 20, height/2);

        });
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
            if(String.valueOf(serviceOrientedDropdown.getSelectedItem()).equals(FolderNameEnums.REPOSITORY.getName())){
                text = FileWriterHelper.readAndWriteFromStorageFileToTextArea(NounGermanFilePathConstants.RESOURCE_PAGINATE_API_REPO_PATH, moduleName);
                codeGeneratedTextArea.setText(text);
            } else if(String.valueOf(serviceOrientedDropdown.getSelectedItem()).equals(FolderNameEnums.CONTROLLER.getName())){
                text = FileWriterHelper.readAndWriteFromStorageFileToTextArea(NounGermanFilePathConstants.RESOURCE_PAGINATE_API_CONTROLLER_PATH, moduleName);
                codeGeneratedTextArea.setText(text);
            } else if(String.valueOf(serviceOrientedDropdown.getSelectedItem()).equals(FolderNameEnums.ROUTE.getName())){
                text = FileWriterHelper.readAndWriteFromStorageFileToTextArea(NounGermanFilePathConstants.RESOURCE_PAGINATE_API_ROUTE_PATH, moduleName);
                codeGeneratedTextArea.setText(text);
            } else if(String.valueOf(serviceOrientedDropdown.getSelectedItem()).equals(FolderNameEnums.SERVICE.getName())){
                if(selectedModel == normalServiceRadioButton.getModel())
                    text = FileWriterHelper.readAndWriteFromStorageFileToTextArea(NounGermanFilePathConstants.RESOURCE_PAGINATE_API_SERVICE_PATH, moduleName);
                else
                    text = FileWriterHelper.readAndWriteFromStorageFileToTextArea(NounGermanFilePathConstants.RESOURCE_PAGINATE_API_SERVICE_MODIFY_PATH, moduleName);

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
        normalServiceRadioButton.setVisible(isVisible);
        withModificationServiceRadioButton.setVisible(isVisible);
    }

    void panelFeatures() {
        setSize(new Dimension(width, height));
        setLayout(null);
        setOpaque(false);
    }
}


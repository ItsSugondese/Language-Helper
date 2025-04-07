package org.example.screen.german;

import org.example.MainFrame;
import org.example.constants.filepath.german.noun.NounGermanFilePathConstants;
import org.example.constants.screen.ScreenConstants;
import org.example.constants.variables.VariableConstants;
import org.example.enums.LanguageNameEnums;
import org.example.repository.german.enumpath.EnumPathRepo;
import org.example.repository.german.modulepath.ModulePathRepo;
import org.example.utils.ActionPerformer;
import org.example.utils.helper.FileWriterHelper;
import org.example.utils.uihelper.CustomPopUp;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class MakeEnumScreen extends JPanel {

    //variable for panel dimensions
    private int width, height;

    //variable for sizing buttons
    private int buttonWidth, buttonHeight;

    //instance of MainFrame class
    private MainFrame frame;

    private JLabel pathLabel;


    private JRadioButton globalRadioButton;
    private JRadioButton internalRadioButton;

    private ButtonGroup group;

    private JButton backButton;
    private JButton pathSelectorButton;
    private JButton generateButton;

    private JTextField enumNameTextField;
    //variable for designing using html
    private String startHtml, endHtml;

    public MakeEnumScreen(MainFrame frame, int width, int height){

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


        //labels
        pathLabelInit();
        pathSelectorButtonInit();

        radioButtonsInit();


        enumNameTextFieldInit();
        generateButtonInit();

        //for asking label declaration, properties and panel adding


    }


    void backButtonInit(){
        backButton = new JButton(startHtml + "Back" + endHtml);
        backButton.setBounds(VariableConstants.BACK_BUTTON_X, VariableConstants.BACK_BUTTON_Y, VariableConstants.BACK_BUTTON_WIDTH, VariableConstants.BACK_BUTTON_HEIGHT);
        backButton.addActionListener(new ActionPerformer(frame, ScreenConstants.GERMAN_HOME_PAGE));
        add(backButton);
    }

    void pathLabelInit(){
        String path = EnumPathRepo.getEnumPath();

        if (path == null){
            path = "No path specified";
        }
        pathLabel = new JLabel(startHtml + "<span> " + path + "</span>" + endHtml);
        Dimension labelSize = pathLabel.getPreferredSize();

        pathLabel.setBounds(width / 2 - labelSize.width/2, backButton.getY() + backButton.getHeight() + 10,
                labelSize.width, labelSize.height);
        add(pathLabel);
    }


    void pathSelectorButtonInit(){
        pathSelectorButton = new JButton("Select Folder");
        pathSelectorButton.setBounds(width / 2 - buttonWidth/2, pathLabel.getY() + pathLabel.getHeight(),
                buttonWidth, buttonHeight);
        pathSelectorButton.addActionListener(e -> {
            String selectedPath = ModulePathRepo.getModulePath(LanguageNameEnums.GOLANG);
            JFileChooser fileChooser = new JFileChooser(selectedPath == null? "" : selectedPath);
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Allow only directories to be selected
            fileChooser.setAcceptAllFileFilterUsed(false); // Disable the "All files" option

            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFolder = fileChooser.getSelectedFile();
                // Write lines to the file, creating it if it doesn't exist, and appending if it does
                try {
                    String path = selectedFolder.getAbsolutePath();
                    Files.write(Paths.get(NounGermanFilePathConstants.ENUM_PATH), Arrays.asList(path));
                    pathLabel.setText(path);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println("Selected folder: " + selectedFolder.getAbsolutePath());
            }
        });

        add(pathSelectorButton);
    }

    void radioButtonsInit(){
        globalRadioButton = new JRadioButton("Global");
        globalRadioButton.setBounds(width / 2 - buttonWidth, pathSelectorButton.getY() + pathSelectorButton.getHeight() + 30,
                buttonWidth, buttonHeight);
        add(globalRadioButton);

        internalRadioButton = new JRadioButton("Internal");
        internalRadioButton.setSelected(true);
        internalRadioButton.setBounds(globalRadioButton.getX() + globalRadioButton.getWidth() , globalRadioButton.getY(), buttonWidth, buttonHeight);
        add(internalRadioButton);

        group = new ButtonGroup();

        group.add(globalRadioButton);
        group.add(internalRadioButton);
    }

    void enumNameTextFieldInit(){
        enumNameTextField = new JTextField();
        enumNameTextField.setBounds(width/2 - 100, height/2 - 15, buttonWidth, buttonHeight);

        // Add DocumentListener to track changes on every keystroke
        enumNameTextField.getDocument().addDocumentListener(new DocumentListener() {
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
        add(enumNameTextField);
    }

    void generateButtonInit(){
        int generateButtonWidth = 120;
        generateButton = new JButton("Generate");
        generateButton.setBounds((enumNameTextField.getX() + enumNameTextField.getWidth()/2) - generateButtonWidth/2, enumNameTextField.getY() + enumNameTextField.getHeight() + 10,
                generateButtonWidth, buttonHeight);
//        generateButton.setEnabled(verifyGenerateButtonClickable());

        generateButton.addActionListener(e -> {
            generateButton.setEnabled(false);

            ButtonModel selectedModel = group.getSelection();

            String beforeEnumName = enumNameTextField.getText().trim().replace(" ", "-").toLowerCase();
            String enumName = beforeEnumName + "-enums";
            String enumPath;

            if(selectedModel == internalRadioButton.getModel())
                enumPath = EnumPathRepo.getEnumPath() + File.separator + "enums";
            else
                enumPath = EnumPathRepo.getEnumPath() + File.separator + enumName;

            File folder = new File(enumPath);

            // Check if the folder exists
            if (folder.exists()) {
                return;
            }

            folder.mkdir();
            String enumPathSnakeCase = enumPath + File.separator + enumName.replace("-", "_").toLowerCase() + ".go";

            FileWriterHelper.readAndWriteFromStorageFileToEnumFile(NounGermanFilePathConstants.RESOURCE_ENUM_GENERATOR_PATH, enumPathSnakeCase, beforeEnumName);


            enumNameTextField.setText("");
            CustomPopUp.showPopUpMessage(frame, "Files created successfully");


        });

        add(generateButton);
    }

    private boolean verifyGenerateButtonClickable(){
        return ModulePathRepo.getModulePath(LanguageNameEnums.GOLANG) != null && (enumNameTextField.getText() != null && !enumNameTextField.getText().isBlank());
    }

    void panelFeatures() {
        setSize(new Dimension(width, height));
        setLayout(null);
        setOpaque(false);
    }
}


package org.example.screen.german;

import org.example.MainFrame;
import org.example.constants.filepath.german.noun.NounGermanFilePathConstants;
import org.example.constants.screen.ScreenConstants;
import org.example.constants.variables.VariableConstants;
import org.example.enums.FolderNameEnums;
import org.example.enums.LanguageNameEnums;
import org.example.repository.german.modulepath.ModulePathRepo;
import org.example.utils.ActionPerformer;
import org.example.utils.helper.FileWriterHelper;
import org.example.utils.uihelper.CustomPopUp;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class MakePackageScreen extends JPanel {

    //variable for panel dimensions
    private int width, height;

    //variable for sizing buttons
    private int buttonWidth, buttonHeight;

    //instance of MainFrame class
    private MainFrame frame;

    //All the buttons and label
    private JLabel asking;
    private JLabel pathLabel;

    private JRadioButton usingUUIDRadioButton;
    private JRadioButton notUUIDRadioButton;
    private ButtonGroup group;

    private JButton backButton;
    private JButton pathSelectorButton;
    private JButton generateButton;

    private JTextField moduleNameTextField;
    //variable for designing using html
    private String startHtml, endHtml;

    public MakePackageScreen(MainFrame frame, int width, int height){

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

        moduleNameTextFieldInit();
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
        String path = ModulePathRepo.getModulePath(LanguageNameEnums.GOLANG);

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
                    String path = ModulePathRepo.setModulePath(selectedFolder, LanguageNameEnums.GOLANG);
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
        usingUUIDRadioButton = new JRadioButton("Using UUID");
        usingUUIDRadioButton.setSelected(true);
        usingUUIDRadioButton.setBounds(width / 2 - buttonWidth, pathSelectorButton.getY() + pathSelectorButton.getHeight() + 30,
                buttonWidth, buttonHeight);
        add(usingUUIDRadioButton);

        notUUIDRadioButton = new JRadioButton("Not using UUID");
        notUUIDRadioButton.setBounds(usingUUIDRadioButton.getX() + usingUUIDRadioButton.getWidth() , usingUUIDRadioButton.getY(), buttonWidth, buttonHeight);
        add(notUUIDRadioButton);

        group = new ButtonGroup();

        group.add(usingUUIDRadioButton);
        group.add(notUUIDRadioButton);
    }

    void moduleNameTextFieldInit(){
        moduleNameTextField = new JTextField();
        moduleNameTextField.setBounds(width/2 - 100, height/2 - 15, buttonWidth, buttonHeight);

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
//        generateButton.setEnabled(verifyGenerateButtonClickable());

        generateButton.addActionListener(e -> {
            generateButton.setEnabled(false);

            String moduleName = moduleNameTextField.getText().trim().replace(" ", "-").toLowerCase();
            String modulePath = ModulePathRepo.getModulePath(LanguageNameEnums.GOLANG) + File.separator + moduleName;

            File folder = new File(modulePath);

            // Check if the folder exists
            if (folder.exists()) {
              return;
            }

            folder.mkdir();

            List<FolderNameEnums> subFoldersEnums = Arrays.stream(FolderNameEnums.values()).toList();

            for(FolderNameEnums subFolderEnum : subFoldersEnums){
                String snakeCaseModuleName = moduleName.replace("-", "_").toLowerCase();
                String toNameSubFolder = subFolderEnum.getName();

                if(subFolderEnum == FolderNameEnums.NAVIGATOR)
                    toNameSubFolder = moduleName + "-" + subFolderEnum.getName();

                String subFolderPath = modulePath + File.separator + toNameSubFolder;

                File subFolderDirectory = new File(subFolderPath);
                subFolderDirectory.mkdir();

                String toNameGoFile = null;

                if(subFolderEnum == FolderNameEnums.ROUTE)
                    toNameGoFile = "routes";
                else if(subFolderEnum == FolderNameEnums.NAVIGATOR)
                    toNameGoFile = "find_by";
                else
                    toNameGoFile = subFolderEnum.getName();
                String fileName = snakeCaseModuleName + "_" + toNameGoFile + ".go";
                String fileNameCreatePath = subFolderPath + File.separator + fileName;


                ButtonModel selectedModel = group.getSelection();
                boolean isUUID = false;
                if(selectedModel == usingUUIDRadioButton.getModel())
                    isUUID = true;

//                    Files.createFile(Paths.get(fileNameCreatePath));
                    if(subFolderEnum == FolderNameEnums.ROUTE)
                        FileWriterHelper.readAndWriteFromStorageFileToFile(NounGermanFilePathConstants.RESOURCE_GO_ROUTE_PATH, fileNameCreatePath, moduleName, isUUID);
                    else if(subFolderEnum == FolderNameEnums.CONTROLLER)
                        FileWriterHelper.readAndWriteFromStorageFileToFile(NounGermanFilePathConstants.RESOURCE_CONTROLLER_PATH, fileNameCreatePath, moduleName, isUUID);
                    else if(subFolderEnum == FolderNameEnums.SERVICE)
                        FileWriterHelper.readAndWriteFromStorageFileToFile(NounGermanFilePathConstants.RESOURCE_SERVICE_PATH, fileNameCreatePath, moduleName, isUUID);
                    else if(subFolderEnum == FolderNameEnums.MODEL)
                        FileWriterHelper.readAndWriteFromStorageFileToFile(NounGermanFilePathConstants.RESOURCE_MODEL_PATH, fileNameCreatePath, moduleName, isUUID);
                    else if(subFolderEnum == FolderNameEnums.REPOSITORY)
                        FileWriterHelper.readAndWriteFromStorageFileToFile(NounGermanFilePathConstants.RESOURCE_REPOSITORY_PATH, fileNameCreatePath, moduleName, isUUID);
                    else if(subFolderEnum == FolderNameEnums.NAVIGATOR)
                        FileWriterHelper.readAndWriteFromStorageFileToFile(NounGermanFilePathConstants.RESOURCE_NAVIGATOR_PATH, fileNameCreatePath, moduleName, isUUID);
                    else if(subFolderEnum == FolderNameEnums.DTO) {
                        String requestName = snakeCaseModuleName + "_" + "request.go";
                        String paginationRequestName = snakeCaseModuleName + "_" + "pagination_request.go";
                        String responseName = snakeCaseModuleName + "_" + "details_response.go";
                        String dtoRequestFileNameCreatePath = subFolderPath + File.separator + requestName;
                        String dtoPaginationRequestFileNameCreatePath = subFolderPath + File.separator + paginationRequestName;
                        String dtoResponseFileNameCreatePath = subFolderPath + File.separator + responseName;
                        FileWriterHelper.readAndWriteFromStorageFileToFile(NounGermanFilePathConstants.RESOURCE_DTO_REQUEST_PATH, dtoRequestFileNameCreatePath, moduleName, isUUID);
                        FileWriterHelper.readAndWriteFromStorageFileToFile(NounGermanFilePathConstants.RESOURCE_DTO_PAGINATION_REQUEST_PATH, dtoPaginationRequestFileNameCreatePath, moduleName, isUUID);
                        FileWriterHelper.readAndWriteFromStorageFileToFile(NounGermanFilePathConstants.RESOURCE_DTO_RESPONSE_PATH, dtoResponseFileNameCreatePath, moduleName, isUUID);

                }
            }

            moduleNameTextField.setText("");
            CustomPopUp.showPopUpMessage(frame, "Files created successfully");


        });

        add(generateButton);
    }

    private boolean verifyGenerateButtonClickable(){
        return ModulePathRepo.getModulePath(LanguageNameEnums.GOLANG) != null && (moduleNameTextField.getText() != null && !moduleNameTextField.getText().isBlank());
    }

    void panelFeatures() {
        setSize(new Dimension(width, height));
        setLayout(null);
        setOpaque(false);
    }
}


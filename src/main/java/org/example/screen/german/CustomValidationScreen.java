package org.example.screen.german;

import org.example.MainFrame;
import org.example.constants.filepath.german.noun.NounGermanFilePathConstants;
import org.example.constants.screen.ScreenConstants;
import org.example.constants.variables.VariableConstants;
import org.example.repository.german.customvalidationpath.CustomValidationPathRepo;
import org.example.repository.german.projectname.ProjectNameRepo;
import org.example.utils.ActionPerformer;
import org.example.utils.helper.FileWriterHelper;
import org.example.utils.uihelper.CustomPopUp;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomValidationScreen extends JPanel {

    //variable for panel dimensions
    private int width, height;

    //variable for sizing buttons
    private int buttonWidth, buttonHeight;

    //instance of MainFrame class
    private MainFrame frame;

    private JLabel pathLabel;

    private JButton backButton;
    private JButton pathSelectorButton;
    private JButton generateButton;


    private JTextPane packageGeneratedTextArea;
    private JScrollPane scrollPane;

    private String noPath = "No Path Specified";

    //variable for designing using html
    private String startHtml, endHtml;

    public CustomValidationScreen(MainFrame frame, int width, int height){

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

        packageGeneratedTextAreaInit();
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
        String path = CustomValidationPathRepo.getCustomValidationPath();

        if (path == null){
            path = noPath;
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
            String selectedPath = CustomValidationPathRepo.getCustomValidationPath();
            JFileChooser fileChooser = new JFileChooser(selectedPath == null? "" : selectedPath);
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Allow only directories to be selected
            fileChooser.setAcceptAllFileFilterUsed(false); // Disable the "All files" option

            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFolder = fileChooser.getSelectedFile();
                // Write lines to the file, creating it if it doesn't exist, and appending if it does
                try {
                    String path = selectedFolder.getAbsolutePath();
                    Files.write(Paths.get(NounGermanFilePathConstants.CUSTOM_VALIDATION_PATH), Arrays.asList(path));
                    pathLabel.setText(path);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println("Selected folder: " + selectedFolder.getAbsolutePath());
            }
        });

        add(pathSelectorButton);
    }

    void packageGeneratedTextAreaInit(){
        packageGeneratedTextArea = new JTextPane();
        packageGeneratedTextArea.setBorder((new LineBorder(Color.BLACK)));

        // Set JTextArea alignment
        packageGeneratedTextArea.setMargin(new Insets(10, 10, 10, 10));
        scrollPane = new JScrollPane(packageGeneratedTextArea);
        scrollPane.setBounds(10, pathSelectorButton.getY() + pathSelectorButton.getHeight() + 30, width - 20, height/3);
        add(scrollPane);
    }

    void generateButtonInit(){
        int generateButtonWidth = 120;
        generateButton = new JButton("Generate");
        generateButton.setBounds((pathSelectorButton.getX() + pathSelectorButton.getWidth()/2) - generateButtonWidth/2, scrollPane.getY() + scrollPane.getHeight() + 30,
                generateButtonWidth, buttonHeight);
        generateButton.setEnabled(verifyGenerateButtonClickable());

        generateButton.addActionListener(e -> {
            String[] textListTextArea = packageGeneratedTextArea.getText().split("\n");
            if (textListTextArea.length < 3){
                CustomPopUp.showPopUpMessage(frame, "Invalid values provided");
                return;
            }
            String folderName = CustomValidationPathRepo.getCustomValidationPath() + File.separator + "custom-validation";

            File folder = new File(folderName);

            // Check if the folder exists
            if (folder.exists()) {
                return;
            }

            folder.mkdir();

            String moduleName = textListTextArea[0].trim().replace(" ", "-").toLowerCase();
            String validationName = textListTextArea[1].trim().replace(" ", "-").toLowerCase();
            String snakeCaseValidationName = textListTextArea[0].trim().replace(" ", "_").toLowerCase();
            String fileName = folderName + File.separator + snakeCaseValidationName + "_validation.go";
            List<String> validationStrings = new ArrayList<>();
            for(int i = 2; i< textListTextArea.length; i++){
                if(textListTextArea[i].trim().isBlank())
                    continue;
                validationStrings.add(textListTextArea[i].trim().split("\\s+")[0]);
            }
//
            FileWriterHelper.readAndWriteFromStorageFileToCustomValidationFile(NounGermanFilePathConstants.RESOURCE_CUSTOM_VALIDATION_PATH,
                    fileName, validationName, moduleName, validationStrings);
            CustomPopUp.showPopUpMessage(frame, "Files created successfully");
        });

        add(generateButton);
    }



    private boolean verifyGenerateButtonClickable(){
        String nameFromRepo = ProjectNameRepo.getProjectName();
        return (nameFromRepo != null && !pathLabel.getText().equals(noPath));
    }

    void panelFeatures() {
        setSize(new Dimension(width, height));
        setLayout(null);
        setOpaque(false);
    }
}


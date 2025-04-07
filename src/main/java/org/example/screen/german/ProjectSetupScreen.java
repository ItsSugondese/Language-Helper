package org.example.screen.german;

import org.example.MainFrame;
import org.example.constants.filepath.german.noun.NounGermanFilePathConstants;
import org.example.constants.screen.ScreenConstants;
import org.example.constants.variables.VariableConstants;
import org.example.enums.LanguageNameEnums;
import org.example.repository.german.modulepath.ModulePathRepo;
import org.example.repository.german.projectname.ProjectNameRepo;
import org.example.utils.ActionPerformer;
import org.example.utils.helper.FileWriterHelper;
import org.example.utils.helper.ProjectCopyHelper;
import org.example.utils.uihelper.CustomPopUp;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class ProjectSetupScreen extends JPanel {

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

    public ProjectSetupScreen(MainFrame frame, int width, int height){

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

        generateButtonInit();
        packageGeneratedTextAreaInit();
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
                    Files.write(Paths.get(NounGermanFilePathConstants.MODULE_PATH), Arrays.asList(path));
                    pathLabel.setText(path);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println("Selected folder: " + selectedFolder.getAbsolutePath());
            }
        });

        add(pathSelectorButton);
    }

    void generateButtonInit(){
        int generateButtonWidth = 120;
        generateButton = new JButton("Generate");
        generateButton.setBounds((pathSelectorButton.getX() + pathSelectorButton.getWidth()/2) - generateButtonWidth/2, pathSelectorButton.getY() + pathSelectorButton.getHeight() + 30,
                generateButtonWidth, buttonHeight);
        generateButton.setEnabled(verifyGenerateButtonClickable());

        generateButton.addActionListener(e -> {
            generateButton.setEnabled(false);
            try {
                ProjectCopyHelper.copyAndModifyFiles(NounGermanFilePathConstants.PROJECT_CODE_PATH, ModulePathRepo.getModulePath(LanguageNameEnums.GOLANG), ProjectNameRepo.getProjectName());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            String packaging = FileWriterHelper.readAndWriteFromPackagesStorageFileToTextArea(NounGermanFilePathConstants.RESOURCE_PROJECT_PACKAGE_PATH);
            packageGeneratedTextArea.setText(packaging);
            CustomPopUp.showPopUpMessage(frame, "Files created successfully");
            generateButton.setEnabled(true);
        });

        add(generateButton);
    }

    void packageGeneratedTextAreaInit(){
        packageGeneratedTextArea = new JTextPane();
        packageGeneratedTextArea.setBorder((new LineBorder(Color.BLACK)));

        // Set JTextArea alignment
        packageGeneratedTextArea.setMargin(new Insets(10, 10, 10, 10));
        scrollPane = new JScrollPane(packageGeneratedTextArea);
        scrollPane.setBounds(10, generateButton.getY() + generateButton.getHeight() + 30, width - 20, height/2);
        add(scrollPane);
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


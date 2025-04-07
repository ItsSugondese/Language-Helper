package org.example.screen.german;

import org.example.MainFrame;
import org.example.constants.filepath.german.GermanFilePathConstants;
import org.example.global.parentclass.MenuGlobalParent;
import org.example.global.parentclass.german.GlobalGermanParent;
import org.example.utils.helper.FileWriterHelper;
import org.example.utils.uihelper.CustomPopUp;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class NounScreen extends MenuGlobalParent {

    private JLabel pathLabel;

    private JButton pathSelectorButton;
    private JButton generateButton;


    private JTextPane packageGeneratedTextArea;
    private JScrollPane scrollPane;

    private String noPath = "No Path Specified";


    public NounScreen(MainFrame frame, int width, int height){
        super(frame, width, height);


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

    void pathLabelInit(){
        String path = "";

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
            String selectedPath = "";
            JFileChooser fileChooser = new JFileChooser(selectedPath == null? "" : selectedPath);
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Allow only directories to be selected
            fileChooser.setAcceptAllFileFilterUsed(false); // Disable the "All files" option

            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFolder = fileChooser.getSelectedFile();
                // Write lines to the file, creating it if it doesn't exist, and appending if it does
                try {
                    String path = selectedFolder.getAbsolutePath();
                    Files.write(Paths.get(GermanFilePathConstants.MODULE_PATH), Arrays.asList(path));
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
//            try {
//                ProjectCopyHelper.copyAndModifyFiles(GermanFilePathConstants.PROJECT_CODE_PATH, ModulePathRepo.getModulePath(LanguageNameEnums.GOLANG), ProjectNameRepo.getProjectName());
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }

            String packaging = FileWriterHelper.readAndWriteFromPackagesStorageFileToTextArea(GermanFilePathConstants.RESOURCE_PROJECT_PACKAGE_PATH);
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
        String nameFromRepo = "ProjectNameRepo.getProjectName()";
        return (nameFromRepo != null && !pathLabel.getText().equals(noPath));
    }
}


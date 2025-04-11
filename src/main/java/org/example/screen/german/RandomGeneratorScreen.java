package org.example.screen.german;

import org.example.MainFrame;
import org.example.constants.filepath.german.GermanFilePathConstants;
import org.example.constants.screen.ScreenConstants;
import org.example.global.parentclass.MaterialParent;
import org.example.repository.german.generic.GenericRepo;
import org.example.utils.ActionPerformer;
import org.example.utils.uihelper.CustomTextAreaDialog;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class RandomGeneratorScreen extends MaterialParent {

    private JButton insertValuesButton;
    private JButton fileFromPathButton;



    public RandomGeneratorScreen(MainFrame frame, int width, int height) {
        super(frame, width, height);

    }

    @Override
    protected void initilizer() {
        super.initilizer();


    }

    @Override
    protected void materials() {
        super.materials();
        insertValuesButtonInit();
        fileFromPathButtonInit();
    }

    protected void insertValuesButtonInit(){
        insertValuesButton = new JButton("Insert Values");
        insertValuesButton.setBounds(width / 2 - buttonWidth * 2 - buttonMargin, scoreLabel.getY() + scoreLabel.getHeight() + 10,
                buttonWidth * 2, buttonHeight);

        insertValuesButton.addActionListener(e -> {
            String valuesFromDialog = CustomTextAreaDialog.showCustomDialog(frame, "Insert Text to randomize", mergeStringFromList(genericValuesList));
            if(valuesFromDialog != null && !valuesFromDialog.isBlank()) {
                genericValuesList = new ArrayList<>(Arrays.asList(valuesFromDialog.split("\n")));
                whenClickButton(0);
            } else {
                genericValuesList.clear();
            }

            whenValuesInInsertValueList();
        });

        add(insertValuesButton);
    }

    protected void fileFromPathButtonInit(){
        fileFromPathButton = new JButton("From Path");
        fileFromPathButton.setBounds(width / 2 + buttonMargin, insertValuesButton.getY(),
                buttonWidth * 2, buttonHeight);

        fileFromPathButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(GermanFilePathConstants.SAMPLE_FOLDER_PATH);
            fileChooser.setAcceptAllFileFilterUsed(false); // Disable the "All files" option

            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFolder = fileChooser.getSelectedFile();
                genericValuesList = GenericRepo.getAllFromFileAsList(selectedFolder.getPath());
                whenValuesInInsertValueList();
            }
        });

        add(fileFromPathButton);
    }





    @Override
    protected ActionPerformer backButtonPathSetter(String path) {
        return new ActionPerformer(frame, ScreenConstants.GERMAN_HOME_PAGE);
    }


}


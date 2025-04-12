package org.example.screen.german;

import org.example.MainFrame;
import org.example.constants.DelimiterConstants;
import org.example.constants.filepath.german.GermanFilePathConstants;
import org.example.constants.screen.ScreenConstants;
import org.example.global.parentclass.MaterialParent;
import org.example.repository.german.generic.GenericRepo;
import org.example.utils.ActionPerformer;
import org.example.utils.StringUtils;
import org.example.utils.uihelper.CustomTextAreaDialog;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Override
    protected void conclusion() {
        super.conclusion();
        whenValuesInInsertValueList();
    }

    protected void insertValuesButtonInit(){
        insertValuesButton = new JButton("Insert Values");
        insertValuesButton.setBounds(width / 2 - buttonWidth * 2 - buttonMargin, scoreLabel.getY() + scoreLabel.getHeight() + 10,
                buttonWidth * 2, buttonHeight);

        insertValuesButton.addActionListener(e -> {
            String valuesFromDialog = CustomTextAreaDialog.showCustomDialog(frame, "Insert Text to randomize", StringUtils.mergeStringFromList(genericValuesList, DelimiterConstants.lineBreak));
            if(valuesFromDialog != null && !valuesFromDialog.isBlank()) {
                genericValuesList = StringUtils.listFromString(valuesFromDialog, DelimiterConstants.lineBreak);
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
            fileChooser.setAcceptAllFileFilterUsed(false); // Disable "All files"
            fileChooser.setMultiSelectionEnabled(true); // ✅ Enable multiple selection

            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File[] selectedFiles = fileChooser.getSelectedFiles(); // ✅ Get selected files

                List<String> allValues = new ArrayList<>();
                for (File selectedFile : selectedFiles) {
                    allValues.addAll(GenericRepo.getAllFromFileAsList(selectedFile.getPath()));
                }

                genericValuesList = allValues;
                whenValuesInInsertValueList();
            }
        });


        add(fileFromPathButton);
    }


    protected void whenValuesInInsertValueList(){
        if (!genericValuesList.isEmpty()){
            correctButton.setEnabled(true);
            incorrectButton.setEnabled(true);
        } else {
            correctButton.setEnabled(false);
            incorrectButton.setEnabled(false);
        }
        setFormattedTotalWordLabel();
        whenClickButton(0);
    }




    @Override
    protected ActionPerformer backButtonPathSetter(String path) {
        return new ActionPerformer(frame, ScreenConstants.GERMAN_HOME_PAGE);
    }


}


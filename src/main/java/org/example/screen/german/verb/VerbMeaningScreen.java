package org.example.screen.german.verb;

import org.example.MainFrame;
import org.example.constants.screen.german.GermanScreenConstants;
import org.example.repository.german.verb.VerbRepo;
import org.example.utils.ActionPerformer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VerbMeaningScreen extends VerbParent {

    public VerbMeaningScreen(MainFrame frame, int width, int height) throws Exception {
        super(frame, width, height);
    }
//    private JButton generateButton;
//    private JButton saveButton;
//
//    private JLabel verbEnglishLabel;
//    private JTextField verbTextField;
//
//    List<String> verbs;
//
//
//    @Override
//    protected void initilizer() {
//        super.initilizer();
//        verbs = VerbRepo.getAllVerbsFromFile();
//    }
//
//    @Override
//    protected void materials() {
//        //for golang button declaration, properties and panel adding
//        backButtonInit();
//
//
//        //labels
//
//
//        verbTextFieldInit();
//        verbEnglishLabelInit();
//        generateButtonInit();
//        saveButtonInit();
//
//    }
//
//    protected void verbTextFieldInit() {
//        verbTextField = new JTextField();
//        verbTextField.setHorizontalAlignment(SwingConstants.CENTER);
//        verbTextField.setFont(verbTextField.getFont().deriveFont(Font.BOLD, 16f)); // 16f = font size
//        verbTextField.setBounds(width / 2 - buttonWidth * 2 - buttonMargin / 2, height / 2 - 15, buttonWidth * 4 + buttonMargin, buttonHeight * 2);
//
//        add(verbTextField);
//    }
//
//    protected void verbEnglishLabelInit(){
//        verbEnglishLabel = new JLabel();
//        verbEnglishLabel.setBounds(width / 2 - labelWidth / 2, verbTextField.getY() - buttonHeight - 10,
//                labelWidth, buttonHeight);
//        add(verbEnglishLabel);
//    }
//
//
//    protected void generateButtonInit() {
//        generateButton = new JButton("Generate");
//        generateButton.setBounds(width / 2 - buttonWidth - buttonMargin, verbTextField.getY() + verbTextField.getHeight() + 10,
//                buttonWidth, buttonHeight);
//
//        generateButton.addActionListener(e -> {
//            score++;
//            whenClickButton(score);
//        });
//
//        add(generateButton);
//    }
//
//    protected void saveButtonInit() {
//        saveButton = new JButton("Save");
//        saveButton.setBounds(width / 2 + +buttonMargin, verbTextField.getY() + verbTextField.getHeight() + 10,
//                buttonWidth, buttonHeight);
//
//        saveButton.addActionListener(e -> {
//            score = 0;
//            whenClickButton(score);
//        });
//
//        add(saveButton);
//    }
//
//    @Override
//    protected ActionPerformer backButtonPathSetter(String path) {
//        return new ActionPerformer(frame, GermanScreenConstants.VERB_PAGE);
//    }
//
//    @Override
//    public void setLabelWidth(int labelWidth) {
//        super.setLabelWidth(200);
//    }
}


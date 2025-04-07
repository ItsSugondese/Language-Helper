package org.example.screen.german.verb;

import org.example.MainFrame;
import org.example.repository.german.verb.VerbRepo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RandomVerbFromInternetScreen extends RandomVerbParent {

    private JButton correctButton;
    private JButton incorrectButton;

    private JTextField moduleNameTextField;

    List<String> verbs;

    public RandomVerbFromInternetScreen(MainFrame frame, int width, int height) {
        super(frame, width, height);


        verbs = VerbRepo.getAllVerbsFromFile();

        //this method contains all the components like label and buttons declaration
        materials();

    }

    void materials() {
        //for golang button declaration, properties and panel adding
        backButtonInit();


        //labels


        moduleNameTextFieldInit();
        correctButtonInit();
        incorrectButtonInit();

    }

    protected void moduleNameTextFieldInit() {
        moduleNameTextField = new JTextField();
        moduleNameTextField.setHorizontalAlignment(SwingConstants.CENTER);
        moduleNameTextField.setEnabled(false);
        moduleNameTextField.setDisabledTextColor(Color.BLACK);
        moduleNameTextField.setBackground(Color.WHITE);
        moduleNameTextField.setFont(moduleNameTextField.getFont().deriveFont(Font.BOLD, 16f)); // 16f = font size
        moduleNameTextField.setBounds(width / 2 - buttonWidth * 2 - buttonMargin / 2, height / 2 - 15, buttonWidth * 4 + buttonMargin, buttonHeight * 2);

        if (!verbs.isEmpty())
            moduleNameTextField.setText(verbs.get(generateRandomNumber(verbs.size())));


        add(moduleNameTextField);
    }


    protected void correctButtonInit() {
        correctButton = new JButton("Correct");
        correctButton.setBounds(width / 2 - buttonWidth - buttonMargin, moduleNameTextField.getY() + moduleNameTextField.getHeight() + 10,
                buttonWidth, buttonHeight);

        correctButton.addActionListener(e -> {
            score++;
            whenClickButton(score);
        });

        add(correctButton);
    }

    protected void incorrectButtonInit() {
        incorrectButton = new JButton("Incorrect");
        incorrectButton.setBounds(width / 2 + +buttonMargin, moduleNameTextField.getY() + moduleNameTextField.getHeight() + 10,
                buttonWidth, buttonHeight);

        incorrectButton.addActionListener(e -> {
            score = 0;
            whenClickButton(score);
        });

        add(incorrectButton);
    }

    @Override
    protected void whenClickButton(int scoreNumber) {
        moduleNameTextField.setText(verbs.get(generateRandomNumber(verbs.size())));
        scoreLabel.setText(String.format(scoreLabelTemplate, scoreNumber));
    }
}


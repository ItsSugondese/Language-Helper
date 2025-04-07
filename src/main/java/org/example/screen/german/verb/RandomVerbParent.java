package org.example.screen.german.verb;

import org.example.MainFrame;
import org.example.global.parentclass.german.GlobalGermanParent;

import javax.swing.*;
import java.util.Random;

public class RandomVerbParent extends GlobalGermanParent {

    protected JLabel scoreLabel;
    protected String scoreLabelTemplate = "<html><b>Score: %d</b></html>";
    int score = 0;
    int buttonMargin = 10;

    Random random = new Random();


    public RandomVerbParent(MainFrame frame, int width, int height) {
        super(frame, width, height);

        scoreLabelInit();

    }

    protected void scoreLabelInit() {
        int labelWidth = 100;
        scoreLabel = new JLabel(String.format(scoreLabelTemplate, score));


        scoreLabel.setBounds(width / 2 - labelWidth / 2, backButton.getY() + backButton.getHeight() + 10,
                labelWidth, buttonHeight);
        add(scoreLabel);
    }

    protected int generateRandomNumber(int till) {
        return random.nextInt(till);
    }

    protected void whenClickButton(int scoreNumber) {
        scoreLabel.setText(String.format(scoreLabelTemplate, scoreNumber));
    }
}

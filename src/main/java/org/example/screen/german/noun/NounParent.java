package org.example.screen.german.noun;

import lombok.Getter;
import lombok.Setter;
import org.example.MainFrame;
import org.example.global.parentclass.german.GlobalGermanParent;

import javax.swing.*;
import java.util.Random;

@Getter
@Setter
public class NounParent extends GlobalGermanParent {

    protected JLabel scoreLabel;
    protected String scoreLabelTemplate;
    protected int score = 0;
    protected int buttonMargin;
    protected int labelWidth;


    public NounParent(MainFrame frame, int width, int height) {
        super(frame, width, height);
    }

    @Override
    protected void initilizer() {
        setLabelWidth(120);
        random = new Random();
        buttonMargin = 10;
        scoreLabelTemplate = "<html><b>Score: %d</b></html>";
    }

    protected void scoreLabelInit() {
        scoreLabel = new JLabel(String.format(scoreLabelTemplate, score));
        scoreLabel.setBounds(width / 2 - labelWidth / 2, backButton.getY() + backButton.getHeight() + 10,
                labelWidth, buttonHeight);
        add(scoreLabel);
    }

    protected void whenClickButton(int scoreNumber) {
        scoreLabel.setText(String.format(scoreLabelTemplate, scoreNumber));
    }
}

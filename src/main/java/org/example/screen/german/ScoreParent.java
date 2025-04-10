package org.example.screen.german;

import lombok.Getter;
import lombok.Setter;
import org.example.MainFrame;
import org.example.enums.WordType;
import org.example.global.parentclass.GlobalParent;

import javax.swing.*;
import java.util.Random;

@Getter
@Setter
public class ScoreParent extends GlobalParent {


    protected JLabel scoreLabel;
    protected String scoreLabelTemplate;
    protected int score = 0;
    protected int buttonMargin;
    protected int labelWidth;

    public ScoreParent(MainFrame frame, int width, int height) {
        super(frame, width, height);
    }


    @Override
    protected void initilizer() {
        setLabelWidth(120);
        random = new Random();
        buttonMargin = 10;
        scoreLabelTemplate = "<html><b>Score: %d</b></html>";

        scoreLabelInit();

    }

    protected String getWordFromCombineWord(String combineWord, WordType wordType) {
        String[] splitWord = combineWord.split("\\|");
        if (wordType == WordType.NOUN || wordType == WordType.VERB || wordType == WordType.RANDOM) {
            return splitWord[0].trim();
        } else if (wordType == WordType.ENGLISH) {
            return splitWord[1].trim();
        }
        return null;
    }

    protected void scoreLabelInit() {
        scoreLabel = new JLabel(String.format(scoreLabelTemplate, score));
        scoreLabel.setBounds(width / 2 - labelWidth / 2, backButton.getY() + backButton.getHeight() + 10,
                labelWidth, buttonHeight);
        add(scoreLabel);
    }

    protected void whenClickButton(int scoreNumber) {
        setFormattedScoreLabel(scoreNumber);
    }

    protected void setFormattedScoreLabel(int scoreNumber) {
        scoreLabel.setText(String.format(scoreLabelTemplate, scoreNumber));
    }
}

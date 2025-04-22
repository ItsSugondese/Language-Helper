package org.example.global.parentclass;

import org.example.MainFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TotalWordsParent extends GlobalParent{
    protected JLabel totalWordLabel;
    protected String totalWordLabelTemplate;

    protected int randomNum;

    protected JLabel currentIndexLabel;
    protected String currentIndexLabelTemplate;
    protected List<String> genericValuesList;

    public TotalWordsParent(MainFrame frame, int width, int height) throws Exception {
        super(frame, width, height);
    }

    @Override
    protected void initilizer() {
        super.initilizer();
        totalWordLabelTemplate = "<html><b>Total Words: %d</b></html>";
        currentIndexLabelTemplate = "<html><b>Index: %d</b></html>";
        genericValuesList = new ArrayList<>();

    }

    @Override
    protected void materials() throws Exception {
        super.materials();
        totalWordLabelInit();
        currentIndexLabelInit();
    }

    protected void totalWordLabelInit() {
        totalWordLabel = new JLabel();
        setFormattedTotalWordLabel();
        totalWordLabel.setBounds(backButton.getX(), backButton.getY() + backButton.getHeight() + 10,
                labelWidth, buttonHeight);
        add(totalWordLabel);
    }

    protected void currentIndexLabelInit() {
        currentIndexLabel = new JLabel();
        setFormattedCurrentIndexLabel();
        currentIndexLabel.setBounds(totalWordLabel.getX(), totalWordLabel.getY() + totalWordLabel.getHeight() + 10,
                labelWidth, buttonHeight);
        add(currentIndexLabel);
    }

    // to set totalWordCount in label
    protected void setFormattedTotalWordLabel() {
        totalWordLabel.setText(String.format(totalWordLabelTemplate, getTillNumberValue()));
    }

    // to set totalWordCount in label
    protected void setFormattedCurrentIndexLabel() {
        currentIndexLabel.setText(String.format(currentIndexLabelTemplate, randomNum + 1));
    }

    @Override
    protected int getTillNumberValue() {
        return genericValuesList.size();
    }
}

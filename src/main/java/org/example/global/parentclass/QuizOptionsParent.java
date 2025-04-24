package org.example.global.parentclass;

import net.miginfocom.swing.MigLayout;
import org.example.MainFrame;
import org.example.utils.misc.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuizOptionsParent extends TotalWordsParent{

    protected Map<JButton, JLabel> buttonLabelMap;
    protected JPanel mainPanel;
    public QuizOptionsParent(MainFrame frame, int width, int height) throws Exception {
        super(frame, width, height);
    }

    @Override
    protected void initilizer() {
        super.initilizer();
        buttonLabelMap = new HashMap<>();
    }

    @Override
    protected void conclusion() {
        super.conclusion();
        int buttonCount = getButtonCount();

        mainPanel = new JPanel(new MigLayout(
                "insets 10",
                "[100][150]20[100][150]",
                "[]10[]10[]"
        ));


        int row = 0;

// Left side
        for (int i = 0; i < buttonCount; i++) {
            JButton button = new JButton("Button " + (i + 1));

            JLabel label = new JLabel("Label " + (i + 1));

            buttonLabelMap.put(button, label);
            button.addActionListener(e -> checkAction(buttonLabelMap.get(button).getText()));
            mainPanel.add(button, "cell 0 " + row);
            mainPanel.add(label, "cell 1 " + row);
            row++;
        }



        setQuizPanelBounds();

        add(mainPanel);
    }

    protected int getButtonCount(){
        return  4;
    }
    protected void setQuizPanelBounds(){
        mainPanel.setBounds(backButton.getX(), backButton.getY() + backButton.getHeight() + 20, width - backButton.getX() * 2, height/2);
    }

    protected void checkAction(String value){
        System.out.println(value);

        for (Map.Entry<JButton, JLabel> entry : buttonLabelMap.entrySet()) {
            JButton button = entry.getKey();
            JLabel label = entry.getValue();

            label.setText("Chekc it out");
        }
    }
}

package org.example.global.parentclass;

import org.example.MainFrame;
import org.example.utils.ActionPerformer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MenuGlobalParent extends GlobalParent{
    protected JLabel asking;
    protected Map<String, String> screenButtonMap;


    public MenuGlobalParent(MainFrame frame, int width, int height) throws Exception {
        super(frame, width, height);
    }

    @Override
    protected void initilizer() {
        super.initilizer();


        initializeButtonDetails();
    }

    @Override
    protected void materials() throws Exception {
        super.materials();

        int buttonIndex = 0;
        JButton lastButton = null;
        for (Map.Entry<String, String> entry : screenButtonMap.entrySet()) {
            String label = entry.getKey();
            String screen = entry.getValue();

            JButton button = new JButton(startHtml + label + endHtml);

            if (buttonIndex == 0) {
                button.setBounds(width / 2 - buttonWidth / 2, height / 2 - buttonHeight * 3, buttonWidth, buttonHeight);
                askLabelInit(button);
            } else {
                button.setBounds(lastButton.getX(), lastButton.getY() + lastButton.getHeight() + 20, buttonWidth, buttonHeight);
            }
            button.addActionListener(new ActionPerformer(frame, screen));
            add(button);

            lastButton = button;
            buttonIndex++;
        }
    }

    protected void initializeButtonDetails(){
        screenButtonMap = new LinkedHashMap<>();
    }

    protected void askLabelInit(JButton button) {
        asking = new JLabel(setAskingLabelText());
        asking.setBounds(button.getX(), button.getY() - 30, 250, 20);
        asking.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(asking);
    }

    protected String setAskingLabelText(){
        return "Select operations:";
    }

    @Override
    public void setButtonWidth(int buttonWidth) {
        super.setButtonWidth(200);
    }

    @Override
    public void setButtonHeight(int buttonHeight) {
        super.setButtonHeight(90);
    }

}

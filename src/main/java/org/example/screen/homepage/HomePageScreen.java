package org.example.screen.homepage;

import org.example.MainFrame;
import org.example.global.parentclass.GlobalParent;
import org.example.global.parentclass.MenuGlobalParent;
import org.example.utils.ActionPerformer;
import org.example.constants.screen.ScreenConstants;

import javax.swing.*;

public class HomePageScreen extends MenuGlobalParent {

    //All the buttons and label
    private JButton germanButton;

    public HomePageScreen(MainFrame frame, int width, int height) throws Exception {

        super(frame, width, height);
    }

    @Override
    protected void materials(){


        //for golang button declaration, properties and panel adding
        germanInit();

        //for asking label declaration, properties and panel adding
        ask();

    }

    void ask(){
        asking = new JLabel(startHtml + "<span> Select Language: </span>" + endHtml);
        asking.setBounds(germanButton.getX(), germanButton.getY()-30, 250, 20);
        add(asking);
    }

    void germanInit(){
        germanButton = new JButton(startHtml + "German" + endHtml);
        germanButton.setBounds(width/2 - buttonWidth/2, height/2 - buttonHeight - 5 , buttonWidth, buttonHeight);
        germanButton.addActionListener(new ActionPerformer(frame, ScreenConstants.GERMAN_HOME_PAGE));
        add(germanButton);
    }

    @Override
    protected boolean haveBackButton() {
        return Boolean.FALSE;
    }
}

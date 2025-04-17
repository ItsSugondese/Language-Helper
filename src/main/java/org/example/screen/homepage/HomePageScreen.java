package org.example.screen.homepage;

import org.example.MainFrame;
import org.example.constants.screen.ScreenConstants;
import org.example.global.parentclass.MenuGlobalParent;

import java.util.Map;

public class HomePageScreen extends MenuGlobalParent {

    public HomePageScreen(MainFrame frame, int width, int height) throws Exception {

        super(frame, width, height);
    }

    @Override
    protected void initializeButtonDetails() {
        screenButtonMap = Map.ofEntries(
                Map.entry("German", ScreenConstants.GERMAN_HOME_PAGE)
        );
    }

    @Override
    protected String setAskingLabelText() {
        return "Select Language:";
    }

    @Override
    protected boolean haveBackButton() {
        return Boolean.FALSE;
    }
}

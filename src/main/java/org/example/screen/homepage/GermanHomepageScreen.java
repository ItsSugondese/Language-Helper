package org.example.screen.homepage;

import org.example.MainFrame;
import org.example.constants.screen.ScreenConstants;
import org.example.constants.screen.german.GermanScreenConstants;
import org.example.global.parentclass.MenuGlobalParent;
import org.example.utils.ActionPerformer;

public class GermanHomepageScreen extends MenuGlobalParent {

    public GermanHomepageScreen(MainFrame frame, int width, int height) throws Exception {
        super(frame, width, height);
        materials();
    }


    @Override
    protected void initializeButtonDetails() {
        super.initializeButtonDetails();
        screenButtonMap.put("All Words", GermanScreenConstants.GERMAN_ALL_WORD_PAGE);
        screenButtonMap.put("Random Generator", GermanScreenConstants.RANDOM_GENERATOR_PAGE);
        screenButtonMap.put("Search Word", GermanScreenConstants.SEARCH_WORD_PAGE);
        screenButtonMap.put("Audio Mover", GermanScreenConstants.AUDIO_MOVER_PAGE);

    }

    @Override
    protected ActionPerformer backButtonPathSetter(String path) {
        return super.backButtonPathSetter(ScreenConstants.HOME_PAGE);
    }

}


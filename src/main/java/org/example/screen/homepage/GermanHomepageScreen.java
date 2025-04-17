package org.example.screen.homepage;

import org.example.MainFrame;
import org.example.constants.screen.ScreenConstants;
import org.example.constants.screen.german.GermanScreenConstants;
import org.example.global.parentclass.MenuGlobalParent;
import org.example.utils.ActionPerformer;

import java.util.Map;

public class GermanHomepageScreen extends MenuGlobalParent {

    public GermanHomepageScreen(MainFrame frame, int width, int height) throws Exception {
        super(frame, width, height);
        materials();
    }


    @Override
    protected void initializeButtonDetails() {
        screenButtonMap = Map.ofEntries(
                Map.entry("All Words", GermanScreenConstants.GERMAN_ALL_WORD_PAGE),
                Map.entry("Random Generator", GermanScreenConstants.RANDOM_GENERATOR_PAGE),
                Map.entry("Audio Mover", GermanScreenConstants.AUDIO_MOVER_PAGE)
        );
    }

    @Override
    protected ActionPerformer backButtonPathSetter(String path) {
        return super.backButtonPathSetter(ScreenConstants.HOME_PAGE);
    }

}


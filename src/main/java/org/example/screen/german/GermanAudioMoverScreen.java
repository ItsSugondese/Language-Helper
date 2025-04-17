package org.example.screen.german;

import org.example.MainFrame;
import org.example.constants.screen.ScreenConstants;
import org.example.global.parentclass.MaterialParent;
import org.example.utils.ActionPerformer;

public class GermanAudioMoverScreen extends MaterialParent {
    public GermanAudioMoverScreen(MainFrame frame, int width, int height) throws Exception {
        super(frame, width, height);
    }

    @Override
    protected ActionPerformer backButtonPathSetter(String path) {
        return super.backButtonPathSetter(ScreenConstants.GERMAN_HOME_PAGE);
    }
}

package org.example.screen.german;

import org.example.MainFrame;
import org.example.constants.filepath.german.GermanFilePathConstants;
import org.example.constants.screen.ScreenConstants;
import org.example.constants.screen.german.GermanScreenConstants;
import org.example.enums.WordScreenType;
import org.example.global.parentclass.MaterialParent;
import org.example.repository.german.generic.GenericRepo;
import org.example.utils.ActionPerformer;

import java.io.File;

public class WFrageScreen extends MaterialParent {
    public WFrageScreen(MainFrame frame, int width, int height) throws Exception {
        super(frame, width, height);
    }

    @Override
    protected void initilizer() {
        super.initilizer();
        genericValuesList = GenericRepo.getAllFromFileAsList(WordScreenType.GERMAN_W_FRAGE.getWordPath() + File.separator + "w-frage");
    }

    @Override
    protected WordScreenType getWordScreenType(){
        return  WordScreenType.GERMAN_W_FRAGE;
    }

    @Override
    protected ActionPerformer backButtonPathSetter(String path) {
        return new ActionPerformer(frame, ScreenConstants.GERMAN_HOME_PAGE);
    }
}

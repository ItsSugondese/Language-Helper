package org.example.screen.german.noun;

import org.example.MainFrame;
import org.example.constants.filepath.german.noun.NounGermanFilePathConstants;
import org.example.constants.screen.german.GermanScreenConstants;
import org.example.enums.WordScreenType;
import org.example.repository.german.generic.GenericRepo;
import org.example.utils.ActionPerformer;

public class RandomNounFromStorageScreen extends NounParent {

    public RandomNounFromStorageScreen(MainFrame frame, int width, int height) throws Exception {
        super(frame, width, height);

    }

    @Override
    protected void initilizer() {
        super.initilizer();
        genericValuesList = GenericRepo.getAllFromFileAsList(NounGermanFilePathConstants.RANDOM_NOUN_PATH);

    }

    @Override
    protected void materials() throws Exception {
        super.materials();
    }

    @Override
    protected ActionPerformer backButtonPathSetter(String path) {
        return new ActionPerformer(frame, GermanScreenConstants.NOUN_PAGE);
    }

    @Override
    protected WordScreenType getWordScreenType(){
        return  WordScreenType.GERMAN_NOUN;
    }
}


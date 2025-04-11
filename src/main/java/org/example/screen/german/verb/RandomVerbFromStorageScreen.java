package org.example.screen.german.verb;

import org.example.MainFrame;
import org.example.constants.filepath.german.verb.VerbGermanFilePathConstants;
import org.example.constants.screen.german.GermanScreenConstants;
import org.example.repository.german.generic.GenericRepo;
import org.example.utils.ActionPerformer;

public class RandomVerbFromStorageScreen extends VerbParent {

    public RandomVerbFromStorageScreen(MainFrame frame, int width, int height) {
        super(frame, width, height);
    }

    @Override
    protected void initilizer() {
        super.initilizer();
        genericValuesList = GenericRepo.getAllFromFileAsList(VerbGermanFilePathConstants.RANDOM_VERB_PATH);

    }

    @Override
    protected void materials() {
        super.materials();
    }

    @Override
    protected ActionPerformer backButtonPathSetter(String path) {
        return new ActionPerformer(frame, GermanScreenConstants.VERB_PAGE);
    }
}


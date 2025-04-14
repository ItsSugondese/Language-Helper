package org.example.screen.german.noun;

import org.example.MainFrame;
import org.example.constants.filepath.german.noun.NounGermanFilePathConstants;
import org.example.constants.screen.german.GermanScreenConstants;
import org.example.enums.WordType;
import org.example.repository.german.generic.GenericRepo;
import org.example.repository.german.noun.NounRepo;
import org.example.utils.ActionPerformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

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
}


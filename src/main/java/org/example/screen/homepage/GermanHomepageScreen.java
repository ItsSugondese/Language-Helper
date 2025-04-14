package org.example.screen.homepage;

import javazoom.jl.player.Player;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.example.ApiGateway;
import org.example.MainFrame;
import org.example.constants.filepath.FilePathConstants;
import org.example.constants.screen.ScreenConstants;
import org.example.constants.screen.german.GermanScreenConstants;
import org.example.global.parentclass.MenuGlobalParent;
import org.example.utils.ActionPerformer;
import org.example.utils.files.FileSaveUtils;
import org.example.utils.misc.AudioUtils;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;

public class GermanHomepageScreen extends MenuGlobalParent {

    private JButton nounButton;
    private JButton verbButton;
    private JButton randomGeneratorButton;

    public GermanHomepageScreen(MainFrame frame, int width, int height) throws Exception {
        super(frame, width, height);
        materials();
    }

    @Override
    protected void materials(){
        nounButtonInit();
        askLabelInit();
        verbButtonInit();
        randomGeneratorButtonInit();
    }

    void askLabelInit(){
        asking = new JLabel(startHtml + "<span> Select operations: </span>" + endHtml);
        asking.setBounds(nounButton.getX(), nounButton.getY()-30, 250, 20);
        add(asking);
    }

    void nounButtonInit(){
        nounButton = new JButton(startHtml + "Noun" + endHtml);
        nounButton.setBounds(width/2 - buttonWidth/2, height/2 - buttonHeight - 5 , buttonWidth, buttonHeight);
        nounButton.addActionListener(new ActionPerformer(frame, GermanScreenConstants.NOUN_PAGE));
        add(nounButton);
    }

    void verbButtonInit(){
        verbButton = new JButton(startHtml + "Verb" + endHtml);
        verbButton.setBounds(nounButton.getX(), nounButton.getY() + nounButton.getHeight() + 20 , buttonWidth, buttonHeight);
        verbButton.addActionListener(new ActionPerformer(frame, GermanScreenConstants.VERB_PAGE));
        add(verbButton);
    }

    void randomGeneratorButtonInit(){
        randomGeneratorButton = new JButton(startHtml + "Random Generator" + endHtml);
        randomGeneratorButton.setBounds(verbButton.getX(), verbButton.getY() + verbButton.getHeight() + 20 , buttonWidth, buttonHeight);
        randomGeneratorButton.addActionListener(new ActionPerformer(frame, GermanScreenConstants.RANDOM_GENERATOR_PAGE));
        add(randomGeneratorButton);
    }

    @Override
    protected ActionPerformer backButtonPathSetter(String path) {
        return new ActionPerformer(frame, ScreenConstants.HOME_PAGE);
    }








}


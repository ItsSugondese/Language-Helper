package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.constants.screen.ScreenConstants;
import org.example.constants.screen.german.GermanScreenConstants;
import org.example.screen.german.*;
import org.example.screen.german.noun.RandomNounFromStorageScreen;
import org.example.screen.german.verb.VerbMeaningScreen;
import org.example.screen.german.verb.RandomVerbFromStorageScreen;
import org.example.screen.homepage.AudioLoaderScreen;
import org.example.screen.homepage.GermanHomepageScreen;
import org.example.screen.homepage.HomePageScreen;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MainFrame extends JFrame {
    private HomePageScreen homePageScreen;
    private GermanHomepageScreen germanHomepageScreen;
    private AudioLoaderScreen audioLoaderScreen;


    // verb
    private VerbScreen verbScreen;
    private NounScreen nounScreen;
    private GermanMiscScreen germanMiscScreen;
    private RandomGeneratorScreen randomGeneratorScreen;

    private RandomVerbFromStorageScreen randomVerbFromStorageScreen;
    private VerbMeaningScreen verbMeaningScreen;

    private RandomNounFromStorageScreen randomNounFromStorageScreen;

    private WFrageScreen wFrageScreen;

    private CardLayout cardLayout;
    private JPanel mainPanel;

    //for dimension of frame
    private int width, height;


    public MainFrame(int width, int height) throws Exception {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Setting the dimensions for the frame
        this.width = width;
        this.height = height;

        // Creating and adding the homepage panel
        homePageScreen = new HomePageScreen(this, width, height);
        homePageScreen.setName(ScreenConstants.HOME_PAGE); // Set the name to match the constraint
        mainPanel.add(homePageScreen, ScreenConstants.HOME_PAGE);

        germanHomepageScreen = new GermanHomepageScreen(this, width, height);
        germanHomepageScreen.setName(ScreenConstants.GERMAN_HOME_PAGE); // Set name
        mainPanel.add(germanHomepageScreen, ScreenConstants.GERMAN_HOME_PAGE);

        audioLoaderScreen = new AudioLoaderScreen(this, width, height);
        audioLoaderScreen.setName(ScreenConstants.AUDIO_LOADER_PAGE); // Set name
        mainPanel.add(audioLoaderScreen, ScreenConstants.AUDIO_LOADER_PAGE);

        // German panels
        nounScreen = new NounScreen(this, width, height);
        nounScreen.setName(GermanScreenConstants.NOUN_PAGE); // Set name
        mainPanel.add(nounScreen, GermanScreenConstants.NOUN_PAGE);

        verbScreen = new VerbScreen(this, width, height);
        verbScreen.setName(GermanScreenConstants.VERB_PAGE); // Set name
        mainPanel.add(verbScreen, GermanScreenConstants.VERB_PAGE);

        germanMiscScreen = new GermanMiscScreen(this, width, height);
        germanMiscScreen.setName(GermanScreenConstants.GERMAN_MISC_PAGE); // Set name
        mainPanel.add(germanMiscScreen, GermanScreenConstants.GERMAN_MISC_PAGE);

        randomGeneratorScreen = new RandomGeneratorScreen(this, width, height);
        randomGeneratorScreen.setName(GermanScreenConstants.RANDOM_GENERATOR_PAGE); // Set name
        mainPanel.add(randomGeneratorScreen, GermanScreenConstants.RANDOM_GENERATOR_PAGE);

        randomVerbFromStorageScreen = new RandomVerbFromStorageScreen(this, width, height);
        randomVerbFromStorageScreen.setName(GermanScreenConstants.RANDOM_VERB_PAGE); // Set name
        mainPanel.add(randomVerbFromStorageScreen, GermanScreenConstants.RANDOM_VERB_PAGE);

        verbMeaningScreen = new VerbMeaningScreen(this, width, height);
        verbMeaningScreen.setName(GermanScreenConstants.VERB_MEANING_PAGE); // Set name
        mainPanel.add(verbMeaningScreen, GermanScreenConstants.VERB_MEANING_PAGE);

        randomNounFromStorageScreen = new RandomNounFromStorageScreen(this, width, height);
        randomNounFromStorageScreen.setName(GermanScreenConstants.RANDOM_NOUN_PAGE); // Set name
        mainPanel.add(randomNounFromStorageScreen, GermanScreenConstants.RANDOM_NOUN_PAGE);

        wFrageScreen = new WFrageScreen(this, width, height);
        wFrageScreen.setName(GermanScreenConstants.W_FRAGE_PAGE); // Set name
        mainPanel.add(wFrageScreen, GermanScreenConstants.W_FRAGE_PAGE);

        // Call this method for general frame setup
        frameFeatures();

        // Add the mainPanel to the frame
        add(mainPanel);

        // Initially show the start page
//        cardLayout.show(mainPanel, ScreenConstants.AUDIO_LOADER_PAGE);
        cardLayout.show(mainPanel, GermanScreenConstants.GERMAN_MISC_PAGE);

        // Set frame visibility to true
        setVisible(true);
    }


    void frameFeatures() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(width, height));
        setResizable(false);
        setTitle("Boiler plate Generator");
    }


}

package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.constants.screen.ScreenConstants;
import org.example.constants.screen.german.GermanScreenConstants;
import org.example.screen.german.NounScreen;
import org.example.screen.german.RandomGeneratorScreen;
import org.example.screen.german.VerbScreen;
import org.example.screen.german.noun.RandomNounFromStorageScreen;
import org.example.screen.german.verb.VerbMeaningScreen;
import org.example.screen.german.verb.RandomVerbFromStorageScreen;
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


    // verb
    private VerbScreen verbScreen;
    private NounScreen nounScreen;
    private RandomGeneratorScreen randomGeneratorScreen;

    private RandomVerbFromStorageScreen randomVerbFromStorageScreen;
    private VerbMeaningScreen verbMeaningScreen;

    private RandomNounFromStorageScreen randomNounFromStorageScreen;

    private CardLayout cardLayout;
    private JPanel mainPanel;

    //for dimension of frame
    private int width, height;

    public MainFrame(int width, int height) {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        //setting value in variable to use in dimension

        this.width = width;
        this.height = height;

        //creating homepage class object and initializing mainframe object, width and height value
        homePageScreen = new HomePageScreen(this, width, height);
        mainPanel.add(homePageScreen, ScreenConstants.HOME_PAGE);
        germanHomepageScreen = new GermanHomepageScreen(this, width, height);
        mainPanel.add(germanHomepageScreen, ScreenConstants.GERMAN_HOME_PAGE);


        // german
        nounScreen = new NounScreen(this, width, height);
        mainPanel.add(nounScreen, GermanScreenConstants.NOUN_PAGE);
        verbScreen = new VerbScreen(this, width, height);
        mainPanel.add(verbScreen, GermanScreenConstants.VERB_PAGE);
        randomGeneratorScreen = new RandomGeneratorScreen(this, width, height);
        mainPanel.add(randomGeneratorScreen, GermanScreenConstants.RANDOM_GENERATOR_PAGE);

        randomVerbFromStorageScreen = new RandomVerbFromStorageScreen(this, width, height);
        mainPanel.add(randomVerbFromStorageScreen, GermanScreenConstants.RANDOM_VERB_PAGE);
        verbMeaningScreen = new VerbMeaningScreen(this, width, height);
        mainPanel.add(verbMeaningScreen, GermanScreenConstants.VERB_MEANING_PAGE);

        randomNounFromStorageScreen = new RandomNounFromStorageScreen(this, width, height);
        mainPanel.add(randomNounFromStorageScreen, GermanScreenConstants.RANDOM_NOUN_PAGE);


        //this method contains all the features of JFrame
        frameFeatures();

        //adding homepage panel object in frame


        add(mainPanel);

        // Initially show the startup panel
        cardLayout.show(mainPanel, ScreenConstants.GERMAN_HOME_PAGE);
//        cardLayout.show(mainPanel, GermanScreenConstants.RANDOM_GENERATOR_PAGE);

        //setting frame visibility to true
        setVisible(true);
    }

    void frameFeatures() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(width, height));
        setResizable(false);
        setTitle("Boiler plate Generator");
    }


}

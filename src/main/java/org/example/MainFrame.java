package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.constants.screen.ScreenConstants;
import org.example.constants.screen.german.GermanScreenConstants;
import org.example.screen.german.GermanAllWordScreen;
import org.example.screen.german.GermanAudioMoverScreen;
import org.example.screen.german.RandomGeneratorScreen;
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

    private GermanAllWordScreen germanAllWordScreen;
    private RandomGeneratorScreen randomGeneratorScreen;
    private GermanAudioMoverScreen germanAudioMoverScreen;

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

        // German panels
        germanAllWordScreen = new GermanAllWordScreen(this, width, height);
        germanAllWordScreen.setName(GermanScreenConstants.GERMAN_ALL_WORD_PAGE); // Set name
        mainPanel.add(germanAllWordScreen, GermanScreenConstants.GERMAN_ALL_WORD_PAGE);

        randomGeneratorScreen = new RandomGeneratorScreen(this, width, height);
        randomGeneratorScreen.setName(GermanScreenConstants.RANDOM_GENERATOR_PAGE); // Set name
        mainPanel.add(randomGeneratorScreen, GermanScreenConstants.RANDOM_GENERATOR_PAGE);

        germanAudioMoverScreen = new GermanAudioMoverScreen(this, width, height);
        germanAudioMoverScreen.setName(GermanScreenConstants.AUDIO_MOVER_PAGE); // Set name
        mainPanel.add(germanAudioMoverScreen, GermanScreenConstants.AUDIO_MOVER_PAGE);

        // Call this method for general frame setup
        frameFeatures();

        // Add the mainPanel to the frame
        add(mainPanel);

        // Initially show the start page
//        cardLayout.show(mainPanel, ScreenConstants.GERMAN_HOME_PAGE);
        cardLayout.show(mainPanel, GermanScreenConstants.AUDIO_MOVER_PAGE);

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

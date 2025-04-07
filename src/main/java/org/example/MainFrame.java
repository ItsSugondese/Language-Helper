package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.constants.screen.ScreenConstants;
import org.example.screen.german.*;
import org.example.screen.german.api.GetApiGenerateScreen;
import org.example.screen.german.api.PaginateApiGenerateScreen;
import org.example.screen.german.api.SaveApiGenerateScreen;
import org.example.screen.homepage.AngularHomepageScreen;
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
    private AngularHomepageScreen angularHomepageScreen;


    // golang
    private MakePackageScreen makePackageScreen;
    private ApiScreen apiScreen;
    private MakeEnumScreen makeEnumScreen;
    private CustomValidationScreen customValidationScreen;
    private ProjectSetupScreen projectSetupScreen;

    //sub golang
    private SaveApiGenerateScreen saveApiGenerateScreen;
    private GetApiGenerateScreen getApiGenerateScreen;
    private PaginateApiGenerateScreen paginateApiGenerateScreen;




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


//        // golang
//        makePackageScreen = new MakePackageScreen(this, width, height);
//        mainPanel.add(makePackageScreen, ScreenConstants.MAKE_PACKAGE_GOLANG);
//        apiScreen = new ApiScreen(this, width, height);
//        mainPanel.add(apiScreen, ScreenConstants.API_GOLANG);
//        makeEnumScreen = new MakeEnumScreen(this, width, height);
//        mainPanel.add(makeEnumScreen, ScreenConstants.MAKE_ENUM_GOLANG);
//        customValidationScreen = new CustomValidationScreen(this, width, height);
//        mainPanel.add(customValidationScreen, ScreenConstants.CUSTOM_VALIDATION_GOLANG);
//        projectSetupScreen = new ProjectSetupScreen(this, width, height);
//        mainPanel.add(projectSetupScreen, ScreenConstants.PROJECT_SETUP_GOLANG);
//
//        // sub golang
//        saveApiGenerateScreen = new SaveApiGenerateScreen(this, width, height);
//        mainPanel.add(saveApiGenerateScreen, ScreenConstants.SAVE_API_GENERATE_GOLANG);
//        getApiGenerateScreen = new GetApiGenerateScreen(this, width, height);
//        mainPanel.add(getApiGenerateScreen, ScreenConstants.GET_API_GENERATE_GOLANG);
//        paginateApiGenerateScreen = new PaginateApiGenerateScreen(this, width, height);
//        mainPanel.add(paginateApiGenerateScreen, ScreenConstants.PAGINATE_API_GENERATE_GOLANG);
//
//
//        // anglular
//        componentGeneratorScreen = new ComponentGeneratorScreen(this, width, height);
//        mainPanel.add(componentGeneratorScreen, ScreenConstants.COMPONENT_GENERATE_ANGULAR);

        //this method contains all the features of JFrame
        frameFeatures();

        //adding homepage panel object in frame


        add(mainPanel);

        // Initially show the startup panel
        cardLayout.show(mainPanel, ScreenConstants.GERMAN_HOME_PAGE);

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

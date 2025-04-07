package org.example.screen.homepage;

import org.example.MainFrame;
import org.example.constants.filepath.german.noun.NounGermanFilePathConstants;
import org.example.constants.variables.VariableConstants;
import org.example.repository.german.projectname.ProjectNameRepo;
import org.example.utils.ActionPerformer;
import org.example.constants.screen.ScreenConstants;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class AngularHomepageScreen extends JPanel {

    //variable for panel dimensions
    private int width, height;

    //variable for sizing buttons
    private int buttonWidth, buttonHeight;

    //instance of MainFrame classMFra
    private MainFrame frame;

    //All the buttons and label
    private JLabel asking;

    private JButton backButton;
    private JButton changeProjectNameButton;
    private JButton componentGeneratorButton;
    private JButton apiButton;
    private JButton makeEnumButton;
    private JButton customValidationButton;
    private JButton setupProjectButton;

    private JTextField projectNameTextField;
    //variable for designing using html
    private String startHtml, endHtml;

    //instance for event Listener
    private ActionPerformer actionPerformer;

    public AngularHomepageScreen(MainFrame frame, int width, int height){

        buttonWidth = 200;
        buttonHeight = 60;

        this.frame = frame;
        this.width = width;
        this.height = height;

        startHtml = "<html> <style> h2{font-family: \"Comic Sans MS\", \"Comic Sans\", cursive;}span{font-family: fantasy}</style> <center><h2>";
        endHtml = "</h2> </center> </html>";

        //this method contains all the panel properties
        panelFeatures();

        //this method contains all the components like label and buttons declaration
        materials();
    }

    void materials(){

        backButtonInit();

        projectNameTextFieldInit();
        changeProjectNameButtonInit();

        //for golang button declaration, properties and panel adding
        componentGeneratorButtonInit();
        askLabelInit();
//
//        apiButtonInit();
//        makeEnumButtonInit();
//        customValidationButtonInit();
//        setupProjectButtonInit();

        //for asking label declaration, properties and panel adding


    }

    void backButtonInit(){
        backButton = new JButton(startHtml + "Back" + endHtml);
        backButton.setBounds(VariableConstants.BACK_BUTTON_X, VariableConstants.BACK_BUTTON_Y, VariableConstants.BACK_BUTTON_WIDTH, VariableConstants.BACK_BUTTON_HEIGHT);
        backButton.addActionListener(new ActionPerformer(frame, ScreenConstants.HOME_PAGE));
        add(backButton);
    }


    void projectNameTextFieldInit(){
        projectNameTextField = new JTextField();
        projectNameTextField.setBounds(width/2 - 100, backButton.getY() + backButton.getHeight() + 10, buttonWidth, buttonHeight/2);

        String projectName = ProjectNameRepo.getProjectName();
        projectNameTextField.setText(projectName != null? projectName.trim() : "");
        // Add DocumentListener to track changes on every keystroke
        projectNameTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLabel();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLabel();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLabel(); // Handles attribute changes, not commonly used for plain text
            }

            private void updateLabel() {
                changeProjectNameButton.setEnabled(verifyChangeProjectNameClickable());
            }
        });
        add(projectNameTextField);
    }
    void changeProjectNameButtonInit(){
        changeProjectNameButton = new JButton("Change Project Name");
        changeProjectNameButton.setBounds(width / 2 - buttonWidth/2, projectNameTextField.getY() + projectNameTextField.getHeight() + 10,
                buttonWidth, buttonHeight/2);
        changeProjectNameButton.addActionListener(e -> {
            String projectName = projectNameTextField.getText().trim();
            try {
                changeProjectNameButton.setEnabled(false);
                Files.write(Paths.get(NounGermanFilePathConstants.PROJECT_NAME_PATH), Arrays.asList(projectName));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }


        });
        String projectName = ProjectNameRepo.getProjectName();

        changeProjectNameButton.setEnabled(verifyChangeProjectNameClickable());

        add(changeProjectNameButton);
    }
    void askLabelInit(){
        asking = new JLabel(startHtml + "<span> Select operations: </span>" + endHtml);
        asking.setBounds(componentGeneratorButton.getX(), componentGeneratorButton.getY()-30, 250, 20);
        add(asking);
    }

    void componentGeneratorButtonInit(){
        componentGeneratorButton = new JButton(startHtml + "Generate Components" + endHtml);
        componentGeneratorButton.setBounds(width/2 - buttonWidth/2, height/2 - buttonHeight - 5 , buttonWidth, buttonHeight);
        componentGeneratorButton.addActionListener(new ActionPerformer(frame, ScreenConstants.COMPONENT_GENERATE_ANGULAR));
        add(componentGeneratorButton);
    }




    private boolean verifyChangeProjectNameClickable(){
        String nameFromRepo = ProjectNameRepo.getProjectName();
        String nameFromTextField = projectNameTextField.getText().trim();
        return (nameFromRepo != null && !nameFromRepo.equals(nameFromTextField)) && (!nameFromTextField.isBlank());
    }


    void panelFeatures() {
        setSize(new Dimension(width, height));
        setLayout(null);
        setOpaque(false);
    }
}


package mvcmodel.view;/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Lily Parker
 * Section: 01
 * Date: 11/5/20
 * Time: 5:51 PM
 *
 * Project: csci205FinalProject
 * Package: PACKAGE_NAME
 * Class: mvcmodel.view.MastermindModeView
 *
 * Description: Class for the second scene of the game
 *
 * *****************************************
 */

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Class for the mode view scene
 */
public class MastermindModeView {

    /** VBox of the root */
    private VBox root;

    /** Button for the easy difficulty */
    private Button easyBtn;

    /** Button for the medium difficulty */
    private Button mediumBtn;

    /** Button for the master difficulty */
    private Button masterBtn;

    /** Button for the master difficulty */
    private Button soundOnOff;

    /**
     * Constructor for the class
     */
    public MastermindModeView() {
        // Set up the root
        setUpRoot();

        // Create Label
        setUpLabel();

        // Create buttons
        setUpButtons();
    }

    /**
     * Method to set up the label
     */
    private void setUpLabel() {
        Label chooseMode = new Label("Choose Mode:");
        chooseMode.setFont(new Font("Arial", 20));
        root.getChildren().add(chooseMode);
        root.setAlignment(Pos.CENTER);
    }

    /**
     * Method to set up the buttons
     */
    private void setUpButtons() {
        root.setSpacing(10);
        easyBtn = new Button("Beginner");
        easyBtn.setStyle("-fx-background-color: #03045E; -fx-text-fill: #FFFFFF; -fx-font-size: 20");
        easyBtn.setPrefSize(200,50);
        root.getChildren().add(easyBtn);
        mediumBtn = new Button("Intermediate");
        mediumBtn.setStyle("-fx-background-color: #0077B6; -fx-text-fill: #FFFFFF; -fx-font-size: 20");
        root.getChildren().add(mediumBtn);
        mediumBtn.setPrefSize(200,50);
        masterBtn = new Button("Master");
        masterBtn.setStyle("-fx-background-color: #00B4D8; -fx-text-fill: #FFFFFF; -fx-font-size: 20");
        root.getChildren().add(masterBtn);
        masterBtn.setPrefSize(200,50);


        // sound button
        soundOnOff = new Button("Click to turn sound on");
        soundOnOff.setStyle("-fx-background-color: #34eb71; -fx-text-fill: #FFFFFF; -fx-font-size: 20");
        root.getChildren().add(soundOnOff);
        soundOnOff.setPrefSize(200,50);
    }

    /**
     * Method to set up the root
     */
    private void setUpRoot() {
        root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setPrefSize(500,500);
        root.setAlignment(Pos.CENTER);
    }

    /**
     * Getter for the easy button
     *
     * @return - Button object of the easy button object
     */
    public Button getEasyBtn() {
        return easyBtn;
    }

    /**
     * Getter for the intermediate button
     *
     * @return - Button object of the intermediate button object
     */
    public Button getMediumBtn() {
        return mediumBtn;
    }

    /**
     * Getter for the master button
     *
     * @return - Button object of the master button object
     */
    public Button getMasterBtn() {
        return masterBtn;
    }

    /**
     * Getter for the root
     *
     * @return - VBox object of the root
     */
    public VBox getRoot() {
        return root;
    }

    public Button getSoundOnOff() {
        return soundOnOff;
    }
}

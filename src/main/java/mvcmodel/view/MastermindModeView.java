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

    /** Button for the single player option */
    private Button singlePlayerBtn;

    /** Button for the multiplayer option */
    private Button multiplayerBtn;

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
        singlePlayerBtn = new Button("Single Player");
        singlePlayerBtn.setStyle("-fx-background-color: #03045E; -fx-text-fill: #FFFFFF; -fx-font-size: 20");
        singlePlayerBtn.setPrefSize(200,50);
        root.getChildren().add(singlePlayerBtn);
        multiplayerBtn = new Button("Multiplayer");
        multiplayerBtn.setStyle("-fx-background-color: #0077B6; -fx-text-fill: #FFFFFF; -fx-font-size: 20");
        root.getChildren().add(multiplayerBtn);
        multiplayerBtn.setPrefSize(200,50);
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
     * Getter for the single player button
     *
     * @return - Button object of the single player button object
     */
    public Button getSinglePlayerBtn() {
        return singlePlayerBtn;
    }

    /**
     * Getter for the multiplayer button
     *
     * @return - Button object of the multiplayer button object
     */
    public Button getMultiplayerBtn() {
        return multiplayerBtn;
    }

    /**
     * Getter for the root
     *
     * @return - VBox object of the root
     */
    public VBox getRoot() {
        return root;
    }
}

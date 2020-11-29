package mvcmodel.view;

/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Cuong Nguyen
            Lily Parker
            Minh Anh Phan
            Anurag Vadiya
 * Section: 01
 * Date: 11/5/20
 * Time: 5:08 PM
 *
 * Project: csci205FinalProject
 * Package: mvcmodel.view
 * Class: mvcmodel.view.MastermindIntroView
 *
 * Description: View for the introductory scene
 *
 * *****************************************
 */

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Class for the introductory view of the game
 */
public class MastermindIntroView {

    /** Root object */
    private VBox root;

    /** TextField for the user's input of their name */
    private TextField nameInput;

    /** Button to play the game */
    private Button playBtn;

    /**
     * Constructor for the class
     */
    public MastermindIntroView() {
        // Set up the root
        setUpRoot();

        // Set up labels
        setUpLabels();

        // Set up the text input
        setUpTextInput();

        // Set up the start button
        setUpBtn();

    }

    /**
     * Method to set up the labels
     */
    private void setUpLabels() {
        Label gameTitle = new Label("MASTERMIND");
        gameTitle.setTextFill(Color.NAVY);
        gameTitle.setId("title");
        root.getChildren().add(gameTitle);
        root.setAlignment(Pos.CENTER);
        Label nameLabel = new Label("Enter Your Name");
        nameLabel.setTextFill(Color.NAVY);
        root.getChildren().add(nameLabel);
    }

    /**
     * Method to set up the button
     */
    private void setUpBtn() {
        playBtn = new Button("START");
        playBtn.setStyle("-fx-background-color: #00B4D8; -fx-font-size: 18");
        playBtn.setPrefSize(100,50);
        root.getChildren().add(playBtn);
    }

    /**
     * Method to set up the text input of the user's name
     */
    private void setUpTextInput() {
        nameInput = new TextField();
        nameInput.setPrefSize(100,30);
        nameInput.setAlignment(Pos.CENTER);
        root.getChildren().add(nameInput);
    }

    /**
     * Method to set up the root of the scene
     */
    private void setUpRoot() {
        root = new VBox(10);
        root.setPrefSize(500,500);
        root.setPadding(new Insets(5));
        root.setMinHeight(300);
        root.setAlignment(Pos.CENTER);
        BackgroundFill backgroundFill = new BackgroundFill(Color.rgb(202,240,248), CornerRadii.EMPTY,Insets.EMPTY);
        Background background = new Background(backgroundFill);
        root.setBackground(background);
    }

    /**
     * Getter for the name input text field
     *
     * @return - TextField object of the name input
     */
    public TextField getNameInput() {
        return nameInput;
    }

    /**
     * Getter for the root
     *
     * @return - the VBox of the root
     */
    public VBox getRoot() {
        return root;
    }

    /** Getter for the play button
     *
     * @return - the play button
     */
    public Button getPlayBtn() {
        return playBtn;
    }
}

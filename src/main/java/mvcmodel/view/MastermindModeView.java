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
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Class for the mode view scene
 */
public class MastermindModeView {

    private Label numPegs;
    private Slider pegsSlider;
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

    private Slider guessesSlider;

    private Label numGuesses;

    public Slider getGuessesSlider() {
        return guessesSlider;
    }

    public Label getNumGuesses() {
        return numGuesses;
    }

    private Button submit;

    public Button getSubmit() {
        return submit;
    }

    public Slider getPegsSlider() {
        return pegsSlider;
    }

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

        setUpCustomGuesses();

        setUpCustomNumPegs();

        setUpSubmitCustomBtn();

        setUpSoundBtn();
    }

    private void setUpSoundBtn() {
        // sound button
        soundOnOff = new Button("Click to turn sound on");
        soundOnOff.setStyle("-fx-background-color: #34eb71; -fx-text-fill: #FFFFFF; -fx-font-size: 20");
        soundOnOff.setPrefSize(200,50);
        root.getChildren().add(soundOnOff);
    }

    private void setUpSubmitCustomBtn() {
        submit = new Button("Submit Custom Mode!");
        root.getChildren().add(submit);
    }

    private void setUpCustomNumPegs() {
        HBox hBoxNumPegs = new HBox();
        hBoxNumPegs.setSpacing(10);
        pegsSlider = new Slider(4,6,4);
        pegsSlider.setBlockIncrement(1);
        pegsSlider.setMajorTickUnit(1);
        pegsSlider.setShowTickLabels(true);
        pegsSlider.setShowTickMarks(true);
        pegsSlider.setMinorTickCount(0);
        pegsSlider.setSnapToTicks(true);
        hBoxNumPegs.setAlignment(Pos.CENTER);
        numPegs = new Label("Number of Pegs:");
        hBoxNumPegs.getChildren().addAll(numPegs,pegsSlider);
        root.getChildren().add(hBoxNumPegs);
    }

    private void setUpCustomGuesses() {
        HBox hBoxNumGuesses = new HBox();
        hBoxNumGuesses.setSpacing(10);
        guessesSlider = new Slider(2,12,1);
        guessesSlider.setShowTickLabels(true);
        guessesSlider.setShowTickMarks(true);
        guessesSlider.setMajorTickUnit(1);
        guessesSlider.setMinorTickCount(0);
        guessesSlider.setSnapToTicks(true);
        guessesSlider.setBlockIncrement(1);
        guessesSlider.setPrefWidth(250);
        hBoxNumGuesses.setAlignment(Pos.CENTER);
        numGuesses = new Label("Number of Guesses:");
        hBoxNumGuesses.getChildren().addAll(numGuesses,guessesSlider);
        root.getChildren().add(hBoxNumGuesses);
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

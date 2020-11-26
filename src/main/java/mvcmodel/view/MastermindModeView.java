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
 * Time: 5:51 PM
 *
 * Project: csci205FinalProject
 * Package: mvcmodel.view
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * Class for the mode view scene
 */
public class MastermindModeView {

    /** How many pegs you want to play with **/
    private Label numPegs;

    /** slider UI to select number of pegs **/
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

    /** Select number of guesses **/
    private Slider guessesSlider;

    /** How many nuumber of guesses user wants **/
    private Label numGuesses;

    /** Button to submit user defined game options **/
    private Button submit;

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

        // set up user defined mode for guesses
        setUpCustomGuesses();

        // set up user defined mode for pegs
        setUpCustomNumPegs();

        // submit user defined mode
        setUpSubmitCustomBtn();

        // set up sound button
        setUpSoundBtn();
    }

    /**
     * Initiailize the sound button
     */
    private void setUpSoundBtn() {
        // sound button
        soundOnOff = new Button("Click to turn sound on");
        soundOnOff.setStyle("-fx-background-color: #34eb71; -fx-text-fill: #FFFFFF; -fx-font-size: 20");
        soundOnOff.setPrefSize(200,50);
        root.getChildren().add(soundOnOff);
    }

    /**
     * Initialize button for submitting custom mode
     */
    private void setUpSubmitCustomBtn() {
        submit = new Button("Submit Custom Mode");
        submit.setStyle("-fx-background-color: #be90d5; -fx-text-fill: #FFFFFF; -fx-font-size: 20");
        submit.setPrefSize(200,50);
        root.getChildren().add(submit);
    }

    /**
     * Initialize a slider for selecting number of pegs
     */
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
        numPegs = new Label("Number of Pegs ");
        numPegs.setTextFill(Color.web("0F0E0E"));
        hBoxNumPegs.getChildren().addAll(numPegs,pegsSlider);
        root.getChildren().add(hBoxNumPegs);
    }

    /**
     * Initialize a slider for selecting number of guesses
     */
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
        numGuesses = new Label("Number of Guesses ");
        numGuesses.setTextFill(Color.web("0F0E0E"));
        hBoxNumGuesses.getChildren().addAll(numGuesses,guessesSlider);
        root.getChildren().add(hBoxNumGuesses);
    }

    /**
     * Method to set up the label for choosing custom mode
     */
    private void setUpLabel() {
        Label chooseMode = new Label("Choose Mode Or Create Your Own Mode!");
        Font font = Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 55);
        chooseMode.setFont(font);
        chooseMode.setTextFill(Color.web("0F0E0E"));
        root.getChildren().add(chooseMode);
        root.setAlignment(Pos.CENTER);
    }

    /**
     * Method to set up the buttons
     */
    private void setUpButtons() {
        root.setSpacing(10);
        easyBtn = new Button("EasyMind");
        easyBtn.setStyle("-fx-background-color: #03045E; -fx-text-fill: #FFFFFF; -fx-font-size: 20");
        easyBtn.setPrefSize(200,50);
        root.getChildren().add(easyBtn);
        mediumBtn = new Button("MediumMind");
        mediumBtn.setStyle("-fx-background-color: #0077B6; -fx-text-fill: #FFFFFF; -fx-font-size: 20");
        root.getChildren().add(mediumBtn);
        mediumBtn.setPrefSize(200,50);
        masterBtn = new Button("MasterMind");
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

    /**
     * Getter for sound button
     * @return - the sound on off button
     */
    public Button getSoundOnOff() {
        return soundOnOff;
    }

    /**
     * Getter for the number of guesses slider button
     * @return - the number of guesses slider button
     */
    public Slider getGuessesSlider() { return guessesSlider; }

    /**
     * Getter for the submit button
     * @return - the submit button
     */
    public Button getSubmit() { return submit; }

    /**
     * Getter for the number of pegs button
     * @return - the number of pegs buttons
     */
    public Slider getPegsSlider() { return pegsSlider; }
}

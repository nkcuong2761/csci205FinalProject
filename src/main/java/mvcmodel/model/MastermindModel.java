package mvcmodel.model;
/* *****************************************
* CSCI205 - Software Engineering and Design
* Fall 2020
* Instructor: Prof. Brian King
*
* FINAL PROJECT
* Name: Team01
* Member:   Cuong Nguyen
            Lily Parker
            Minh Anh Phan
            Anurag Vadiya
            
* Date: 11/5/2020 - GMT +7
* Time: 9:20 PM - GMT +7
*
* Project: csci205FinalProject
* Package: Object
* Class: mvcmodel.model.MastermindModel
*
* Description: Mastermind Final Project for CSCI 205
*
* ****************************************
*/

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import objects.CodeMaker;
import objects.PegSequence;
import objects.Player;

/**
 * Class to handle the backend logic of the game
 */
public class MastermindModel {

    /**
     * Code maker for the class
     */
    private static CodeMaker codeMaker;

    /**
     * objects.Player in the model
     */
    private static Player player;

    /**
     * Which mode is the user playing
     */
    private String mode;

    /**
     * Number of pegs the user is playing with
     */
    private int numPegs;

    /**
     * Sound on or off
     */
    private boolean sound;

    /**
     * Variable to represent the feedback for the user response
     */
    private int lastRowChecked = -1;

    /**
     * Number representing the current guess;
     */
    private static int currGuess = 0;

    /**
     * Number representing the max number of guesses guess;
     */
    public int MAX_GUESS = 12;

    /**
     * Media object to keep click sound
     */
    private Media buttonMedia;

    /**
     * MeldiaPlayer object for click sound
     */
    private MediaPlayer buttonPlayer;

    /**
     * Variable to represent user guess
     */
    private static PegSequence userGuess;

    /**
     * Constructor for the model to initialize the variable player and the codeMaker
     */
    public MastermindModel(){

        player = new Player();

        // load button sound clip
        buttonMedia = new Media(getClass().getResource("/assets/click.wav").toExternalForm());
        buttonPlayer = new MediaPlayer(buttonMedia);
    }

    /**
     * Method to restart the game and
     */
    public void restartGame(){
        currGuess = 0;
        codeMaker.generateCode();
        userGuess = null;
        lastRowChecked = -1;
    }

    /**
     * Method to start the game
     */
    public void startGame() {
        codeMaker = new CodeMaker();
    }

    /**
     * Method to set the mode of the game
     * @param mode - string mode
     */
    public void setMode(String mode) {
        this.mode = mode;
        switch(mode) {
            case("EasyMind"):
                MAX_GUESS = 12;
                this.numPegs = 4;
                break;
            case("MediumMind"):
                MAX_GUESS = 10;
                this.numPegs = 5;
                break;
            case("MasterMind"):
                MAX_GUESS = 8;
                this.numPegs = 6;
                break;
        }
        PegSequence.setSequenceLength(this.numPegs);

    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }

    /**
     * Set the player name in the model
     * @param pName - the Name of the player
     */
    public void setPlayerName(String pName) {
        player.setPlayerName(pName);
    }

    /**
     * Method to set the custom mode designed by the user
     * @param numGuesses - user define dnumber of guesses
     * @param numPegs - user defined number of pegs
     */
    public void setCustomMode(double numGuesses, double numPegs) {
        MAX_GUESS = (int) numGuesses;
        this.numPegs = (int) numPegs;
        PegSequence.setSequenceLength(this.numPegs);
    }

    /**
     * Set user code to be the one the user entered to receive the feedback
     */
    public PegSequence setUserCode(PegSequence userInputCode){
        currGuess ++;
        lastRowChecked ++;
        return codeMaker.compare(userInputCode);
    }

    /**
     * Getter for player
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Getter for maximum guesses allowed
     * @return - int number of maximum guesses
     */
    public int getMaxGuess() {
        return MAX_GUESS;
    }

    /**
     * Getter for userGuess
     * @return userGuess
     */
    public PegSequence getUserGuess() {
        return userGuess;
    }

    /**
     * Getter for current Guess
     * @return currGuess
     */
    public int getCurrGuess() {
        return currGuess;
    }

    /**
     * Getter for codeMaker
     * @return the codeMaker object
     */
    public static CodeMaker getCodeMaker() {
        return codeMaker;
    }

    /**
     * Getter for button click sound player
     * @return - MediaPlayer of button click sound
     */
    public MediaPlayer getButtonPlayer() {
        return buttonPlayer;
    }

    /**
     * Getter for number of pegs on the board
     * @return - int number of pegs
     */
    public int getNumPegs() {
        return numPegs;
    }

    /**
     * Getter for whether sound is on or off
     * @return - boolean (true if sound is on, false otherwise)
     */
    public boolean getSound() {
        return sound;
    }

    /**
     * Getter for which is the last row checked
     * @return - int last row checked
     */
    public int getLastRowChecked() {
        return lastRowChecked;
    }
}
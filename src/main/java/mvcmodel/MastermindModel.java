package mvcmodel;/* *****************************************
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
* Class: mvcmodel.MastermindModel
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

public class MastermindModel {

    /**
     * Code maker for the class
     */
    private static CodeMaker codeMaker;

    /**
     * objects.Player in the model
     */
    private static Player player;

    private String mode;

    private int numPegs;

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

    private Media buttonMedia;
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

    public void startGame() {
        codeMaker = new CodeMaker();
    }


    public void setMode(String mode) {
        this.mode = mode;
        switch(mode) {
            case("Beginner"):
                MAX_GUESS = 12;
                this.numPegs = 4;
                break;
            case("Intermediate"):
                MAX_GUESS = 10;
                this.numPegs = 5;
                break;
            case("Master"):
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

    public MediaPlayer getButtonPlayer() {
        return buttonPlayer;
    }

    public int getNumPegs() {
        return numPegs;
    }

    public boolean getSound() {
        return sound;
    }

    public int getLastRowChecked() {
        return lastRowChecked;
    }
}
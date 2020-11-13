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

    public void setMode(String mode) {
        switch(mode) {
            case("Beginner"):
                System.out.println("set to 12 guesses");
                MAX_GUESS = 12;
                break;
            case("Intermediate"):
                System.out.println("set to 10 guesses");
                MAX_GUESS = 10;
                break;
            case("Master"):
                System.out.println("set to 8 guesses");
                MAX_GUESS = 8;
                break;
        }
    }

    /**
     * Variable to represent user guess
     */
    private static PegSequence userGuess;

    public int getLastRowChecked() {
        return lastRowChecked;
    }

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
     * Constructor for the model to initialize the variable player and the codeMaker
     */
    public MastermindModel(){
        codeMaker = new CodeMaker();
        player = new Player();
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
     * Set the player name in the model
     * @param pName - the Name of the player
     */
    public void setPlayerName(String pName) {
        player.setPlayerName(pName);
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
     * Set user code to be the one the user entered to receive the feedback
     */
    public PegSequence setUserCode(PegSequence userInputCode){
        currGuess ++;
        lastRowChecked ++;
        return codeMaker.compare(userInputCode);
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
}
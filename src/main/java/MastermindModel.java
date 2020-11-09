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
* Class: MastermindModel
*
* Description: Mastermind Final Project for CSCI 205
*
* ****************************************
*/

public class MastermindModel {

    /**
     * Code maker for the class
     */
    private static CodeMaker codeMaker;

    /**
     * Player in the model
     */
    private static Player player;

    /**
     * Variable to represent user guess
     */
    private static PegSequence userGuess;

    /**
     * Variable to represent the feedback for the user response
     */
    private static PegSequence response;

    /**
     * Number representing the current guess;
     */
    private static int currGuess = 0;

    /**
     * Number representing the max number of guesses guess;
     */
    protected static final int MAX_GUESS = 12;

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

    public static int getMaxGuess() {
        return MAX_GUESS;
    }

    public void setPlayerName(String pName) {
        player.setPlayerName(pName);
    }

    /**
     * Method to start the game and reset the code
     */
    private void gameStart(){

    }

    /**
     * Method to restart the game and
     */
    public void restartGame(){
        currGuess = 0;
        codeMaker.generateCode();
        userGuess = null;
        response = null;
    }

    /**
     * Set user code to be the one the user entered to receive the feedback
     */
    public PegSequence setUserCode(PegSequence userInputCode){
        return codeMaker.compare(userInputCode);
    }

    /**
     * Method to end the game
     */
    private void gameEnd(){

    }

    /**
     * Getter for userGuess
     * @return userGuess
     */
    public static PegSequence getUserGuess() {
        return userGuess;
    }

    /**
     * Getter for current Guess
     * @return currGuess
     */
    public static int getCurrGuess() {
        return currGuess;
    }
}
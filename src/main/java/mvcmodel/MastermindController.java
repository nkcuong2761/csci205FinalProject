package mvcmodel;/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Lily Parker
 * Section: 01
 * Date: 11/9/20
 * Time: 9:10 AM
 *
 * Project: csci205FinalProject
 * Package: PACKAGE_NAME
 * Class: mvcmodel.MastermindController
 *
 * Description: The controller in the MVC logic for the Mastermind game. Handling the logic between the model and the view.
 *
 * *****************************************
 */

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import mvcmodel.view.MastermindView;
import objects.Peg;
import objects.PegSequence;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class MastermindController {
    /**
     * The model of the game
     */
    private MastermindModel theModel;

    /**
     * The main board view of the game
     */
    private MastermindView theView;

    /**
     * The scene generated by the modeView
     */
    private Scene modeScene;

    /**
     * The variable holding list of the rows that have already been checked
     */
    private ArrayList<Integer> rowsChecked = new ArrayList<>();

    /**
     * Variable to indicate if the game has been finished
     */
    private boolean finished;

    private boolean isSoundOnOrOff;

    /** count for the number of time that hint button has been clicked */
    private int countHint;

    /**
     * The constructor for the controller of the game. Handling the logic between the view and the model
     * @param theModel - The main model of the game
     * @param theView - The class for theView
     */
    public MastermindController(MastermindModel theModel, Scene modeScene, MastermindView theView) {
        // Initialize the main variable
        this.theModel = theModel;
        this.theView = theView;
        this.modeScene = modeScene;

        countHint = 0;

        // method to set up the pegs
        handleUserChoice();

        // handler for the quit button
        handleQuitButton();

        // handler for the submit button
        handleCheckAnswerBtn();

        // handler for the reset button
        handleResetButton();

        // handler for the delete button
        handleDelete();

        // handler for rules button
        handleRulesButton();

        // handler for hint button
        handleHintButton();

        // handler for theme button
        handleThemeButton();

        // handler for the back button
        handleBackButton();

    }

    /**
     * Method to handle transition between the previous and new screen
     */
    private void handleBackButton() {
        theView.getBackBtn().setOnAction(event -> {
            click();
            Stage modeStage = (Stage) theView.getBackBtn().getScene().getWindow();
            modeStage.setScene(modeScene);
            // Restart the game
            theModel.restartGame();
            if (theView.getMediaPlayer() != null){
                theView.getMediaPlayer().stop();
            }
        });
    }

    /**
     * The logic for the submit button: Check if the user enters all the output before hitting the submit button.
     * Handle winning/ losing logic
     */
    private void handleCheckAnswerBtn() {
        theView.getCheckBtn().setOnAction(event -> {
            click();
            // TODO SAFE ERROR CHECKING: Nothing happens when clicking on the pegs after game finished
            if (finished) {
                return;
            }
            // Empty the current string there
            theView.updateOutputString("");

            // do not allow user to use check button if entered guess is incomplete
            // NOTE THIS ONE DOES NOT CONSIDER THE LAST ROW SUBMISSION: ROW = 11; COLUMN = -1
            if ((getColumn(getRow()) != 1 || (getColumn(getRow()) == 1 && getRow() == 0)) && !(getColumn(getRow()) == -1 && getRow() == (theModel.getMaxGuess()-1))){
                theView.updateOutputString("Finish entering your guess first!");
            }
            else {
                // if you are in the else part, then you know for sure that the user guess is a sequence of four colors
                // get the row the user is on
                rowsChecked.add(getRow() - 1);

                // if you are hitting check answer after the row has already been checked
                if ((getRow() == theModel.getLastRowChecked() + 1) && (getRow() != (theModel.getMaxGuess()-1))){
                    theView.updateOutputString(String.format("Row %d has already been checked", theModel.getLastRowChecked() + 1));
                    return;
                }

                // get the sequence of colors input as a peg sequence
                PegSequence userGuessPegSequence = new PegSequence();
                // Iterate through each circle
                for (Circle circle: theView.getGuesses().get(theModel.getCurrGuess())){
                    int num = 0;
                    // Get the index based on the class name
                    for (int i = 0; i < theView.getStyleClassString().length; i++) {
                        if (circle.getStyleClass().toString().equals(theView.getStyleClassString()[i])) {
                            num = i + 1;
                            break;
                        }
                    }
                    // Create peg from the position
                    Peg currentPeg = Peg.getPegfromString(String.valueOf(num));
                    // Create the user sequence
                    userGuessPegSequence.addPeg(currentPeg);
                }

                // Receive the feedback from the model
                PegSequence comparisonResult = theModel.setUserCode(userGuessPegSequence);

                // Output winning message
                if (comparisonResult.toString().equals(PegSequence.WINNING_SEQUENCE)) {
                    try {
                        theView.displayEndGame(true);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    finished = true;
                }

                // Update the response peg accordingly
                // if you're on the last row
                if (theModel.getCurrGuess() == (theModel.getMaxGuess())){
                    theView.updateRow(getRow(), comparisonResult);
                } else {
                    // for all rows except the last one
                    theView.updateRow(getRow() - 1, comparisonResult);
                }
                theView.updateTurnLeftString();
                System.out.println("Current guess number is: " + theModel.getCurrGuess());
            }

            // Finished when all the rows are filled
            if (theModel.getCurrGuess() == theModel.getMaxGuess() && !finished) {
                try {
                    theView.displayEndGame(false);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                finished = true;
                return;
            }
        });
    }

    /**
     * Method to gracefully exit the game using the quit button
     */
    private void handleQuitButton() {
        theView.getQuitBtn().setOnAction(event -> {
        	countHint = 0;
            click();
            Platform.exit();
        });
    }


    /**
     * Method to get the row that the user is currently on
     *
     * @return integer of the row that the user is currently on
     */
    private int getRow() {
        int rowNumber = 0;
        for( int i = 0; i < theView.getRows().size(); i++) {
            TilePane currentRow = theView.getRows().get(i);
            ObservableList<Node> guesses = currentRow.getChildren();
            rowNumber = i;
            if(getColumn(i) != -1){
                break;
            }
        }
        return rowNumber;
    }

    /**
     * Method to get the index of the column of the first open circle on the input row
     * !REMEMBER! Each row now has 7 columns (with the line indicators on the 1st column)
     *
     * @param row - integer of the row that is input
     * @return -index of the open circle
     */
    private int getColumn(int row) {
        int colNumber = -1;
        TilePane currentRow = theView.getRows().get(row);
        ObservableList<Node> guesses = currentRow.getChildren();
        for (int j = 1; j <= theModel.getNumPegs(); j++) {
            if (guesses.get(j).getId().equals("blank-circle")) {
                colNumber = j;
                break;
            }
        }
        return colNumber;
    }

    /**
     * Method to tie clicking on the pegs to changing the colors on the rows
     */
    private void handleUserChoice() {
        for (int j = 0; j < theView.getPegsTray().size(); j++) {
            Circle circle = theView.getPegsTray().get(j);
            circle.setOnMouseClicked(event -> {
                click();
                // Empty the current string there
                theView.updateOutputString("");
                // Ensure the button does nothing when the game is finished
                if (finished) {
                    return;
                }
                // Get the corresponding row
                else if (getColumn(getRow()) != -1 && getRow() == 0) {
                    int xValue = getRow();
                    int yValue = getColumn(xValue);
                    theView.updateGuess(xValue, yValue, circle.getStyleClass().toString());
                } else if (getColumn(getRow()) != -1 && rowsChecked.contains(getRow() - 1)) {
                    int xValue = getRow();
                    int yValue = getColumn(xValue);
                    theView.updateGuess(xValue, yValue,  circle.getStyleClass().toString());
                }
                // When the rows is already filled
                else {
                    theView.updateOutputString("Row is entirely filled! Hit Check Answer button!");
                }
            });
        }

    }

    /**
     * Method to handle the logic of the delete button by deleting the previous pegs
     */
    private void handleDelete() {
        theView.getDeleteBtn().setOnAction(event -> {
            click();
            // Ensure the button does nothing when the game is finished
            if (finished) {
                return;
            }
            // Empty output string
            theView.updateOutputString("");
            theView.updateOutputLabel("");
            int rowNumber = getRow();
            int columnNumber = getColumn(rowNumber);
            columnNumber -= 1;
            // Handling edge cases
            if (columnNumber <= 0) {
                columnNumber = 1;
                // Deleting at the beginning of the game
                if (rowNumber == 0 && rowsChecked.size() == 0) {
                    theView.updateOutputString("Haven't entered anything to be deleted");
                }
                // Deleting a submitted answer
                else if (rowsChecked.size() != 0 && rowsChecked.get(rowsChecked.size() - 1) == rowNumber - 1 && theModel.getCurrGuess()!=theModel.getMaxGuess()-1) {
                    theView.updateOutputString("Cannot delete an already submitted answer :P");
                }
                // Deleting the last peg of a filled row that has not yet been checked
                else if (rowsChecked.size() == theModel.getCurrGuess() && theModel.getCurrGuess() != theModel.getMaxGuess()-1) {
                    theView.updateGuess(rowNumber - 1, theModel.getNumPegs(), null);
                    return;
                }
                // Deleting a filled row if it is the last row
                else if (theModel.getCurrGuess() == theModel.getMaxGuess()-1) {
                    theView.updateGuess(rowNumber, theModel.getNumPegs(), null);
                    return;
                }
            }
            theView.updateGuess(rowNumber, columnNumber, null);

        });
    }


    /**
     * Method to handle the reset button
     */
    private void handleResetButton() {
        theView.getResetBtn().setOnMouseClicked(event -> {
            click();
            // Empty the current string there
            theView.updateOutputString("");
            theView.updateOutputLabel("");
            // reset rowsChecked
            rowsChecked = new ArrayList<>();
            // reset countHint
	        countHint = 0;
            // reset finished
            finished = false;
            // Restart the game
            theModel.restartGame();
            theView.createNewScene();
        });
    }

    /**
     * Method to handle the rules button
     */
    private void handleRulesButton() {
        theView.getRulesBtn().setOnMouseClicked(event -> {
            click();
            // Check for finished case and do not output anything
            if (finished){
                return;
            }
            // show the rules in the output string
            theView.updateOutputString("" +
                    "The idea of the game is for one player (the\n" +
                    "code-breaker) to guess the secret code chosen\n" +
                    "by the other player (the code-maker). The code\n" +
                    "is a sequence of 4 colored pegs chosen from six\n" +
                    "colors available. The code-breaker makes a serie\n" +
                    "of pattern guesses - after each guess the\n" +
                    "code-maker gives feedback in the form of 2 numbers,\n" +
                    "the number of pegs that are of the right color and\n" +
                    "in the correct position, and the number of pegs that\n" +
                    "are of the correct color but not in the correct\n" +
                    "position - these numbers are represented by small\n" +
                    "black and grey pegs.");
        });
    }

    /**
     * Method to handle the hint button
     */
    private void handleHintButton() {
        // TODO update this for the changing amount of options for colors of pegs

        theView.getHintBtn().setOnMouseClicked(event -> {
            click();
            countHint += 1;
            // tell the user about one peg that is in the secret code
            if (finished){
                return;
            }
            Random rand = new Random();
            int i = rand.nextInt(PegSequence.getSequenceLength());
            String colorPos = theModel.getCodeMaker().getSecretCode().getSequence().get(i).getValueofPeg();
            String output = null;
            switch (colorPos) {
                case "1":
	                colorPos = "first";
                    break;
                case "2":
	                colorPos = "second";
                    break;
                case "3":
	                colorPos = "third";
                    break;
                case "4":
	                colorPos = "fourth";
                    break;
	            case "5":
		            colorPos = "fifth";
		            break;
	            case "6":
	            	colorPos = "sixth";
	            	break;
                default:
	                colorPos = "bruh";
            }
            switch (countHint) {
	            case 1:
	                output = "See that " + colorPos + " peg in the panel up there?\n" +
			                "There's at least one of that color in the secret code";
	                break;
	            case 2:
	            	output = "Still stuck? Why don't you give it\n" +
				            "another round of thought";
	            	break;
	            case 3: case 4: case 5: case 6:
	            	output = "C'mon it can't be that hard!";
	            	break;
	            case 7:
	            	output = "Okay okay geez! That color I talked about earlier,\n" +
				            "the exact position of the peg with that color in\n" +
				            "the code is " + (i+1);
	            	break;
	            case 8:
	            	output = "Omg what else do you want? A spiritual cheer?";
	            	break;
	            case 9:
	            	output = "Are you really that dumb?";
	            	break;
	            default:
	            	output = "YOU ARE ON YOUR OWN NOW MY GUY/SISTER";
	            	break;
            }
            theView.updateOutputString(output);
        });
    }

    /**
     * Method to handle the theme Button
     */
    private void handleThemeButton() {
        // Set the handler for the button to open/close the tray
        theView.getThemeBtn().setOnMouseClicked(event -> {
            click();
             Boolean trayVisibility = theView.getThemeTray().isVisible();
             theView.getThemeTray().setVisible(!trayVisibility);
        });
        // Set the handler for the theme circles being clicked
        for (Circle c : theView.getThemeOption()) {
            c.setOnMouseClicked(event -> {
                int i = 1;
                if (c.getFill().equals(Color.web("00B4D8")))
                    theView.getRoot().getStylesheets().setAll(getClass().getResource("/style3.css").toExternalForm());
                else if (c.getFill().equals(Color.web("D58097")))
                    theView.getRoot().getStylesheets().setAll(getClass().getResource("/style2.css").toExternalForm());
                else
                    theView.getRoot().getStylesheets().setAll(getClass().getResource("/style.css").toExternalForm());
            });
        }
    }
    private void click() {
        theModel.getButtonPlayer().stop();
        if (theModel.getSound()) {
            System.out.println("Going to play sound");
            theModel.getButtonPlayer().play();
        }
    }

}

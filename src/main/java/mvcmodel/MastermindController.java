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
import mvcmodel.view.MastermindModeView;
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
     * The mode view of the game
     */
    private MastermindModeView modeView;

    /**
     * The variable holding list of the rows that have already been checked
     */
    private ArrayList<Integer> rowsChecked = new ArrayList<>();

    /**
     * Variable to indicate if the game has been finished
     */
    private boolean finished;

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
            // TODO SAFE ERROR CHECKING: Nothing happens when clicking on the pegs after game finished
            if (finished) {
            }
            // Empty the current string there
            theView.updateOutputString("");


            System.out.println(getColumn(getRow()) + " " + getRow());
            // do not allow user to use check button if entered guess is incomplete
            // NOTE THIS ONE DOES NOT CONSIDER THE LAST ROW SUBMISSION: ROW = 11; COLUMN = -1
            if ((getColumn(getRow()) != 1 || (getColumn(getRow()) == 1 && getRow() == 0)) && !(getColumn(getRow()) == -1 && getRow() == (theModel.getMaxGuess()-1))){
                theView.updateOutputString("Finish entering your guess first!");
            }
            else {
                // if you are in the else part, then you know for sure that the user
                // guess is a sequence of four colors

                // get the row the user is on
                rowsChecked.add(getRow() - 1);
                if ((getRow() == theModel.getLastRowChecked() + 1) && (getRow() != (theModel.getMaxGuess()-1))){
                    theView.updateOutputString(String.format("Row %d has already been checked", theModel.getLastRowChecked() + 1));
                    return;
                }
                // get the sequence of colors input as a peg sequence
                PegSequence userGuessPegSequence = new PegSequence();
                for (int i = 0; i < theView.getGuesses().get(getRow() - 1).size(); i++) {
                    Peg currentPeg = Peg.getPegForColor(Color.web(theView.getGuesses().get(getRow() - 1).get(i)
                            .getFill().toString().substring(2, 8).toUpperCase()));

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
                if (getRow() == (theModel.getMaxGuess()-1) && theModel.getLastRowChecked() == (theModel.getMaxGuess())){
                    theView.updateRow(getRow(), comparisonResult);
                } else {
                    theView.updateRow(getRow() - 1, comparisonResult);
                }
                theView.updateTurnLeftString();
                System.out.println("Current guess number is: " + theModel.getCurrGuess());
            }

            // Finished when all the rows are filled
            if (theModel.getCurrGuess() == theModel.getMaxGuess()) {
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
                // Empty the current string there
                theView.updateOutputString("");
                // Ensure the button does nothing when the game is finished
                if (finished) {
                }
                // Get the corresponding row
                else if (getColumn(getRow()) != -1 && getRow() == 0) {
                    int xValue = getRow();
                    int yValue = getColumn(xValue);
                    theView.updateGuess(xValue, yValue, (Color) circle.getFill(), circle.getStyleClass().toString());
                } else if (getColumn(getRow()) != -1 && rowsChecked.contains(getRow() - 1)) {
                    int xValue = getRow();
                    int yValue = getColumn(xValue);
                    theView.updateGuess(xValue, yValue, (Color) circle.getFill(),  circle.getStyleClass().toString());
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
            // Ensure the button does nothing when the game is finished
            if (finished) {
            }
            // Empty output string
            theView.updateOutputString("");
            theView.updateOutputLabel("");
            int rowNumber = getRow();
            int columnNumber = getColumn(rowNumber);
            columnNumber -= 1;
            System.out.println("rowsChecked: " + rowsChecked);
            System.out.println("rowNumber: " + rowNumber + ", columnNumber: " + columnNumber);
            // Handling edge cases
            if (columnNumber <= 0) {
                columnNumber = 1;
                // Deleting at the beginning of the game
                if (rowNumber == 0 && rowsChecked.size() == 0) {
                    theView.updateOutputString("Haven't entered anything to be deleted");
                    System.out.println("Haven't entered anything to be deleted");
                }
                // Deleting a submitted answer
                else if (rowsChecked.size() != 0 && rowsChecked.get(rowsChecked.size() - 1) == rowNumber - 1 && theModel.getCurrGuess()!=theModel.getMaxGuess()-1) {
                    theView.updateOutputString("Cannot delete an already submitted answer :P");
                    System.out.println("Cannot delete an already submitted answer :P");
                }
                // Deleting the last peg of a filled row that has not yet been checked
                else if (rowsChecked.size() == theModel.getCurrGuess() && theModel.getCurrGuess() != theModel.getMaxGuess()-1) {
                    System.out.println("Deleting the last peg of the row");
                    theView.updateGuess(rowNumber - 1, 4, Color.WHITE, null);
                    return;
                }
                // Deleting a filled row if it is the last row
                else if (theModel.getCurrGuess() == theModel.getMaxGuess()-1) {
                    System.out.println("Deleting the last peg of the last row");
                    theView.updateGuess(rowNumber, 4, Color.WHITE, null);
                    return;
                }
            }
            theView.updateGuess(rowNumber, columnNumber, Color.WHITE, null);

        });
    }


    /**
     * Method to handle the reset button
     */
    private void handleResetButton() {
        theView.getResetBtn().setOnMouseClicked(event -> {
            // Empty the current string there
            theView.updateOutputString("");
            theView.updateOutputLabel("");
            // reset rowsChecked
            rowsChecked = new ArrayList<>();
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
            // tell the user about one peg that is in the secret code
            Random rand = new Random();
            int i = rand.nextInt(PegSequence.getSequenceLength());
            String colorValue = theModel.getCodeMaker().getSecretCode().getSequence().get(i).getValueofPeg();
            String colorName;
            switch (colorValue) {
                case "1":
                    colorName = "Red";
                    break;
                case "2":
                    colorName = "Yellow";
                    break;
                case "3":
                    colorName = "Green";
                    break;
                case "4":
                    colorName = "Blue";
                    break;
                default:
                    colorName = "bruh";
            }
            theView.updateOutputString("There is at least 1 " + colorName + " peg in the secret code");
        });
    }

    /**
     * Method to handle the theme Button
     */
    private void handleThemeButton() {
        // Set the handler for the button to open/close the tray
        theView.getThemeBtn().setOnMouseClicked(event -> {
             Boolean trayVisibility = theView.getThemeTray().isVisible();
             theView.getThemeTray().setVisible(!trayVisibility);
        });
        // Set the handler for the theme circles being clicked
        for (Circle c : theView.getThemeOption()) {
            c.setOnMouseClicked(event -> {
                int i = 1;
                if (c.getFill().equals(Color.web("D58097"))){
                    Peg.getPegfromString(String.valueOf(1)).updatePegColor(Color.web("294352"));
                    Peg.getPegfromString(String.valueOf(2)).updatePegColor(Color.web("6F5771"));
                    Peg.getPegfromString(String.valueOf(3)).updatePegColor(Color.web("D58097"));
                    Peg.getPegfromString(String.valueOf(4)).updatePegColor(Color.web("E6B5B8"));
                    Peg.getPegfromString(String.valueOf(5)).updatePegColor(Color.web("123456"));
                    Peg.getPegfromString(String.valueOf(6)).updatePegColor(Color.web("654321"));
                    for (Circle circle: theView.getPegsTray()) {
                        circle.setFill(Peg.getPegfromString(String.valueOf(i)).getColor());
                        i++;
                    }

                    /* Loop when later on refactor with an array list
                    for (int i = 0; i < theView.getPegsTray().size(); i++) {
                        Peg.getPegfromString(String.valueOf(i + 1)).updatePegColor());
                    }*/
                    theView.getRoot().getStylesheets().setAll(getClass().getResource("/style2.css").toExternalForm());}
                else {
                    Peg.getPegfromString(String.valueOf(1)).updatePegColor(Color.web("EF476F"));
                    Peg.getPegfromString(String.valueOf(2)).updatePegColor(Color.web("FFD166"));
                    Peg.getPegfromString(String.valueOf(3)).updatePegColor(Color.web("06D6A0"));
                    Peg.getPegfromString(String.valueOf(4)).updatePegColor(Color.web("023E8A"));
                    Peg.getPegfromString(String.valueOf(5)).updatePegColor(Color.BROWN);
                    Peg.getPegfromString(String.valueOf(6)).updatePegColor(Color.PURPLE);
                    for (Circle circle: theView.getPegsTray()) {
                        circle.setFill(Peg.getPegfromString(String.valueOf(i)).getColor());
                        i++;
                    }
                    theView.getRoot().getStylesheets().setAll(getClass().getResource("/style.css").toExternalForm());}
            });
        }
    }

}

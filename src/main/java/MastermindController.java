/* *****************************************
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
 * Class: MastermindController
 *
 * Description:
 *
 * *****************************************
 */

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MastermindController {
//TODO Need to test more on the restart logic.
//TODO Need to change the get row method.
//TODO Need to delete the rows checked.
//TODO Need to add comments to all the work.
    private MastermindModel theModel;
    private MastermindView theView;
    private MastermindIntroView introView;
    private MastermindModeView modeView;
    private Scene modeScene;
    private ArrayList<Integer> rowsChecked = new ArrayList<>();
    private Scene gameScene;
    private boolean finished;

    public MastermindController(MastermindModel theModel, MastermindModeView modeView, MastermindIntroView introView, MastermindView theView) {
        this.theModel = theModel;
        this.introView = introView;
        this.modeView = modeView;
        this.theView = theView;

        // set up the three different scenes
        setUpScenes();

        // set up handling the get name input
        handleGetName();

        // set up the event handlers for the mode buttons
        handleModeButtons();

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
            if ((getColumn(getRow()) != 0 || (getColumn(getRow()) == 0 && getRow() == 0)) && !(getColumn(getRow()) == -1 && getRow() == 11)){
                theView.updateOutputString("Finish entering your guess first!");
            }
            else {
                // if you are in the else part, then you know for sure that the user
                // guess is a sequence of four colors

                // get the row the user is on
                rowsChecked.add(getRow() - 1);
                if ((getRow() == theModel.getLastRowChecked() + 1) && (getRow() != 11)){
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
                    theView.displayEndGame(true);
                    finished = true;
                }

                // Update the response peg accordingly
                if (getRow() == 11 && theModel.getLastRowChecked() == 11){
                    theView.updateResponse(getRow(), comparisonResult);
                } else {
                    theView.updateResponse(getRow() - 1, comparisonResult);
                }
                theView.updateTurnLeftString();
                System.out.println("Current guess number is: " + theModel.getCurrGuess());
                System.out.println(comparisonResult.toString());
            }

            // Finished when all the rows are filled
            if (theModel.getCurrGuess() == 12) {
                theView.displayEndGame(false);
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
     *
     * @param row - integer of the row that is input
     * @return -index of the open circle
     */
    private int getColumn(int row) {
        int colNumber = -1;
        TilePane currentRow = theView.getRows().get(row);
        ObservableList<Node> guesses = currentRow.getChildren();
        for (int j = 0; j < 4; j++) {
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
                // TODO SAFE ERROR CHECKING: Nothing happens when clicking on the pegs after game finished
                if (finished) {
                }
                // Get the corresponding row
                else if (getColumn(getRow()) != -1 && getRow() == 0) {
                    int xValue = getRow();
                    int yValue = getColumn(xValue);
                    theView.updateGuess(xValue, yValue, (Color) circle.getFill());
                } else if (getColumn(getRow()) != -1 && rowsChecked.contains(getRow() - 1)) {
                    int xValue = getRow();
                    int yValue = getColumn(xValue);
                    theView.updateGuess(xValue, yValue, (Color) circle.getFill());
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
            // TODO SAFE ERROR CHECKING: Nothing happens when clicking on the pegs after game finished
            if (finished) {
            }
            // Empty output string
            theView.updateOutputString("");
            int rowNumber = getRow();
            int columnNumber = getColumn(rowNumber);
            columnNumber -= 1;
            // Handling edge cases
            if (columnNumber == -1) {
                columnNumber = 0;
                // Deleting at the beginning of the game
                if (rowNumber == 0 && rowsChecked.size() == 0) {
                    theView.updateOutputString("Haven't entered anything to be deleted");
                    System.out.println("Haven't entered anything to be deleted");
                }
                // Deleting a submitted answer
                else if (rowsChecked.size() != 0 && rowsChecked.get(rowsChecked.size() - 1) == rowNumber - 1) {
                    theView.updateOutputString("Cannot delete an already submitted answer :P");
                    System.out.println("Cannot delete an already submitted answer :P");
                }
                // Deleting a filled row that has not yet been checked
                else if (rowsChecked.size() == theModel.getCurrGuess()) {
                    theView.updateGuess(rowNumber - 1, 3, Color.WHITE);
                }

            }
            theView.updateGuess(rowNumber, columnNumber, Color.WHITE);

        });
    }

    /**
     * Method to set up the scences for transition to different modes and the main games
     */
    private void setUpScenes() {
        // Scene to choose single/ multiplayer
        modeScene = new Scene(modeView.getRoot());
        modeScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        // Scene to enter the game
        gameScene = new Scene(theView.getRoot());
        gameScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    }

    /**
     * Method to update the player name to display on the game
     */
    private void handleGetName() {
        introView.getPlayBtn().setOnAction((ActionEvent event) -> {
            try {
                String strName = introView.getNameInput().getText();
                if (strName.length() > 0) {
                    theModel.setPlayerName(strName);
                    theView.updateName(strName);
                    Stage introStage = (Stage) introView.getPlayBtn().getScene().getWindow();
                    introStage.setScene(modeScene);
                    introStage.sizeToScene();
                }
            }
            // Error checking for valid name
            catch (NumberFormatException numberFormatException) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect input!");
                alert.setHeaderText("Incorrect input specified!");
                alert.setContentText(String.format("Invalid name: \"%s\"",
                        introView.getNameInput().getText()));

                alert.show();
            }
        });

        introView.getNameInput().setOnAction(introView.getPlayBtn().getOnAction());
    }
// TODO HANDLE RESET BUTTON: CALL RESET IN THE MODEL, RENDER A NEW SCREEN, GAME SCENE

    /**
     * Method to handle the mode transition to single player/ multiplayer option
     */
    private void handleModeButtons() {
        modeView.getSinglePlayerBtn().setOnAction((ActionEvent event) -> {
            String mode = "Single";
            Stage modeStage = (Stage) modeView.getSinglePlayerBtn().getScene().getWindow();
            modeStage.setScene(gameScene);
            modeStage.sizeToScene();
        });

        modeView.getMultiplayerBtn().setOnAction(modeView.getSinglePlayerBtn().getOnAction());
    }

    /**
     * Method to handle the reset button
     */
    private void handleResetButton() {
        theView.getResetBtn().setOnMouseClicked(event -> {
            // Empty the current string there
            theView.updateOutputString("");
            // Restart the game
            theModel.restartGame();
            theView.createNewScene();
        });
    }

}

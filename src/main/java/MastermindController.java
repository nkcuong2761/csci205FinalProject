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

    private MastermindModel theModel;
    private MastermindView theView;
    private MastermindIntroView introView;
    private MastermindModeView modeView;
    private Scene modeScene;
    private Scene gameScene;
    private ArrayList<Integer> rowsChecked = new ArrayList<>();


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
        handleQuitButton(theView);

        // handler for the submit button
        theView.getCheckBtn().setOnAction(event -> {


            // do not allow user to use check button if entered guess is incomplete
            if (getColumn(getRow()) != 0 || (getColumn(getRow()) == 0 && getRow() == 0)) {

                System.out.println("Finish entering your guess please");
            }
            else {
                // if you are in the else part, then you know for sure that the user
                // guess is a sequence of four colors
                int currentGuess = getRow();
                theModel.setCurrGuess(currentGuess);
                System.out.println("Current guess number is: " + theModel.getCurrGuess());

                // get the row the user is on
                System.out.println("The user is on row: " + getRow());
                rowsChecked.add(getRow() - 1);
                System.out.println("Rows checked: " + rowsChecked.toString());

                // get the sequence of colors now
                PegSequence userGuessPegSequence = new PegSequence();
                for (int i = 0; i < theView.getGuesses().get(getRow() - 1).size(); i++) {
                    Peg currentPeg = Peg.getPegForColor(Color.web(theView.getGuesses().get(getRow() - 1).get(i)
                                    .getFill().toString().substring(2,8).toUpperCase()));

                    userGuessPegSequence.addPeg(currentPeg);
                }

               PegSequence comparisonResult = CodeMaker.compare(userGuessPegSequence);
               System.out.println(comparisonResult.toString());
            }



            // if the row is 11, then quit (TODO: should display result)
            if (theModel.getCurrGuess() == 11){
                Platform.exit();
            }


        });

        handleDelete();


    }

    private void handleQuitButton(MastermindView theView) {
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
     * @return - integer of -1 if the row is filled, and index of the open circle if not
     */
    private int getColumn(int row) {
        int colNumber = -1;
        TilePane currentRow = theView.getRows().get(row);
        ObservableList<Node> guesses = currentRow.getChildren();
        for (int j = 0; j < 4; j++) {
            if( guesses.get(j).getId().equals("blank-circle")) {
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
                if ( getColumn(getRow()) != -1 && getRow() == 0){
                    int xValue = getRow();
                    int yValue = getColumn(xValue);
                    theView.updateGuess(xValue, yValue, (Color) circle.getFill());
                }
                else if (getColumn(getRow()) != -1 && rowsChecked.contains(getRow() - 1)){
                    int xValue = getRow();
                    int yValue = getColumn(xValue);
                    theView.updateGuess(xValue, yValue, (Color) circle.getFill());
                }
                else {
                    System.out.println("Row is not entirely filled yet");
                }
            });
        }

    }

    private void handleDelete() {
        theView.getDeleteBtn().setOnAction(event -> {
           int rowNumber = getRow();
           int columnNumber = getColumn(rowNumber) ;
           columnNumber -= 1;
           if(columnNumber == -1 ) {
               columnNumber = 0;
               System.out.println("Cannot delete an already submitted answer :P");
           }
           theView.updateGuess(rowNumber,columnNumber, Color.WHITE);

        });
    }

    private void setUpScenes() {
        modeScene = new Scene(modeView.getRoot());
        modeScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        gameScene = new Scene(theView.getRoot());
        gameScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    }

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

    private void handleModeButtons() {
        modeView.getSinglePlayerBtn().setOnAction((ActionEvent event) -> {
            String mode = "Single";
            Stage modeStage = (Stage) modeView.getSinglePlayerBtn().getScene().getWindow();
            modeStage.setScene(gameScene);
            modeStage.sizeToScene();
        });

        modeView.getMultiplayerBtn().setOnAction(modeView.getSinglePlayerBtn().getOnAction());
    }


}

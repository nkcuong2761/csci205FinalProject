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

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MastermindController {

    private MastermindModel theModel;
    private MastermindView theView;
    private MastermindIntroView introView;
    private MastermindModeView modeView;
    private Scene modeScene;
    private Scene gameScene;


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

        handleDelete();


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
                if(getColumn(11) != -1) {
                    int xValue = getRow();
                    int yValue = getColumn(xValue);
                    theView.updateGuess(xValue, yValue, (Color) circle.getFill());
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
               columnNumber = 3;
               rowNumber = rowNumber -1;
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

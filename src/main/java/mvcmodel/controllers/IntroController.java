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
 * Date: 11/12/20
 * Time: 9:14 PM
 *
 * Project: csci205FinalProject
 * Package: mvcmodel
 * Class: Controller
 *
 * Description:
 *
 * *****************************************
 */

package mvcmodel.controllers;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import mvcmodel.model.MastermindModel;
import mvcmodel.view.MastermindIntroView;
import mvcmodel.view.MastermindModeView;
import mvcmodel.view.MastermindView;
import java.util.concurrent.atomic.AtomicInteger;

public class IntroController {

    /**
     * The model of the game
     */
    private MastermindModel theModel;

    /**
     * The scene generated by theView
     */
    private Scene gameScene;

    /**
     * The scene generated by the modeView
     */
    private Scene modeScene;

    /**
     * The intro view of the game
     */
    private MastermindIntroView introView;

    /**
     * The mode view of the game
     */
    private MastermindModeView modeView;



    public IntroController(MastermindModeView modeView, MastermindIntroView introView, MastermindModel theModel) throws InterruptedException {
        this.modeView = modeView;
        this.introView = introView;
        this.theModel = theModel;

        setUpScenes();
        handleGetName();
        handleModeButtons();
        handleSubmitBtn();
        // handle sound
        handleSoundBtn(modeView);

    }

    private void handleSoundBtn(MastermindModeView modeView) {
        AtomicInteger count = new AtomicInteger();
        modeView.getSoundOnOff().setOnAction(event -> {
            if (count.get() %2 == 0){
                modeView.getSoundOnOff().setText("Click to turn sound off");
                theModel.setSound(true);
                click();
            }
            else {
                modeView.getSoundOnOff().setText("Click to turn sound on");
                theModel.setSound(false);

            }
            count.getAndIncrement();
        });
    }

    private void handleSubmitBtn() {
        modeView.getSubmit().setOnAction((ActionEvent event) -> {
            click();
            theModel.setCustomMode(modeView.getGuessesSlider().getValue(), modeView.getPegsSlider().getValue());
            Stage modeStage = (Stage) modeView.getEasyBtn().getScene().getWindow();
            MastermindView theView = new MastermindView(theModel);
            gameScene = new Scene(theView.getRoot());
            gameScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            modeStage.setScene(gameScene);
            modeStage.sizeToScene();
            theModel.startGame();
            MastermindController theController = new MastermindController(theModel, modeScene, theView);
        });
    }


    /**
     * Method to set up the scences for transition to different modes and the main games
     */
    private void setUpScenes() {
        // Scene to choose single/ multiplayer
        modeScene = new Scene(modeView.getRoot());
        modeScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

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

    /**
     * Method to handle the mode transition to single player/ multiplayer option
     */
    private void handleModeButtons() throws InterruptedException {
        modeView.getEasyBtn().setOnAction((ActionEvent event) -> {
            click();
            theModel.setMode(((Button)event.getSource()).getText());
            System.out.println("- Mode has been set -");
            Stage modeStage = (Stage) modeView.getEasyBtn().getScene().getWindow();
            MastermindView theView = new MastermindView(theModel);
            gameScene = new Scene(theView.getRoot());
            gameScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            modeStage.setScene(gameScene);
            modeStage.sizeToScene();
            theModel.startGame();
            MastermindController theController = new MastermindController(theModel, modeScene, theView);

        });

        modeView.getMediumBtn().setOnAction((modeView.getEasyBtn().getOnAction()));
        modeView.getMasterBtn().setOnAction((modeView.getEasyBtn().getOnAction()));

    }


    private void click() {
        theModel.getButtonPlayer().stop();
        if (theModel.getSound()){
            System.out.println("Going to play sound");
            theModel.getButtonPlayer().play();
        }
    }
}

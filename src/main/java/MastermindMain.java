/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Anurag Vaidya
 * Section: 8:50 AM
 * Date: 11/1/2020
 * Time: 9:37 AM
 *
 * Project: csci205FinalProject
 * Package: PACKAGE_NAME
 * Class: Main
 *
 * Description:
 *
 * ****************************************
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MastermindMain extends Application {

    private MastermindModel theModel;
    private MastermindView theView;
    private MastermindIntroView introView;
    private MastermindModeView modeView;

    @Override
    public void init() throws Exception {
        super.init();
        this.theModel = new MastermindModel();
        this.introView = new MastermindIntroView();
        this.modeView = new MastermindModeView();
        this.theView = new MastermindView(theModel);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Mastermind");
        Scene scene = new Scene(introView.getRoot());
        // I dont know how to implement css:(
//        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
//        scene.getStylesheets().add("/style.css");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();

        // this method will change to the mode choice scene when the user enter their name
        handleChangeToModeScene(primaryStage);

        // this method will change to the main game scene when the user chooses the mode of the game
        handleChangeToMainGameScene(primaryStage);

        primaryStage.show();
    }

    private void handleChangeToMainGameScene(Stage primaryStage) {
        modeView.getSinglePlayerBtn().setOnAction((ActionEvent event) -> {
            String mode = "Single";
            // @CUONG: This is where the scene is switching to your scene!! (After one of the single player
            // or multiplayer buttons are clicked) - Lily
            primaryStage.setScene(new Scene (theView.getRoot()));
            primaryStage.sizeToScene();
        });

        modeView.getMultiplayerBtn().setOnAction(modeView.getSinglePlayerBtn().getOnAction());
    }

    private void handleChangeToModeScene(Stage primaryStage) {
        introView.getPlayBtn().setOnAction((ActionEvent event) -> {
            try {
                String strName = introView.getNameInput().getText();
                if (strName.length() > 0) {
                    String playerName = strName;
                    primaryStage.setScene(new Scene(modeView.getRoot()));
                    primaryStage.sizeToScene();
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

    public static void main(String[] args) {
        launch(args);
    }
}

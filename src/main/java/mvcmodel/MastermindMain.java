package mvcmodel;/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Cuong Nguyen
            Lily Parker
            Minh Anh Phan
            Anurag Vadiya
 * Section: 8:50 AM
 * Date: 11/1/2020
 * Time: 9:37 AM
 *
 * Project: csci205FinalProject
 * Package: PACKAGE_NAME
 * Class: Main
 *
 * Description: The JavaFX Class to run the JavaFX application.
 *
 * ****************************************
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mvcmodel.controllers.IntroController;
import mvcmodel.model.MastermindModel;
import mvcmodel.view.MastermindIntroView;
import mvcmodel.view.MastermindModeView;
import mvcmodel.view.MastermindView;

/**
 * The JavaFX Class to run the JavaFX application.
 */
public class MastermindMain extends Application {
    /**
     * The model of the game
     */
    private MastermindModel theModel;

    /**
     * The main board view of the game
     */
    private MastermindView theView;

    /**
     * The intro view of the game
     */
    private MastermindIntroView introView;

    /**
     * The mode view of the game
     */
    private MastermindModeView modeView;

    /**
     * The controller for the game
     */
    private IntroController theIntroController;


    /**
     * The first scene of the game
     */
    private Scene introScene;

    /**
     * initialize the model, and two views
     * @throws Exception
     */
    @Override
    public void init() throws Exception {
        super.init();
        this.theModel = new MastermindModel();
        this.introView = new MastermindIntroView();
        this.modeView = new MastermindModeView();
    }

    /**
     * initialize the controllers and the scenes
     * @param primaryStage - stage to set the GUI on
     * @throws InterruptedException
     */
    @Override
    public void start(Stage primaryStage) throws InterruptedException {
        theIntroController = new IntroController(modeView,introView,theModel);
        primaryStage.setTitle("Mastermind");
        introScene = new Scene(introView.getRoot());
        introScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setScene(introScene);
        primaryStage.sizeToScene();
        primaryStage.show();

    }

    /**
     * Main method for the program
     * @param args - command line args
     */
    public static void main(String[] args) {
        launch(args);
    }
}

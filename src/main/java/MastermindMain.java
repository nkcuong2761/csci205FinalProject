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
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MastermindMain extends Application {

    private MastermindModel theModel;
    private MastermindView theView;
    private MastermindIntroView introView;
    private MastermindModeView modeView;
    private MastermindController theController;
    private Scene introScene;


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
        introScene = new Scene(introView.getRoot());
        introScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(introScene);
        primaryStage.sizeToScene();
        primaryStage.show();
        this.theController = new MastermindController(theModel,modeView,introView,theView);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

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

    @Override
    public void init() throws Exception {
        super.init();
        this.theModel = new MastermindModel();
        this.theView = new MastermindView(theModel);
    }

    @Override
    public void start(Stage primaryStage) {
        // Add the scene to the stage
        primaryStage.setScene(new Scene(theView.getRoot()));
        primaryStage.sizeToScene();
        // Set the title for the main window
        primaryStage.setTitle("Mastermind");
        // Display the scene
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

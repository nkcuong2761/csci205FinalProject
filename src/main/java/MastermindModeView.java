/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Lily Parker
 * Section: 01
 * Date: 11/5/20
 * Time: 5:51 PM
 *
 * Project: csci205FinalProject
 * Package: PACKAGE_NAME
 * Class: MastermindModeView
 *
 * Description:
 *
 * *****************************************
 */

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MastermindModeView {
    public VBox getRoot() {
        return root;
    }

    private VBox root;

    public Button getSinglePlayerBtn() {
        return singlePlayerBtn;
    }

    public Button getMultiplayerBtn() {
        return multiplayerBtn;
    }

    private Button singlePlayerBtn;
    private Button multiplayerBtn;

    public MastermindModeView() {
        root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setPrefSize(500,500);
        root.setAlignment(Pos.CENTER);

        Label chooseMode = new Label("Choose Mode:");
        chooseMode.setFont(new Font("Arial", 20));
        root.getChildren().add(chooseMode);
        root.setAlignment(Pos.CENTER);


        singlePlayerBtn = new Button("Single Player");
        singlePlayerBtn.setStyle("-fx-background-color: #03045E; -fx-text-fill: #FFFFFF; -fx-font-size: 20");
        singlePlayerBtn.setPrefSize(200,50);
        root.getChildren().add(singlePlayerBtn);
        multiplayerBtn = new Button("Multiplayer");
        multiplayerBtn.setStyle("-fx-background-color: #0077B6; -fx-text-fill: #FFFFFF; -fx-font-size: 20");
        root.getChildren().add(multiplayerBtn);
        multiplayerBtn.setPrefSize(200,50);
    }
}

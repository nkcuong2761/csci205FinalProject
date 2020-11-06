/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Lily Parker
 * Section: 01
 * Date: 11/5/20
 * Time: 5:08 PM
 *
 * Project: csci205FinalProject
 * Package: PACKAGE_NAME
 * Class: MastermindIntroView
 *
 * Description:
 *
 * *****************************************
 */

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MastermindIntroView {
    private MastermindModel theModel;
    private VBox root;

    public TextField getNameInput() {
        return nameInput;
    }

    private TextField nameInput;

    public Button getPlayBtn() {
        return playBtn;
    }

    private Button playBtn;

    public MastermindIntroView() {
        root = new VBox(10);
        root.setPrefSize(500,500);
        root.setPadding(new Insets(5));
        root.setMinHeight(300);
        root.setAlignment(Pos.CENTER);
        BackgroundFill backgroundFill = new BackgroundFill(Color.rgb(202,240,248), CornerRadii.EMPTY,Insets.EMPTY);
        Background background = new Background(backgroundFill);
        root.setBackground(background);

        Label gameTitle = new Label("MASTERMIND");
        gameTitle.setFont(new Font("Arial", 50));
        root.getChildren().add(gameTitle);
        root.setAlignment(Pos.CENTER);
        Label nameLabel = new Label("Enter Your Name:");
        root.getChildren().add(nameLabel);

        nameInput = new TextField();
        nameInput.setPrefSize(100,30);
        nameInput.setAlignment(Pos.CENTER);
        root.getChildren().add(nameInput);

        playBtn = new Button("START");
        playBtn.setStyle("-fx-background-color: #00B4D8; -fx-font-size: 18");
        playBtn.setPrefSize(100,50);
        root.getChildren().add(playBtn);


    }

    public VBox getRoot() {
        return root;
    }
}

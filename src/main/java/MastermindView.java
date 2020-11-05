import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

/**
 * Method to store the view(interface) of the Mastermind board
 */
public class MastermindView {

	private MastermindModel theModel;
	private HBox root;
	private VBox leftPane;
	private ArrayList<TilePane> rows;

//	/** Array for storing the guesses */
//	private ArrayList<Circle> guess;
//
//	/** Array for storing the feedbacks of each guess */
//	private ArrayList<Circle > feedback;

	public MastermindView(MastermindModel theModel) {
		this.theModel = theModel;
		// Create a new HBox instance that displays the overall layout
		root = new HBox(20);
		root.setPadding(new Insets(10));
		root.setAlignment(Pos.CENTER);
		root.setMinHeight(831);
		root.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		// Left pane (the big ass pane that show the user input as well as the feedbacks
		leftPane = new VBox(16);
		leftPane.setAlignment(Pos.CENTER);
		leftPane.setMinHeight(778);
		leftPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		// DropShadow that is going to be used for a lot of objects
		DropShadow shadow1 = new DropShadow(6, 0, 3, Color.web("000000", 0.12));
		DropShadow shadow2 = new DropShadow(16, 0, 6, Color.web("000000", 0.08));
		DropShadow shadow3 = new DropShadow(28, 0, 9, Color.web("000000", 0.05));
		leftPane.setEffect(shadow1);
		leftPane.setEffect(shadow2);
		leftPane.setEffect(shadow3);

		// rows in the guesses pane
		rows = new ArrayList<>();
		for (int i=0; i<MastermindModel.MAX_GUESS; i++) {
			// Input pegs
			Circle circle = new Circle(13.5, Color.WHITE);
			InnerShadow innerShadow = new InnerShadow();
			innerShadow.setOffsetX(-4);
			innerShadow.setOffsetY(3);
			innerShadow.setColor(Color.web("062644", 0.08));
			circle.setEffect(innerShadow);
			// Feedback pegs
			Circle circleSm = new Circle(7, Color.WHITE);
			circleSm.setEffect(innerShadow);
			// Feedback pegs group box
			GridPane fbBox = new GridPane();
			fbBox.setHgap(8);
			fbBox.setVgap(8);
			fbBox.add(circleSm, 0, 0);
//			fbBox.add(circleSm, 1, 0);
//			fbBox.add(circleSm, 0, 1);
//			fbBox.add(circleSm, 1, 1);

			TilePane row = new TilePane();
			row.setHgap(34);
			row.setAlignment(Pos.CENTER);
			row.getChildren().add(circle);
			row.getChildren().add(circle);
			row.getChildren().add(circle);
			row.getChildren().add(circle);
			row.getChildren().add(fbBox);

			rows.add(row);
		}
		leftPane.getChildren().addAll(rows);

		root.getChildren().add(leftPane);

	}

	/**
	 * Getter for the root
	 * @return HBox object
	 */
	public HBox getRoot() {
		return root;
	}

}

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * Method to store the view(interface) of the Mastermind board
 */
public class MastermindView {

	private MastermindModel theModel;
	private final HBox root;
	private VBox leftPane;
	private BorderPane rightPane;

	/** a bunch of buttons */
	private Button deleteBtn;
	private Button checkBtn;
	private Button hintBtn;
	private Button rulesBtn;
	private Button resetBtn;
	private Button quitBtn;

	/** Pegs on the right tray */
	private ArrayList<Circle> pegsTray;

	/** Array for storing a list of input rows */
	private ArrayList<TilePane> rows;

	/** Array for storing the guesses */
	private ArrayList<ArrayList<Circle> > guesses;

	/** Array for storing the feedbacks of each guess */
	private ArrayList<ArrayList<Circle> > feedbacks;

	private Text nameText;

	public MastermindView(MastermindModel theModel) {
		this.theModel = theModel;
		// Create a new HBox instance that displays the overall layout
		root = new HBox(30);
		root.setPadding(new Insets(20));
		root.setAlignment(Pos.CENTER);
		root.setMaxHeight(660);
		root.setMinWidth(800);
		root.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

		// Initialize the left pane
		initLeftPane();
		// Initialize the right pane
		initRightPane();

		// Add the two panes to root
		root.getChildren().addAll(leftPane, rightPane);
	}

	/**
	 * Method for creating the right pane (for buttons and stuffs)
	 */
	private void initRightPane() {
		rightPane = new BorderPane();
		rightPane.setMinHeight(620);
		rightPane.setPadding(new Insets(10, 0, 0, 0));
		// TODO: Explain all of this shit
		FlowPane topPane = new FlowPane(Orientation.VERTICAL);
		topPane.setVgap(10);

		// Player Label
		HBox title1 = new HBox();
		Label nameLabel = new Label("Player: ");
		nameText = new Text(theModel.getPlayer().getPlayerName());
		nameText.setId("text-bold");
		title1.getChildren().addAll(nameLabel, nameText);
		title1.setAlignment(Pos.CENTER);
		// "Turns left" Label
		HBox title2 = new HBox();
		Label turnLabel = new Label("You have: ");
		Text turnText = new Text(String.format("%d turns left", theModel.MAX_GUESS - theModel.getCurrGuess()));
		turnText.setId("text-bold");
		title2.getChildren().addAll(turnLabel, turnText);
		title2.setAlignment(Pos.CENTER);

		// Peg tray
		HBox trayBox = new HBox(20);
		trayBox.setAlignment(Pos.CENTER);
		trayBox.setPadding(new Insets(10, 40, 10, 40));
		trayBox.setId("pane-with-shadow");
		// Pegs inside the box
		pegsTray = new ArrayList<>();
		Circle redPeg = new Circle(13.5, Peg.THE_RED_PEG.getColor());
		Circle yellowPeg = new Circle(13.5, Peg.THE_YELLOW_PEG.getColor());
		Circle greenPeg = new Circle(13.5, Peg.THE_GREEN_PEG.getColor());
		Circle bluePeg = new Circle(13.5, Peg.THE_BLUE_PEG.getColor());
		pegsTray.add(redPeg);
		pegsTray.add(yellowPeg);
		pegsTray.add(greenPeg);
		pegsTray.add(bluePeg);
		trayBox.getChildren().addAll(pegsTray);

		// Delete button
		deleteBtn = new Button("Delete");
		deleteBtn.setStyle("-fx-text-fill: white; -fx-background-color: rgba(0, 0, 0, 0.85)");
		// check answer button
		checkBtn = new Button("Check answer");
		checkBtn.setStyle("-fx-text-fill: white; -fx-background-color: rgb(239, 71, 111)");
		// hint button
		hintBtn = new Button("Hint");

		//omg im so high rn
		FlowPane botPane = new FlowPane(Orientation.VERTICAL);
		botPane.setVgap(10);
		// rules button
		rulesBtn = new Button("Rules");
		// reset button
		resetBtn = new Button("Reset the game");
		// quit button
		quitBtn = new Button("Quit");
		quitBtn.setStyle("-fx-text-fill: white; -fx-background-color: rgba(0, 0, 0, 0.85)");

		// Add everything
		topPane.getChildren().addAll(title1, title2, trayBox, deleteBtn, checkBtn, hintBtn);
		botPane.getChildren().addAll(rulesBtn, resetBtn, quitBtn);
		botPane.setAlignment(Pos.BOTTOM_CENTER);
		rightPane.setTop(topPane);
		rightPane.setBottom(botPane);
	}

	/**
	 * method for creating the left pane (the big ass pane that show the user input as well as the feedbacks)
	 */
	private void initLeftPane() {
		leftPane = new VBox(16);
		leftPane.setPrefHeight(620);
		leftPane.setAlignment(Pos.CENTER);
		leftPane.setPadding(new Insets(10, 40, 10, 40));
		leftPane.setId("pane-with-shadow");

		// Initialize guesses and feedbacks
		guesses = new ArrayList<ArrayList<Circle> >();
		feedbacks = new ArrayList<ArrayList<Circle> >();

		// rows in the guesses(left) pane
		rows = new ArrayList<>();
		for (int i = 0; i< theModel.MAX_GUESS; i++) {
			// TODO: find a way to replace g with theModel.getUserGuess.getCODE_LENGTH (was not be able to do that because of a bs error)
			for (int g=0; g<4; g++) {
				guesses.add(new ArrayList<>());
				feedbacks.add(new ArrayList<>());
				// Input pegs
				Circle guessPeg = new Circle(13.5);
				guessPeg.setId("blank-circle");
				guesses.get(i).add(guessPeg);
				// Feedback pegs
				Circle fbPeg = new Circle(7);
				fbPeg.setId("blank-circle");
				feedbacks.get(i).add(fbPeg);
			}
			// Feedback pegs group box
			GridPane fbBox = new GridPane();
			fbBox.setHgap(8);
			fbBox.setVgap(8);
			fbBox.add(feedbacks.get(i).get(0), 0, 0);
			fbBox.add(feedbacks.get(i).get(1), 1, 0);
			fbBox.add(feedbacks.get(i).get(2), 0, 1);
			fbBox.add(feedbacks.get(i).get(3), 1, 1);

			// A single row
			TilePane row = new TilePane();
			row.setHgap(20);
			row.setAlignment(Pos.CENTER);
			row.getChildren().addAll(guesses.get(i));
			row.getChildren().add(fbBox);
			// Add to the rows list
			rows.add(row);
		}
		leftPane.getChildren().addAll(rows);
	}

	/**
	 * Getter for the root
	 * @return HBox object
	 */
	public HBox getRoot() {
		return root;
	}

	public ArrayList<TilePane> getRows() {
		return rows;
	}

	public ArrayList<ArrayList<Circle>> getGuesses() {
		return guesses;
	}

	public ArrayList<ArrayList<Circle>> getFeedbacks() {
		return feedbacks;
	}

	public Button getDeleteBtn() {
		return deleteBtn;
	}

	public Button getCheckBtn() {
		return checkBtn;
	}

	public Button getHintBtn() {
		return hintBtn;
	}

	public Button getRulesBtn() {
		return rulesBtn;
	}

	public Button getResetBtn() {
		return resetBtn;
	}

	public Button getQuitBtn() {
		return quitBtn;
	}

	public void updateName(String playerName) {
		nameText.setText(playerName);
	}
}
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * Method to store the view(interface) of the Mastermind board
 */
public class MastermindView {
	private static final double BUTTON_WIDTH = 340;
	private static final double BUTTON_HEIGHT = 40;
	private static final double PANE_HEIGHT = 640;
	private static final double ROOT_HEIGHT = 660;

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

	/** Global Font */
	private final Font fontRegular;
	private final Font fontBlack;

	/** DropShadow(s) that are going to be used for a lot of objects */
	private final DropShadow shadow1;
	private final DropShadow shadow2;
	private final DropShadow shadow3;

	/** InnerShadow that is going to be used for a lot of objects */
	private final InnerShadow innerShadow;

	/** Pegs on the right tray */
	private ArrayList<Circle> pegsTray;

	/** Array for storing a list of input rows */
	private ArrayList<TilePane> rows;

	/** Array for storing the guesses */
	private ArrayList<ArrayList<Circle> > guesses;

	/** Array for storing the feedbacks of each guess */
	private ArrayList<ArrayList<Circle> > feedbacks;

	public MastermindView(MastermindModel theModel) {
		this.theModel = theModel;
		// Create a new HBox instance that displays the overall layout
		root = new HBox(30);
		root.setPadding(new Insets(20));
		root.setAlignment(Pos.CENTER);
		root.setMaxHeight(ROOT_HEIGHT);
		root.setMinWidth(800);
		root.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

		// Init font
		fontRegular = Font.font("Roboto", FontWeight.NORMAL, FontPosture.REGULAR, 14);
		fontBlack = Font.font("Roboto", FontWeight.BLACK, FontPosture.REGULAR, 14);
		// Init shadows
		shadow1 = new DropShadow(6, 0, 3, Color.web("000000", 0.12));
		shadow2 = new DropShadow(16, 0, 6, Color.web("000000", 0.08));
		shadow3 = new DropShadow(28, 0, 9, Color.web("000000", 0.05));
		// Init innerShadow
		innerShadow = new InnerShadow();
		innerShadow.setOffsetX(-4);
		innerShadow.setOffsetY(3);
		innerShadow.setColor(Color.web("062644", 0.08));

		// Initialize the left pane
		initLeftPane(theModel);
		// Initialize the right pane
		initRightPane(theModel);

		// Add the two panes to root
		root.getChildren().addAll(leftPane, rightPane);
	}

	/**
	 * Method for creating the right pane (for buttons and stuffs)
	 * @param theModel
	 */
	private void initRightPane(MastermindModel theModel) {
		rightPane = new BorderPane();
		rightPane.setMinHeight(ROOT_HEIGHT-40);
		rightPane.setPadding(new Insets(10, 0, 0, 0));
		// TODO: Explain all of this shit
		FlowPane topPane = new FlowPane(Orientation.VERTICAL);
		topPane.setVgap(10);

		// Player Label
		HBox title1 = new HBox();
		Label nameLabel = new Label("Player: ");
		nameLabel.setFont(fontRegular);
		Text nameText = new Text(theModel.getPlayer().getPlayerName());
		nameText.setFont(fontBlack);
		title1.getChildren().addAll(nameLabel, nameText);
		title1.setAlignment(Pos.CENTER);
		// "Turns left" Label
		HBox title2 = new HBox();
		Label turnLabel = new Label("You have: ");
		turnLabel.setFont(fontRegular);
		Text turnText = new Text(String.format("%d turns left", theModel.MAX_GUESS - theModel.getCurrGuess()));
		turnText.setFont(fontBlack);
		title2.getChildren().addAll(turnLabel, turnText);
		title2.setAlignment(Pos.CENTER);

		// Peg tray
		HBox trayBox = new HBox(20);
		trayBox.setMinWidth(BUTTON_WIDTH);
		trayBox.setAlignment(Pos.CENTER);
		trayBox.setPadding(new Insets(10, 40, 10, 40));
		trayBox.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(8), Insets.EMPTY)));
		trayBox.setEffect(shadow1);
		trayBox.setEffect(shadow2);
		trayBox.setEffect(shadow3);
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
		for (Circle c : pegsTray) {
			c.setEffect(shadow1);
			c.setEffect(shadow2);
			c.setEffect(shadow3);
		}
		trayBox.getChildren().addAll(pegsTray);

		// Button huhu
		deleteBtn = new Button("Delete");
		deleteBtn.setFont(fontRegular);
		deleteBtn.setStyle("-fx-text-fill: white");
		deleteBtn.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(8), Insets.EMPTY)));
		deleteBtn.setEffect(shadow1);
		deleteBtn.setEffect(shadow2);
		deleteBtn.setEffect(shadow3);
		deleteBtn.setMinWidth(BUTTON_WIDTH);
		deleteBtn.setMinHeight(BUTTON_HEIGHT);
		// Button huhu
		checkBtn = new Button("Check answer");
		checkBtn.setFont(fontRegular);
		checkBtn.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(8), Insets.EMPTY)));
		checkBtn.setEffect(shadow1);
		checkBtn.setEffect(shadow2);
		checkBtn.setEffect(shadow3);
		checkBtn.setMinWidth(BUTTON_WIDTH);
		checkBtn.setMinHeight(BUTTON_HEIGHT);
		// Button huhu
		hintBtn = new Button("Hint");
		hintBtn.setFont(fontRegular);
		hintBtn.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(8), Insets.EMPTY)));
		hintBtn.setEffect(shadow1);
		hintBtn.setEffect(shadow2);
		hintBtn.setEffect(shadow3);
		hintBtn.setMinWidth(BUTTON_WIDTH);
		hintBtn.setMinHeight(BUTTON_HEIGHT);

		//omg im so high rn
		FlowPane botPane = new FlowPane(Orientation.VERTICAL);
		botPane.setVgap(10);
		// Button huhu
		rulesBtn = new Button("Rules");
		rulesBtn.setFont(fontRegular);
		rulesBtn.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(8), Insets.EMPTY)));
		rulesBtn.setEffect(shadow1);
		rulesBtn.setEffect(shadow2);
		rulesBtn.setEffect(shadow3);
		rulesBtn.setMinWidth(BUTTON_WIDTH);
		rulesBtn.setMinHeight(BUTTON_HEIGHT);
		// Button huhu
		resetBtn = new Button("Reset the game");
		resetBtn.setFont(fontRegular);
		resetBtn.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(8), Insets.EMPTY)));
		resetBtn.setEffect(shadow1);
		resetBtn.setEffect(shadow2);
		resetBtn.setEffect(shadow3);
		resetBtn.setMinWidth(BUTTON_WIDTH);
		resetBtn.setMinHeight(BUTTON_HEIGHT);
		// Button huhu
		quitBtn = new Button("Quit");
		quitBtn.setFont(fontRegular);
		quitBtn.setStyle("-fx-text-fill: white");
		quitBtn.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(8), Insets.EMPTY)));
		quitBtn.setEffect(shadow1);
		quitBtn.setEffect(shadow2);
		quitBtn.setEffect(shadow3);
		quitBtn.setMinWidth(BUTTON_WIDTH);
		quitBtn.setMinHeight(BUTTON_HEIGHT);

		// Add everything
		topPane.getChildren().addAll(title1, title2, trayBox, deleteBtn, checkBtn, hintBtn);
		botPane.getChildren().addAll(rulesBtn, resetBtn, quitBtn);
		botPane.setAlignment(Pos.BOTTOM_CENTER);
		rightPane.setTop(topPane);
		rightPane.setBottom(botPane);
	}

	/**
	 * method for creating the left pane (the big ass pane that show the user input as well as the feedbacks)
	 * @param theModel
	 */
	private void initLeftPane(MastermindModel theModel) {
		leftPane = new VBox(16);
		leftPane.setPrefHeight(PANE_HEIGHT);
		leftPane.setAlignment(Pos.CENTER);
		leftPane.setPadding(new Insets(10, 40, 10, 40));
		leftPane.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(8), Insets.EMPTY)));
		leftPane.setEffect(shadow1);
		leftPane.setEffect(shadow2);
		leftPane.setEffect(shadow3);

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
				Circle guessPeg = new Circle(13.5, Color.WHITE);
				guessPeg.setEffect(innerShadow);
				guesses.get(i).add(guessPeg);
				// Feedback pegs
				Circle fbPeg = new Circle(7, Color.WHITE);
				fbPeg.setEffect(innerShadow);
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
}
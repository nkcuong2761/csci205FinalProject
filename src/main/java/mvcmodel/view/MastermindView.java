package mvcmodel.view;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import mvcmodel.MastermindModel;
import objects.Peg;
import objects.PegSequence;

import java.util.ArrayList;

/**
 * Method to store the view(interface) of the Mastermind board
 */
public class MastermindView {
    /** The model of the game */
    private MastermindModel theModel;

    /** The root of the view */
    private HBox root;

    /** The right pane of the game */
    private VBox leftPane;

    /** The left pane of the game */
    private BorderPane rightPane;

    /**
     * a bunch of buttons
     */
    private Button deleteBtn;
    private Button checkBtn;
    private Button hintBtn;
    private Button rulesBtn;
    private Button resetBtn;
    private Button quitBtn;

    /**
     * Pegs on the right tray
     */
    private ArrayList<Circle> pegsTray;

    /**
     * Array for storing a list of input rows
     */
    private ArrayList<TilePane> rows;

    /**
     * Array for storing the guesses
     */
    private ArrayList<ArrayList<Circle>> guesses;

    /**
     * Array for storing the feedbacks of each guess
     */
    private ArrayList<ArrayList<Circle>> feedbacks;

    private Text nameText;
    private Text turnText;

    /** Tooltips for questionCircle(left) */
    private ArrayList<Button> questionIconList;
    /** Line Indicator */
    private ArrayList<ImageView> lineIndicators;
    /** Questions Icons(right) */
    private Button questionCircleBig;

    /** output string to show the text below the result (next steps etc.) */
    private Text outputString;

    /** output message for winning/losing */
    private Label outputLabel;

    /** Tooltip for questionCircle(right) */
    private Tooltip tooltipRight;


    public MastermindView(MastermindModel theModel) {
        this.theModel = theModel;
        // Create a new HBox instance that displays the overall layout
        root = new HBox(30);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setMaxHeight(740);
        root.setMinWidth(850);
        root.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        // Initialize Tooltip(s)
        initExtras();
        // Initialize the left pane
        initLeftPane();
        // Initialize the right pane
        initRightPane();

        // Add the two panes to root
        root.getChildren().addAll(leftPane, rightPane);
    }

    /**
     * Method to erase the previous scene to restart the program
     */
    public void createNewScene() {
        updateTurnLeftString();
        restartLeftPane();
    }

    /**
     * Method to initialize tooltips and line indicators
     */
    private void initExtras() {
        tooltipRight = new Tooltip("Click on each colored peg until you've filled the row\n" +
                "Use the \"Delete\" button to delete your guess");
        tooltipRight.setShowDelay(Duration.seconds(0));
        questionIconList = new ArrayList<>();
        lineIndicators = new ArrayList<>();
    }

    /**
     * Method to reinitialize all variables in the left pane into blank states
     */
    private void restartLeftPane() {
        // Initialize through the 12 rows
        for (int i = 0; i < theModel.getMaxGuess(); i++) {
            // Get the user input rows
            TilePane currentRow = getRows().get(i);
            // Get the list of circle that we have to change
            ArrayList<Circle> currResponse = getFeedbacks().get(i);
            // Iterate through the four options
            for (int g = 1; g < PegSequence.getSequenceLength() + 1; g++) {
                currentRow.getChildren().get(g).setId("blank-circle");
                currResponse.get(g-1).setId("blank-circle");
            }
            // Reset line indicators
            ImageView currTrig = getLineIndicators().get(i);
            currTrig.setVisible(false);
            // Reset tooltips
            Button currIcon = getQuestionIconList().get(i);
            currIcon.setVisible(false);
        }
        // Set the first line indicator to be visible
        ImageView newTrig = getLineIndicators().get(0);
        newTrig.setVisible(true);
    }

    /**
     * Method for creating the right pane (for buttons and stuffs)
     */
    private void initRightPane() {
        rightPane = new BorderPane();
        rightPane.setMinHeight(620);
        rightPane.setPadding(new Insets(10, 0, 0, 0));

        // The topPane that hold the name label, turns left label, trayBox, deleteBtn, checkBtn and hintBtn
        FlowPane topPane = new FlowPane(Orientation.VERTICAL);
        topPane.setVgap(10);
        topPane.setAlignment(Pos.TOP_CENTER);
        // Initialize the player name upper box
        HBox title1 = initPlayerNameBox();
        // Initialize the turn remaining box
        HBox title2 = initTurnRemainBox();
        // Initialize the peg tray
        HBox trayBox = initPegTray();
        // Delete button
        deleteBtn = new Button("Delete");
        deleteBtn.setStyle("-fx-text-fill: white; -fx-background-color: rgba(0, 0, 0, 0.85)");
        // check answer button
        checkBtn = new Button("Check answer");
        checkBtn.setStyle("-fx-text-fill: white; -fx-background-color: rgb(239, 71, 111)");
        // hint button
        hintBtn = new Button("Hint");
        // Initialize the middle pane
        VBox midPane = initMidPane();
        // Initialize the bottom pane
        FlowPane botPane = initBotPane();

        // Add everything
        topPane.getChildren().addAll(title1, title2, trayBox, deleteBtn, checkBtn, hintBtn);
        midPane.getChildren().addAll(outputLabel, outputString);
        botPane.getChildren().addAll(rulesBtn, resetBtn, quitBtn);
        rightPane.setTop(topPane);
        rightPane.setCenter(midPane);
        rightPane.setBottom(botPane);
    }

    /**
     * Method to initialize the peg tray
     * @return trayBox - HBox containing the peg tray
     */
    private HBox initPegTray() {
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
        questionCircleBig = new Button();
        questionCircleBig.setId("iconQuestion");
        questionCircleBig.setTooltip(tooltipRight);
        // Set the drop shadow effect for them
        redPeg.setId("peg-circle");
        yellowPeg.setId("peg-circle");
        greenPeg.setId("peg-circle");
        bluePeg.setId("peg-circle");
        pegsTray.add(redPeg);
        pegsTray.add(yellowPeg);
        pegsTray.add(greenPeg);
        pegsTray.add(bluePeg);
        trayBox.getChildren().addAll(pegsTray);
        trayBox.getChildren().add(questionCircleBig);
        return trayBox;
    }

    /**
     * Method to initialize the number of turns left
     * @return title2 - an HBox for the text string of the turns left
     */
    private HBox initTurnRemainBox() {
        // "Turns left" Label
        HBox title2 = new HBox();
        Label turnLabel = new Label("You have: ");
        turnText = new Text(String.format("%d turns left", theModel.getMaxGuess()));
        turnText.setId("text-bold");
        title2.getChildren().addAll(turnLabel, turnText);
        title2.setAlignment(Pos.CENTER);
        return title2;
    }

    /**
     * Method to initialize the player name text string in an HBox
     * @return title1 - an HBox for the text string
     */
    private HBox initPlayerNameBox() {
        // objects.Player Label
        HBox title1 = new HBox();
        Label nameLabel = new Label("Player: ");
        nameText = new Text(theModel.getPlayer().getPlayerName());
        nameText.setId("text-bold");
        title1.getChildren().addAll(nameLabel, nameText);
        title1.setAlignment(Pos.CENTER);
        return title1;
    }

    /**
     * Method to initialize the middle pane
     * @return mid - The VBox containing feedback information
     */
    private VBox initMidPane() {
        // The midPane that hold the output label and string
        VBox midPane = new VBox(10);
        midPane.setAlignment(Pos.CENTER);
        // Output label above the string
        outputLabel = new Label("");
        outputLabel.setId("resultMsg");
        // Output string above the button
        outputString = new Text("");
        outputString.setId("text-normal");
        return midPane;
    }

    /**
     * Initialize the bottom pane of the right pane
     * @return bot - The flowpane initialized
     */
    private FlowPane initBotPane() {
        // Flow Pane in the bottom
        FlowPane botPane = new FlowPane(Orientation.VERTICAL);
        botPane.setVgap(10);
        botPane.setAlignment(Pos.BOTTOM_CENTER);
        // rules button
        rulesBtn = new Button("Rules");
        // reset button
        resetBtn = new Button("Restart the game");
        // quit button
        quitBtn = new Button("Quit");
        quitBtn.setStyle("-fx-text-fill: white; -fx-background-color: rgba(0, 0, 0, 0.85)");
        return botPane;
    }

    /**
     * method for creating the left pane (the big pane that show the user input as well as the feedbacks)
     */
    private void initLeftPane() {
        leftPane = new VBox(16);
        leftPane.setPrefHeight(620);
        leftPane.setMinWidth(500);
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setPadding(new Insets(10, 20, 10, 20));
        leftPane.setId("pane-with-shadow");

        // Initialize guesses and feedbacks
        guesses = new ArrayList<ArrayList<Circle>>();
        feedbacks = new ArrayList<ArrayList<Circle>>();
        // Initialize all the rows
        initRow();
        leftPane.getChildren().addAll(rows);
    }

    /**
     * Method to initialize all the rows in the board
     */
    private void initRow() {
        // rows in the guesses(left) pane
        rows = new ArrayList<>();
        for (int i = 0; i < theModel.getMaxGuess(); i++) {
            // indicator holder
            Pane indicatorBox = new FlowPane();
            indicatorBox.setId("indicatorHolder");
            // indicator icon
            ImageView icon = new ImageView(getClass().getResource("/assets/indicatorTriangle.png").toExternalForm());
            icon.setFitHeight(22);
            icon.setFitWidth(22);
            // show the indicator if this is the first guess
            if (i==0)
                icon.setVisible(true);
            else
                icon.setVisible(false);
            lineIndicators.add(icon);
            indicatorBox.getChildren().add(lineIndicators.get(i));
            // Initialize the peg sequence: input pegs and feedback pegs
            initPegSequence(i);
            // Feedback pegs group box
            GridPane fbBox = new GridPane();
            fbBox.setHgap(8);
            fbBox.setVgap(8);
            fbBox.add(feedbacks.get(i).get(0), 0, 0);
            fbBox.add(feedbacks.get(i).get(1), 1, 0);
            fbBox.add(feedbacks.get(i).get(2), 0, 1);
            fbBox.add(feedbacks.get(i).get(3), 1, 1);
            // iconBox holder
            Pane iconBox = new FlowPane();
            iconBox.setId("iconHolder");
            //QuestionCircle Icon
            Button questionCircleSmall = new Button();
            questionCircleSmall.setVisible(false);
            questionCircleSmall.setId("iconQuestionSmall");
            questionIconList.add(questionCircleSmall);
            iconBox.getChildren().add(questionIconList.get(i));

            // A single row
            TilePane row = new TilePane();
            row.setHgap(20);
            row.setAlignment(Pos.CENTER);
            row.getChildren().add(indicatorBox);
            row.getChildren().addAll(guesses.get(i));
            row.getChildren().addAll(fbBox, iconBox);
            // Add to the rows list
            rows.add(row);
        }
    }

    /**
     * Method to initialize the user guesses and the feedback pegs
     * @param i - Index of the current row
     */
    private void initPegSequence(int i) {
        // Peg sequence
        for (int g = 0; g < PegSequence.getSequenceLength(); g++) {
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
    }

    public ArrayList<Circle> getPegsTray() {
        return pegsTray;
    }

    /**
     * Getter for the root
     *
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

    public ArrayList<Button> getQuestionIconList() {
        return questionIconList;
    }

    public ArrayList<ImageView> getLineIndicators() {
        return lineIndicators;
    }

    public void updateName(String playerName) {
        nameText.setText(playerName);
    }

    /**
     * Method to update the string displaying the number of turns left
     */
    public void updateTurnLeftString() {
        turnText.setText(String.format("%d turns left", theModel.MAX_GUESS - theModel.getCurrGuess()));
    }

    /**
     * Method to update the max number of turns allowed for the participant
     * @param maxTurns - Max number of turns needed
     */
    public void updateMaxTurns(int maxTurns) {
        turnText.setText(String.format("%d turns left", maxTurns));
    }

    /**
     * Method to update the output string to show the feedback to the user for trivial message
     * For important message, use the updateOutputLabel() method.
     * @param string - String of feedback
     */
    public void updateOutputString(String string) {
        outputString.setText(string);
    }

    /**
     * Method to update the output label to show the feedback to the user for important message.
     * For trivial message, use the updateOutputString() method.
     * @param string - String of feedback
     */
    public void updateOutputLabel(String string) {
        outputLabel.setText(string);
    }

    /**
     * Update the guesses in the game board with the corresponding pegs the user chooses
     * @param rowNumber - Index of the row
     * @param pegNumber - Index of the position of the peg (from 1 to 4)
     * @param newColor - The color of the peg the user chooses
     */
    public void updateGuess(int rowNumber, int pegNumber, Color newColor) {
        TilePane currentRow = rows.get(rowNumber);
        ObservableList<Node> guesses = currentRow.getChildren();
        Circle circleToChange = (Circle) guesses.get(pegNumber);
        // Delete the peg for the delete button
        if (newColor.equals(Color.WHITE)) {
            circleToChange.setId("blank-circle");
        }
        // Change to the user guess
        else {
            circleToChange.setId("peg-circle");
            circleToChange.setFill(newColor);
        }
    }

    /**
     * Method to turn the comparison result sequence into pegs, update the Question Icon and the line Indicator
     *
     * @param rowNumber        - The current row to display the output to
     * @param comparisonResult - The output obtained from comparing the secret code in the model
     */
    public void updateRow(int rowNumber, PegSequence comparisonResult) {
        // Get the list of circle that we have to change
        ArrayList<Circle> currResponse = getFeedbacks().get(rowNumber);
        // Get the list of buttons that we have to change
        Button currIcon = getQuestionIconList().get(rowNumber);
        currIcon.setVisible(true);
        // Update corresponding button and set tooltip
        Tooltip tooltip = new Tooltip("");
        int countBlack = 0;
        int countWhite = 0;
        for (int i = 0; i < PegSequence.getSequenceLength(); i++) {
            // Check for the black peg
            if (comparisonResult.getSequence().get(i).equals(Peg.THE_BLACK_PEG)) {
                currResponse.get(i).setId("black-peg");
                countBlack += 1;
            } else if (comparisonResult.getSequence().get(i).equals(Peg.THE_WHITE_PEG)) {
                currResponse.get(i).setId("white-peg");
                countWhite += 1;
            }
        }
        tooltip.setText(String.format("You got %d peg with right color in the right position and\n" +
                "%d peg with right color but in the wrong position", countBlack, countWhite));
        tooltip.setShowDelay(Duration.seconds(0));
        currIcon.setTooltip(tooltip);

        // show the current indicator icon and hide the previous icon
        ImageView prevTrig = getLineIndicators().get(rowNumber);
        prevTrig.setVisible(false);
        if (rowNumber < 11) {
            ImageView currTrig = getLineIndicators().get(rowNumber + 1);
            currTrig.setVisible(true);
        }
    }

    /**
     * Method to handle when an user has win. Output the winning/losing label and the text string method
     *
     * @param win - Indicate if the user has successfully
     */
    public void displayEndGame(boolean win) {
        if (win) {
            updateOutputLabel("YOU WON! LET'S GOOO!!!");
            outputLabel.setTextFill(Color.web("023E8A"));
        } else {
            updateOutputLabel("You lost! Better luck next time");
            outputLabel.setTextFill(Color.web("EF476F"));
        }
        updateOutputString("You can:\n    Hit Restart the game to play a new one\n    Change mode to multiplayer option\n    Exit the game!");
    }
}
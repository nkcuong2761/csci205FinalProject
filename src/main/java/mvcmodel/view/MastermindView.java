package mvcmodel.view;
/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Cuong Nguyen
            Lily Parker
            Minh Anh Phan
            Anurag Vadiya
 * Section: 01
 * Date: 11/5/20
 * Time: 5:51 PM
 *
 * Project: csci205FinalProject
 * Package: mvcmodel.view
 * Class: mvcmodel.view.MastermindView
 *
 * Description: Class for the main game view
 *
 * *****************************************
 */

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import mvcmodel.model.MastermindModel;
import objects.Peg;
import objects.PegSequence;

import java.io.FileNotFoundException;
import java.util.ArrayList;


/**
 * Class to store the view(interface) of the Mastermind board
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

    /** delete guesses btn **/
    private Button deleteBtn;

    /** Check guess btn **/
    private Button checkBtn;

    /** Get hint btn **/
    private Button hintBtn;

    /** See rules btn **/
    private Button rulesBtn;

    /** reset the game btn **/
    private Button resetBtn;

    /** Quit game btn **/
    private Button quitBtn;

    /** Peg options **/
    private ArrayList<Circle> pegsTray;

    /** Array for storing a list of input rows */
    private ArrayList<TilePane> rows;

    /** Array for storing the guesses */
    private ArrayList<ArrayList<Circle>> guesses;

    /** Array for storing the feedbacks of each guess */
    private ArrayList<ArrayList<Circle>> feedbacks;

    /** Text to represent name of the player */
    private Text nameText;

    /** Text to represent the number of turns left */
    private Text turnText;

    /** Tooltips for questionCircle(left) */
    private ArrayList<Button> questionIconList;

    /** Line Indicator */
    private ArrayList<ImageView> lineIndicators;

    /** Questions Icons(right) */
    private Button questionCircleBig;

    /** Button to go back to the previous screen*/
    private Button backBtn;

    /** Theme icon */
    private Button themeBtn;

    /** Theme option circle array */
    private ArrayList<Circle> themeOption;

    /** Theme tray that contains the theme list */
    private FlowPane themeTray;

    /** Output string to show the text below the result (next steps etc.) */
    private Text outputString;

    /** output message for winning/losing */
    private Label outputLabel;

    /** Tooltip for questionCircle(right) */
    private Tooltip tooltipRight;

    /** Middle box on the right to display rules, hints, winning, and losing messages */
    private VBox midPane;

    /** Media object to store winning or losing audio **/
    private Media media;

    /** Media player to get winning or losing audio from memory **/
    private MediaPlayer mediaPlayer;

    /** Final String array */
    private static final String[] STYLE_CLASS_STRING = new String[]{"first-peg", "second-peg", "third-peg", "fourth-peg", "fifth-peg", "sixth-peg"};


    /**
     * The constructor for the main view
     * @param theModel - the model of the game
     */
    public MastermindView(MastermindModel theModel) {
        this.theModel = theModel;
        // Create a new HBox instance that displays the overall layout
        root = new HBox(30);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setMaxHeight(800);
        root.setMinWidth(800);

        // Initialize Tooltip(s)
        initExtras();
        // Initialize the back button by putting it in a FlowPane
        FlowPane sidePane = new FlowPane();
        sidePane.setMaxWidth(27);
        sidePane.setAlignment(Pos.CENTER);
        backBtn = new Button();
        backBtn.setId("backBtn");
        sidePane.getChildren().add(backBtn);
        // Initialize the left pane
        initLeftPane();
        // Initialize the right pane
        initRightPane();

        // Add the two panes to root
        root.getChildren().addAll(sidePane, leftPane, rightPane);
    }

    /**
     * Method to erase the previous scene to restart the program
     */
    public void createNewScene() {
        updateTurnLeftString();
        restartLeftPane();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

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

        updateMidPane();

    }

    /**
     * Update the MidPane for restart
     */
    private void updateMidPane() {
        // remove things from midpane for restart
        if (midPane.getChildren().size() == 3) {
            midPane.getChildren().remove(midPane.getChildren().size() - 1);
        }
        else if (midPane.getChildren().size() == 4){
            midPane.getChildren().remove(midPane.getChildren().size() - 1);
            midPane.getChildren().remove(midPane.getChildren().size() - 1);
        }
    }

    /**
     * Method for creating the right pane (for buttons and display boxes)
     */
    private void initRightPane() {
        rightPane = new BorderPane();
        rightPane.setMinHeight(620);
//        rightPane.setPadding(new Insets(10, 0, 0, 0));

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
        deleteBtn.setId("blackBtn");
        // check answer button
        checkBtn = new Button("Check answer");
        checkBtn.setId("checkBtn");
        // hint button
        hintBtn = new Button("Hint");
        // Initialize the middle pane
        VBox midPane = initMidPane();
        // Initialize the bottom pane
        FlowPane botPane = initBotPane();

        topPane.getChildren().addAll(title1, title2, trayBox, deleteBtn, checkBtn, hintBtn);

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
        trayBox.setPadding(new Insets(20, 40, 20, 40));
        trayBox.setId("peg-pane");
        // Pegs inside the box
        pegsTray = new ArrayList<>();
        for (int i = 0; i < theModel.getNumPegs(); i++) {
            Circle circle = new Circle(13.5);
            circle.setId("peg-circle");
            circle.getStyleClass().add(STYLE_CLASS_STRING[i]);
            pegsTray.add(circle);
        }

        questionCircleBig = new Button();
        questionCircleBig.setId("iconQuestion");
        questionCircleBig.setTooltip(tooltipRight);

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
        midPane = new VBox(10);
        midPane.setAlignment(Pos.CENTER);
        midPane.setPadding(new Insets(40,0,0,0));
        // Output label above the string
        outputLabel = new Label("");
        // Output string above the button
        outputString = new Text("");
        outputString.setId("text-normal");

        midPane.getChildren().addAll(outputLabel, outputString);
        return midPane;
    }

    /**
     * Initialize the bottom pane of the right pane
     * @return bot - The flowPane initialized
     */
    private FlowPane initBotPane() {
        // Flow Pane in the bottom
        FlowPane botPane = new FlowPane(Orientation.VERTICAL);
        botPane.setVgap(10);
        botPane.setAlignment(Pos.BOTTOM_CENTER);

        // The overall container that hold both the themeBtn and the expanded themeTray
        StackPane themeContainer = new StackPane();
        themeContainer.setMinSize(340,37);
        themeContainer.setMaxWidth(800);
        themeContainer.setAlignment(Pos.CENTER_RIGHT);
        // theme tray (the bigger tray that contain the theme options and the button)
        themeTray = new FlowPane();
        themeTray.setAlignment(Pos.CENTER_LEFT);
        themeTray.setId("themeTray");
        // theme list pane
        TilePane themeList = new TilePane();
        themeList.setHgap(10);
        // init theme option
        themeOption = new ArrayList<>();
        themeOption.add(new Circle(10, Color.WHITE));
        themeOption.add(new Circle(10, Color.web("00B4D8")));
        themeOption.add(new Circle(10, Color.web("D58097")));
        Circle themeWood = new Circle(10, Color.web("604A36"));
        themeWood.setFill(new ImagePattern(new Image("assets/woodbg.jpg")));
        themeOption.add(themeWood);
        themeList.getChildren().addAll(themeOption);
        themeTray.getChildren().add(themeList);
        themeTray.setVisible(false);
        // theme button - expanded
        themeBtn = new Button();
        themeBtn.setId("themeBtn");
        // Add to the container
        themeContainer.getChildren().addAll(themeTray, themeBtn);

        // rules button
        rulesBtn = new Button("Rules");
        // reset button
        resetBtn = new Button("Restart the game");
        // quit button
        quitBtn = new Button("Quit");
        quitBtn.setId("blackBtn");

        botPane.getChildren().addAll(themeContainer, rulesBtn, resetBtn, quitBtn);
        return botPane;
    }

    /**
     * method for creating the left pane (the big pane that show the user input as well as the feedbacks)
     */
    private void initLeftPane() {
        leftPane = new VBox(13);
        leftPane.setPrefHeight(620);
        leftPane.setPrefWidth(740);
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setPadding(new Insets(10, 5, 10, 5));
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
            int count = 0;
            for (int j = 0; j < theModel.getNumPegs(); j++) {
                if(j % 2 == 0) {
                    fbBox.add(feedbacks.get(i).get(j), count,0);
                }
                else {
                    fbBox.add(feedbacks.get(i).get(j),count,1);
                    count++;
                }
            }
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
        double radius = 13.5;
        if (PegSequence.getSequenceLength() == 5) {
            radius = 12;
        }
        else if (PegSequence.getSequenceLength() == 6) {
            radius = 11;
        }

        for (int g = 0; g < PegSequence.getSequenceLength(); g++) {
            guesses.add(new ArrayList<>());
            feedbacks.add(new ArrayList<>());
            // Input pegs

            Circle guessPeg = new Circle(radius);
            guessPeg.setId("blank-circle");
            guesses.get(i).add(guessPeg);
            // Feedback pegs
            Circle fbPeg = new Circle(7);
            fbPeg.setId("blank-circle");
            feedbacks.get(i).add(fbPeg);
        }
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
     * @param className - The className of the peg the user chooses
     */
    public void updateGuess(int rowNumber, int pegNumber, String className) {
        TilePane currentRow = rows.get(rowNumber);
        ObservableList<Node> guesses = currentRow.getChildren();
        Circle circleToChange = (Circle) guesses.get(pegNumber);
        String prevClassName = circleToChange.getStyleClass().toString();
        // Delete the peg for the delete button
        circleToChange.getStyleClass().remove(prevClassName);
        if (className == null) {
            circleToChange.setId("blank-circle");
        }
        // Change to the user guess
        else {
            circleToChange.getStyleClass().add(className);
            circleToChange.setId("peg-circle");
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
        if (rowNumber < (theModel.getMaxGuess()-1)) {
            ImageView currTrig = getLineIndicators().get(rowNumber + 1);
            currTrig.setVisible(true);
        }
    }

    /**
     * Method to handle when an user has win. Output the winning/losing label and the text string method
     *
     * @param win - Indicate if the user has successfully
     */
    public void displayEndGame(boolean win) throws FileNotFoundException {
        if (win) {

            updateOutputLabel("Congratulations! You won");
            outputLabel.setId("winningMsg");

            // display winning gif
            Image image = new Image(getClass().getResource("/assets/congratulations.gif").toExternalForm());

            //Setting the image view
            ImageView imageView = new ImageView(image);

            //Setting the position of the image
            imageView.setX(50);
            imageView.setY(25);

            //setting the fit height and width of the image view
            imageView.setFitHeight(150);
            imageView.setFitWidth(160);

            //Setting the preserve ratio of the image view
            imageView.setPreserveRatio(true);

            midPane.getChildren().add(imageView);

            playWinningSound();

        } else {
            updateOutputLabel("You lost! Better luck next time");
            outputLabel.setId("losingMsg");

            // display winning gif
            Image image = new Image(getClass().getResource("/assets/sad2.gif").toExternalForm());

            //Setting the image view
            ImageView imageView = new ImageView(image);

            //Setting the position of the image
            imageView.setX(50);
            imageView.setY(60);

            //setting the fit height and width of the image view
            imageView.setFitHeight(120);
            imageView.setFitWidth(160);

            //Setting the preserve ratio of the image view
            imageView.setPreserveRatio(true);
            // Remove the white background of the gif
            imageView.setStyle("-fx-blend-mode: darken");

            // Result pegs pane
            VBox resultPane = new VBox(10);
            resultPane.setAlignment(Pos.CENTER);
            Label label = new Label("The secret code is:");
            // HBox to store the result pegs
            HBox codePane = new HBox(20);
            codePane.setAlignment(Pos.CENTER);
            codePane.setPadding(new Insets(0, 40, 0, 40));
            // Pegs inside the box
            ArrayList<Circle> secretCode = new ArrayList<>();
            char[] codeArray = theModel.getCodeMaker().getSecretCode().toString().toCharArray();
            for (char c : codeArray) {
                Circle circle = new Circle(13.5);
                circle.setId("peg-circle");
                circle.getStyleClass().add(STYLE_CLASS_STRING[Integer.parseInt(String.valueOf(c)) - 1]);
                secretCode.add(circle);
            }
            codePane.getChildren().addAll(secretCode);
            resultPane.getChildren().addAll(label, codePane);

            midPane.getChildren().addAll(imageView, resultPane);

            playLosingSound();
        }
    }

    /** Method to play winning sound if soundOn mode **/
    private void playLosingSound() {
        if (theModel.getSound()) {
            media = new Media(getClass().getResource("/assets/womp-womp.mp3").toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
        }
    }

    /** Method to play losing sound if soundOn mode **/
    private void playWinningSound() {
        if (theModel.getSound()) {
            media = new Media(getClass().getResource("/assets/crowdCheer.wav").toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setAutoPlay(true);
        }
    }

    /**
     * Method to get the pegs tray on the right
     * @return - ArrayList with the possible pegs
     */
    public ArrayList<Circle> getPegsTray() {
        return pegsTray;
    }

    /**
     * Getter for the root
     * @return HBox object
     */
    public HBox getRoot() {
        return root;
    }

    /**
     * Getter for rows
     * @return - ArrayList of TilePanes
     */
    public ArrayList<TilePane> getRows() {
        return rows;
    }

    /** Getter for guesses
     *
     * @return - ArrayList of ArrayList of Circles
     */
    public ArrayList<ArrayList<Circle>> getGuesses() {
        return guesses;
    }

    /** Getter for Feedback
     *
     * @return - ArrayList of ArrayList of Circles
     */
    public ArrayList<ArrayList<Circle>> getFeedbacks() {
        return feedbacks;
    }

    /** Getter for Delete btn
     *
     * @return - Delete btn
     */
    public Button getDeleteBtn() {
        return deleteBtn;
    }

    /** Getter for check btn
     *
     * @return - check btn
     */
    public Button getCheckBtn() {
        return checkBtn;
    }

    /** Getter for Hint btn
     *
     * @return - hint btn
     */
    public Button getHintBtn() {
        return hintBtn;
    }

    /** Getter for Rules btn
     *
     * @return - rules btn
     */
    public Button getRulesBtn() {
        return rulesBtn;
    }

    /** Getter for reset btn
     *
     * @return - reset btn
     */
    public Button getResetBtn() {
        return resetBtn;
    }

    /** Getter for quit btn
     *
     * @return - quit btn
     */
    public Button getQuitBtn() {
        return quitBtn;
    }

    /** Getter for Question icons
     *
     * @return - ArrayList of Buttons
     */
    public ArrayList<Button> getQuestionIconList() {
        return questionIconList;
    }

    /** Getter for line indicator
     *
     * @return - ArrayList of ImageView
     */
    public ArrayList<ImageView> getLineIndicators() {
        return lineIndicators;
    }

    /** Getter for Theme btn
     *
     * @return - Theme btn
     */
    public Button getThemeBtn() {
        return themeBtn;
    }

    /** Getter for Theme options
     *
     * @return - ArrayList of cirlces
     */
    public ArrayList<Circle> getThemeOption() {
        return themeOption;
    }

    /** Getter for Theme Flow Pane
     *
     * @return - FlowPane
     */
    public FlowPane getThemeTray() {
        return themeTray;
    }

    /** Getter for Style Class
     *
     * @return - String []
     */
    public static String[] getStyleClassString() { return STYLE_CLASS_STRING; }

    /** Getter for MediaPlayer
     *
     * @return - MediaPLayer
     */
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    /** Getter for back btn
     *
     * @return - back btn
     */
    public Button getBackBtn() {
        return backBtn;
    }

}
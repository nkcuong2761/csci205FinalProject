/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Anurag Vaidya
 * Section: 8:50 AM
 * Date: 11/4/2020
 * Time: 9:06 AM
 *
 * Project: csci205FinalProject
 * Package: PACKAGE_NAME
 * Class: Response
 *
 * Description:
 *
 * ****************************************
 */

import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * A class to represent the response after comparing user code with secret code
 */
public class Response {
    /**
     * Each response has a set length
     */
    private final int RESPONSE_LENGTH = 4;

    /**
     * Response is represented as an arrayList of pegs
     */
    private ArrayList<Peg> response = new ArrayList<>(RESPONSE_LENGTH);

    /**
     * Constructor to create a new response
     * @param c1, c2, c3, c4 - Four colors making up a response
     */
    public Response(Color c1, Color c2, Color c3, Color c4) {
        createResponseFromString(c1, c2, c3, c4);
    }

    /**
     * Method that converts 4 colors into a response
     * @param c1, c2, c3, c4 - Colors representing the code
     */
    public void createResponseFromString(Color c1, Color c2, Color c3, Color c4) {
        response.add(getPegCorrespondingToColor(c1));
        response.add(getPegCorrespondingToColor(c2));
        response.add(getPegCorrespondingToColor(c3));
        response.add(getPegCorrespondingToColor(c4));
    }

    /**
     * Given a color, return the correct peg
     * @param c - peg of which color
     * @return - peg with the correct color
     */
    public Peg getPegCorrespondingToColor(Color c) {
        if (c.equals(Color.BLACK)) return Peg.THE_BLACK_PEG;
        else if (c.equals(Color.WHITE)) return Peg.THE_WHITE_PEG;
        else return Peg.THE_BLACK_PEG;
    }

    @Override
    public String toString() {
        return "Code{" +
                "code=" + response +
                '}';
    }
}

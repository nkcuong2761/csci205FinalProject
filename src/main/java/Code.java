/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Anurag Vaidya
 * Section: 8:50 AM
 * Date: 11/3/2020
 * Time: 10:28 PM
 *
 * Project: csci205FinalProject
 * Package: PACKAGE_NAME
 * Class: Code
 *
 * Description:
 *
 * ****************************************
 */

import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * A class to represent the code object
 */
public class Code {

    /**
     * Each code has a set length
     */
    private final int CODE_LENGTH = 4;

    /**
     * Code is represented as an arrayList of pegs
     */
    private ArrayList<Peg> code = new ArrayList<>(CODE_LENGTH);

    /**
     * Constructor to create a new code
     * @param c1, c2, c3, c4 - Four colors making up a code
     */
    public Code(Color c1, Color c2, Color c3, Color c4) {
        createCodeFromString(c1, c2, c3, c4);
    }

    /**
     * Method that converts four colors into a code
     * @param c1, c2, c3, c4 - Colors representing the code
     */
    public void createCodeFromString(Color c1, Color c2, Color c3, Color c4) {
        code.add(getPegCorrespondingToColor(c1));
        code.add(getPegCorrespondingToColor(c2));
        code.add(getPegCorrespondingToColor(c3));
        code.add(getPegCorrespondingToColor(c4));
    }

    /**
     * Given a color, return the correct peg
     * @param c - peg of which color
     * @return - peg with the correct color
     */
    public Peg getPegCorrespondingToColor(Color c) {
        if (c.equals(Color.BLUE)) return Peg.THE_BLUE_PEG;
        else if (c.equals(Color.GREEN)) return Peg.THE_GREEN_PEG;
        else if (c.equals(Color.BROWN)) return Peg.THE_BROWN_PEG;
        else if (c.equals(Color.PURPLE)) return Peg.THE_PURPLE_PEG;
        else if (c.equals(Color.RED)) return Peg.THE_RED_PEG;
        else return Peg.THE_YELLOW_PEG;
    }

    @Override
    public String toString() {
        return "Code{" +
                "code=" + code +
                '}';
    }
}

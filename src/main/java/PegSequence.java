/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Anurag Vaidya
 * Section: 8:50 AM
 * Date: 11/8/2020
 * Time: 9:40 AM
 *
 * Project: csci205FinalProject
 * Package: PACKAGE_NAME
 * Class: PegSequence
 *
 * Description:
 *
 * ****************************************
 */

import java.util.ArrayList;
import java.util.Objects;

public class PegSequence {
    /**
     * Final variable for the winning sequence
     */
    public static final String WINNING_SEQUENCE = "****";
    /**
     * Final variable for the sequence length
     */
    private static final int SEQUENCE_LENGTH = 4;


    /** Getter method for sequence of the peg
     * @return sequence - the array list
     */
    public ArrayList<Peg> getSequence() {
        return sequence;
    }

    /** Variable to store the sequence of the peg */
    private ArrayList<Peg> sequence;

    /**
     * Constructor to create a peg sequence from a string
     * @param input - Sequence to be created
     */
    public PegSequence(String input) {
        sequence = new ArrayList<>(SEQUENCE_LENGTH);
        sequence.add(Peg.getPegfromString(String.valueOf(input.charAt(0))));
        sequence.add(Peg.getPegfromString(String.valueOf(input.charAt(1))));
        sequence.add(Peg.getPegfromString(String.valueOf(input.charAt(2))));
        sequence.add(Peg.getPegfromString(String.valueOf(input.charAt(3))));
    }

    /**
     * Constructor to create an empty peg sequence array
     */
    public PegSequence() {
        sequence = new ArrayList<>(SEQUENCE_LENGTH);
    }

    /**
     * Add the pegs to the string
     * @param p - Pegs to be added
     */
    public void addPeg (Peg p) {
        sequence.add(p);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PegSequence that = (PegSequence) o;
        return Objects.equals(sequence, that.sequence);
    }

    /**
     * Overriden toString method to print the pegs in readable format
     * @return string - Peg string using the values indicated
     */
    @Override
    public String toString() {
        String sSequence = "";
        for (int i = 0; i < SEQUENCE_LENGTH; i++) {
            sSequence += this.sequence.get(i).getValueofPeg();
        }
        return sSequence;
    }

    /**
     * Return the number of pegs per sequence
     * @return int - the length of a peg sequence
     */
    public static int getSequenceLength() {
        return SEQUENCE_LENGTH;
    }
}

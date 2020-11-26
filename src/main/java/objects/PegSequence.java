package objects;
/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Cuong Nguyen
            Lily Parker
            Minh Anh Phan
            Anurag Vadiya
 * Section: 8:50 AM
 * Date: 11/8/2020
 * Time: 9:40 AM
 *
 * Project: csci205FinalProject
 * Package: PACKAGE_NAME
 * Class: objects.PegSequence
 *
 * Description: Representing an object of 4 pegs sequentially such as the feedback or the user guess
 *
 * ****************************************
 */

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represent peg sequences (code and response)
 */
public class PegSequence {

    /**
     * Final variable for the sequence length
     */
    private static int SEQUENCE_LENGTH;

    /**
     * Variable for the winning sequence
     */
    public static String WINNING_SEQUENCE;

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
        for (int i = 0; i < SEQUENCE_LENGTH; i++) {
            sequence.add(Peg.getPegfromString(String.valueOf(input.charAt(i))));
        }
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

    /**
     * Override equals method
     * @param o - object comparing to
     * @return - true if equals, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PegSequence that = (PegSequence) o;
        return Objects.equals(sequence, that.sequence);
    }

    /**
     * Overriden toString method to print the pegs in readable format
     * @return string - objects.Peg string using the values indicated
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

    /**
     * Setter for sequence length
     * @param length - int length of sequences
     */
    public static void setSequenceLength(int length) {
        SEQUENCE_LENGTH = length;
    }

    /**
     * create for winning sequence
     */
    public static void setUpWinningSequence() {
        String star = "*";
        WINNING_SEQUENCE = star.repeat(SEQUENCE_LENGTH);
    }
}

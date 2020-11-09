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
    private static final int SEQUENCE_LENGTH = 4;

    private ArrayList<Peg> sequence = new ArrayList<>(SEQUENCE_LENGTH);


    public PegSequence(String input) {

        sequence.add(Peg.getPegfromString(String.valueOf(input.charAt(0))));
        sequence.add(Peg.getPegfromString(String.valueOf(input.charAt(1))));
        sequence.add(Peg.getPegfromString(String.valueOf(input.charAt(2))));
        sequence.add(Peg.getPegfromString(String.valueOf(input.charAt(3))));

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PegSequence that = (PegSequence) o;
        return Objects.equals(sequence, that.sequence);
    }

    @Override
    public String toString() {
        String sSequence = "";
        for (int i = 0; i < SEQUENCE_LENGTH; i++) {
            sSequence += this.sequence.get(i).getValueofPeg();
        }
        return sSequence;
    }

    public static int getSEQUENCE_LENGTH() {
        return SEQUENCE_LENGTH;
    }
}

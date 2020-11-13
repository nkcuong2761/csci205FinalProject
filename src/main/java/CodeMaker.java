/* *****************************************
* CSCI205 - Software Engineering and Design
* Fall 2020
* Instructor: Prof. Brian King
*
* FINAL PROJECT
* Name: Team01
* Member:   Cuong Nguyen
            Lily Parker
            Minh Anh Phan
            Anurag Vadiya

* Date: 11/4/2020 - GMT +7
* Time: 9:10 PM - GMT +7
*
* Project: csci205FinalProject
* Package: PACKAGE_NAME
* Class: CodeMaker
*
* Description: Mastermind Final Project for CSCI 205
*
* ****************************************
*/

import java.util.Random;

public class CodeMaker {
    /**
     * Variable to represent the secret code
     */
    private static PegSequence secretCode;

    /**
     * Variable to do the comparison: resulting code in string
     */
    private static StringBuilder responseCodeString;

    /**
     * Variable to do the comparison: secret code in string
     */
    private static StringBuilder secreteCodeString;

    /**
     * Variable to do the comparison: user input code in string
     */
    private static StringBuilder userInputCodeString;

    /**
     * Variable to do the comparison: char array of the user input code
     */
    private static char[] userInputChar;


    /**
     * Public constructor to generate secret code
     */
    public CodeMaker() {
        generateCode();
    }

    /**
     * Method to generate random secret code
     */
    protected void generateCode() {
        // Reinitialize secret code
        secretCode = null;
        Random rand = new Random(); // instance of random class
        // Generate string
        int length = PegSequence.getSequenceLength();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(rand.nextInt(length) + 1);
        }
        // Create the secretCode
        secretCode = new PegSequence(code.toString());
        System.out.println("Secret code is: " + secretCode.toString());
    }

    /**
     * Method to compare the input string with the actual answer
     *
     * @param userCode - user guess
     * @return feedback - the comparison as a String, * will output first
     * * -> correct both location and number
     * + -> correct number, but not location
     */
    public static PegSequence compare(PegSequence userCode) {
        // Create new StringBuffer for the output
        responseCodeString = new StringBuilder("----");
        secreteCodeString = new StringBuilder(secretCode.toString());
        userInputCodeString = new StringBuilder(userCode.toString());
        userInputChar = userCode.toString().toCharArray();
        int n = 0;

        // Check for correct number and correct order
        for (int i = 0; i < secretCode.getSequenceLength(); i++) {
            // Compare the secret code to the input
            if (secreteCodeString.charAt(i) == userInputCodeString.charAt(i)) {
                responseCodeString = responseCodeString.replace(n, n + 1, "*");
                n++;
                secreteCodeString.setCharAt(i, '0');// Reassign the index to avoid repetition
                userInputCodeString.setCharAt(i, '0');
            }
        }

        // Check for correct number but wrong order
        for (int i = 0; i < secretCode.getSequenceLength(); i++) {
            // Skip the one that pass the * test
            if (userInputCodeString.charAt(i) == '0') {
                continue;
            }
            // Check the remaining for correct number
            if (secreteCodeString.indexOf(String.valueOf(userInputCodeString.charAt(i))) >= 0) {
                responseCodeString = responseCodeString.replace(n, n + 1, "+");
                n++;
                secreteCodeString.setCharAt(secreteCodeString.indexOf(String.valueOf(userInputChar[i])), '0');// Reassign the index to avoid repetition
            }
        }
        // Return the Code
        return new PegSequence(responseCodeString.toString());
    }

    /**
     * setter that is only used for JUnitTest
     *
     * @param code - our predefined secret code
     */
    public void setSecretCode(PegSequence code) {
        secretCode = code;
    }

    /**
     * getter that is only used for JUnitTest
     *
     * @return secretCode - our secret code
     */
    public PegSequence getSecretCode() {
        return secretCode;
    }
}
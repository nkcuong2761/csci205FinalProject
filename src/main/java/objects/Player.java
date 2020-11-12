package objects;/* *****************************************
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
            
* Date: 11/5/2020 - GMT +7
* Time: 9:18 PM - GMT +7
*
* Project: csci205FinalProject
* Package: PACKAGE_NAME
* Class: objects.Player
*
* Description: The player for the game
*
* ****************************************
*/

public class Player {

    /**
     * String to represent the player's name
     */
    private static String playerName;

    /**
     * Method to set the string name
     */
    public void setPlayerName(String name){ playerName = name; }

    /**
     * Getter for player's name
     * @return
     */
    public static String getPlayerName() {
        return playerName;
    }
}
package objects;

import javafx.scene.paint.Color;

public enum Peg {
    // colors to represent the codes
    THE_FIRST_PEG(Color.web("EF476F"), "1"),
    THE_SECOND_PEG(Color.web("FFD166"), "2"),
    THE_THIRD_PEG(Color.web("06D6A0"), "3"),
    THE_FOURTH_PEG(Color.web("023E8A"), "4"),
    THE_FIFTH_PEG(Color.web("A52A2A"), "5"),
    THE_SIXTH_PEG(Color.web("80080"), "6"),

    // colors to represent the responses
    THE_BLACK_PEG(Color.web("000000", 0.85), "*"),
    THE_BLANK_PEG(Color.WHITE, "-"),
    THE_WHITE_PEG(Color.web("D9D9D9"), "+");

    private Color color;
    private String value;

    /**
     * Constructor for the peg
     * @param color - Color of the peg
     * @param value - Value of the peg
     */
    Peg(Color color, String value) {
        this.color = color;
        this.value = value;
    }

    /**
     * Return the corresponding EnumPeg from String
     * @param string - the value of the objects.Peg
     * @return peg - Corresponding peg from string
     */
    public static Peg getPegfromString(String string) {
        // Return the corresponding peg
        for (Peg peg : Peg.values()){
            if (peg.value.equals(string)){
                return peg;
            }
        }
        // Return null if cannot find the peg
        return null;
    }

    /**
     * Return the peg based on the input color
     * @param c - The color of the peg
     * @return the corresponding peg or null if not found
     */
    public static Peg getPegForColor(Color c) {
        // find the corresponding peg
        for (Peg peg : Peg.values()){
            if (peg.color.equals(c)){
                return peg;
            }
        }
        // Return null if cannot find the peg
        return null;
    }

    /**
     * Return the value string of the peg
     * @return value - String to represent the value of the objects.Peg
     */
    public String getValueofPeg(){
        return this.value;
    }

    /**
     * Update the value of the peg
     * @param color - New color of the peg
     */
    public void updatePegColor(Color color){
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    @Override
    public String toString() {
        return "objects.Peg{" +
                "color=" + color +
                ", value='" + value + '\'' +
                '}';
    }
}

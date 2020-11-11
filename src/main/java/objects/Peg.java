package objects;

import javafx.scene.paint.Color;

public enum Peg {
    // colors to represent the codes
    THE_RED_PEG(Color.web("EF476F"), "1"),
    THE_YELLOW_PEG(Color.web("FFD166"), "2"),
    THE_GREEN_PEG(Color.web("06D6A0"), "3"),
    THE_BLUE_PEG(Color.web("023E8A"), "4"),
    THE_BROWN_PEG(Color.BROWN, "5"),
    THE_PURPLE_PEG(Color.PURPLE, "6"),

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

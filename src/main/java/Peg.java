import javafx.scene.paint.Color;

public enum Peg {
    // colors to represent the codes
    THE_BLUE_PEG(Color.BLUE, "1"),
    THE_GREEN_PEG(Color.GREEN, "2"),
    THE_BROWN_PEG(Color.BROWN, "3"),
    THE_PURPLE_PEG(Color.PURPLE, "4"),
    THE_RED_PEG(Color.RED, "5"),
    THE_YELLOW_PEG(Color.YELLOW, "6"),

    // colors to represent the responses
    THE_BLACK_PEG(Color.BLACK, "*"),
    THE_BLANK_PEG(Color.BEIGE, "-"),
    THE_WHITE_PEG(Color.WHITE, "+");

    private Color color;
    private String value;

    Peg(Color color, String value) {
        this.color = color;
        this.value = value;
    }

    /**
     * Return the corresponding EnumPeg from String
     * @param string - the value of the Peg
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
     * Return the value string of the peg
     * @return value - String to represent the value of the Peg
     */
    protected String getValueofPeg(){
        return this.value;
    }

    @Override
    public String toString() {
        return "Peg{" +
                "color=" + color +
                ", value='" + value + '\'' +
                '}';
    }
}

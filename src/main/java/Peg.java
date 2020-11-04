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
    THE_BLANK_PEG(Color.BEIGE, "+"),
    THE_WHITE_PEG(Color.WHITE, "+");

    private Color color;
    private String value;


    Peg(Color color, String value) {
        this.color = color;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Peg{" +
                "color=" + color +
                ", value='" + value + '\'' +
                '}';
    }
}

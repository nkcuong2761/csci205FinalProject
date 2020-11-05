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
    THE_BLANK_PEG(Color.BEIGE, "+"),
    THE_WHITE_PEG(Color.web("D9D9D9"), "+");

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

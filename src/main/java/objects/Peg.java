package objects;

/**
 * Enum to store individual pegs that make up the code
 */
public enum Peg {
    // values to represent the pegs
    THE_FIRST_PEG("1"),
    THE_SECOND_PEG("2"),
    THE_THIRD_PEG("3"),
    THE_FOURTH_PEG("4"),
    THE_FIFTH_PEG( "5"),
    THE_SIXTH_PEG( "6"),
    // colors to represent the responses
    THE_BLACK_PEG( "*"),
    THE_BLANK_PEG( "-"),
    THE_WHITE_PEG( "+");

    /**
     * The value of the peg
     */
    private String value;

    /**
     * Constructor for the peg
     * @param value - Value of the peg
     */
    Peg(String value) {
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
     * Return the value string of the peg
     * @return value - String to represent the value of the objects.Peg
     */
    public String getValueofPeg(){
        return this.value;
    }

}

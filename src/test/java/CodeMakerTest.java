import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CodeMakerTest {
    CodeMaker test;

    @BeforeEach
    void setUp() {
        test = new CodeMaker();
    }
    /**
     * Comprehensive testing for the compare method, arguably the most critical method in the program
     */
    @Test
    public void compareGuessTest() {
        test.setSecretCode(new Code("4662"));
        // 4 right number
        assertEquals(new Code("++++"), test.compare(new Code("6426")));
        // 3 right number
        assertEquals(new Code("+++-"), test.compare(new Code("6241")));
        // 2 right number
        assertEquals(new Code("++--"), test.compare(new Code("1234")));
        // Nothing right
        assertEquals(new Code("----"), test.compare(new Code("1135")));
        // 4 right number and position
        assertEquals(new Code("****"), test.compare(new Code("4662")));
        // 3 right number and position
        assertEquals(new Code("***-"), test.compare(new Code("4162")));
        // 2 right number and position and 2 right number only
        assertEquals(new Code("**++"), test.compare(new Code("2664")));
        // 2 right number and position only
        assertEquals(new Code("**--"), test.compare(new Code("1652")));
        // 2 right number and position with 1 right number only
        assertEquals(new Code("**+-"), test.compare(new Code("4625")));
        // 1 right number and position with 3 right number only
        assertEquals(new Code("*+++"), test.compare(new Code("6624")));
        // 1 right number and position with 2 right number only
        assertEquals(new Code("*++-"), test.compare(new Code("2641")));
        // 1 right number and position with 1 right number only
        assertEquals(new Code("*+--"), test.compare(new Code("1142")));
    }

    /**
     * Test generate secret code method, make sure it returns a method within the range
     */
    @Test
    public void generateCodeTest(){
        // Check if secret code is non null
        assertNotEquals(null, test.getSecretCode());
        // Check the code is correct representation
        String regex = "^[1-6][1-6][1-6][1-6]$";
        assertEquals(true, test.getSecretCode().codeToString().matches(regex));

    }
}
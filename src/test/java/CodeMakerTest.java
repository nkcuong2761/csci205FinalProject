import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CodeMakerTest {
    /**
     * Comprehensive testing for the compare method, arguably the most critical method in the program
     */
    @Test
    public void compareGuessTest() {
        CodeMaker test = new CodeMaker();
        test.setSecretCode(new Code("4662"));
        // 4 right number
        assertEquals(new Code("++++"), CodeMaker.compare(new Code("6426")));
        // 3 right number
        assertEquals(new Code("+++-"), CodeMaker.compare(new Code("6241")));
        // 2 right number
        assertEquals(new Code("++--"), CodeMaker.compare(new Code("1234")));
        // Nothing right
        assertEquals(new Code("----"), CodeMaker.compare(new Code("1135")));
        // 4 right number and position
        assertEquals(new Code("****"), CodeMaker.compare(new Code("4662")));
        // 3 right number and position
        assertEquals(new Code("***-"), CodeMaker.compare(new Code("4162")));
        // 2 right number and position and 2 right number only
        assertEquals(new Code("**++"), CodeMaker.compare(new Code("2664")));
        // 2 right number and position only
        assertEquals(new Code("**--"), CodeMaker.compare(new Code("1652")));
        // 2 right number and position with 1 right number only
        assertEquals(new Code("**+-"), CodeMaker.compare(new Code("4625")));
        // 1 right number and position with 3 right number only
        assertEquals(new Code("*+++"), CodeMaker.compare(new Code("6624")));
        // 1 right number and position with 2 right number only
        assertEquals(new Code("*++-"), CodeMaker.compare(new Code("2641")));
        // 1 right number and position with 1 right number only
        assertEquals(new Code("*+--"), CodeMaker.compare(new Code("1142")));
    }

}
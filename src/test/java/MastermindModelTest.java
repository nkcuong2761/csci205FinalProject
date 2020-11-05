import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MastermindModelTest {

    @BeforeEach
    void setUp() {
        MastermindModel model = new MastermindModel();
    }
    /**
     * Test the restart method
     */
    @Test
    public void restartTest() {
        MastermindModel model = new MastermindModel();
        model.restartGame();
        // Check for the current guess
        assertEquals(0, model.getCurrGuess());
        assertEquals(null, model.getUserGuess());
    }
}
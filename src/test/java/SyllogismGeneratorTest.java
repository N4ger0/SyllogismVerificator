import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import univ.syllogismverificator.models.Syllogism;
import univ.syllogismverificator.models.SyllogismResult;
import univ.syllogismverificator.models.SyllogismsGenerator;
import univ.syllogismverificator.models.rules.AaRule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SyllogismGeneratorTest {
    private SyllogismsGenerator syllogismsGenerator;

    @BeforeEach
    void setUp() {
        syllogismsGenerator = new SyllogismsGenerator();
    }

    @Test
    void TestNumberOfSyllogismsAndNumberOfValid() {
        assertEquals(256, syllogismsGenerator.getSyllogisms().size());
    }

    @Test
    void testValidNumberSolverIntegration() {
        int count = 0;
        for (Pair<Syllogism, SyllogismResult> s : syllogismsGenerator.getSyllogisms())
        {
            if (s.getValue().isValid())
                count++;
        }

        assertEquals(15, count);
    }
}

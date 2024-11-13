import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Proposition;
import univ.syllogismverificator.models.Solver;
import univ.syllogismverificator.models.SyllogismResult;
import univ.syllogismverificator.models.rules.Ruleuu;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SolverTest {
    @BeforeEach
    public void setUp() {}

    @Test
    public void testSolveInterestingValid() {
        Solver solver = new Solver();
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("a", "b", true, false),
                new Proposition("b", "c", true, false),
                new Proposition("c", "a", false, false)
        ));
        assertFalse(solver.solve(polysyllogism, true, true, true, true, true, true, true, true, true).isValid());
        assertEquals(solver.solve(polysyllogism, true, true, true, true, true, true, true, true, true).toString(), solver.solve(polysyllogism, true, true, true, true, true, true, true, true, false).toString());
    }

    @Test
    public void testIntegrationRule() {
        Solver solver = new Solver();
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("U", "U", true, false),
                new Proposition("U", "U", true, false),
                new Proposition("U", "U", false, false)
        ));
        SyllogismResult s1 = solver.solve(polysyllogism, false, false, false, false, false, false, false, true, false);

        assertFalse(s1.isValid());

        polysyllogism = new Polysyllogism(List.of(
                new Proposition("U", "U", false, false),
                new Proposition("U", "U", false, false),
                new Proposition("U", "U", false, false)
        ));
        SyllogismResult s2 = solver.solve(polysyllogism, false, false, false, false, false, false, false, true, false);
    }
}

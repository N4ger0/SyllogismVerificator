package univ.syllogismverificator.models.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Proposition;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NRuleTest {

    private NRule nRule;

    @BeforeEach
    void setUp() {
        nRule = new NRule();
    }

    @Test
    void evaluateNegativeNegative() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("a", "b", true, true),
                new Proposition("b", "c", false, false),
                new Proposition("a", "c", true, false)
        ));
        assertTrue(nRule.evaluate(polysyllogism).isValid());
    }

    @Test
    void evaluatePositiveNegative() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("a", "b", true, false),
                new Proposition("b", "c", false, true),
                new Proposition("a", "c", true, false)
        ));
        assertTrue(nRule.evaluate(polysyllogism).isValid());
    }

    @Test
    void evaluatePositivePositive() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("a", "b", true, false),
                new Proposition("b", "c", false, true),
                new Proposition("a", "c", true, true)
        ));
        assertFalse(nRule.evaluate(polysyllogism).isValid());
    }

    @Test
    void evaluateNegativePositive() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("a", "b", true, false),
                new Proposition("b", "c", false, false),
                new Proposition("a", "c", true, false)
        ));
        assertTrue(nRule.evaluate(polysyllogism).isValid());
    }
}
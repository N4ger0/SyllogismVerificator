package univ.syllogismverificator.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import univ.syllogismverificator.models.rules.MediumTermRule;
import univ.syllogismverificator.models.rules.RuleResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MediumTermRuleTest {

    private MediumTermRule mediumTermRule;

    @BeforeEach
    void setUp() {
        mediumTermRule = new MediumTermRule();
    }

    @Test
    void evaluateValid() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("p", "m", true, true),
                new Proposition("m", "s", true, true),
                new Proposition("p", "s", true, true)
        ));

        RuleResult result = mediumTermRule.evaluate(polysyllogism);

        assertTrue(result.isValid());
        assertEquals("", result.toString());
    }

    @Test
    void evaluateInvalid() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("p", "m", false, true),
                new Proposition("m", "s", false, true),
                new Proposition("p", "s", true, true)
        ));

        RuleResult result = mediumTermRule.evaluate(polysyllogism);

        assertFalse(result.isValid());
        assertEquals("", result.toString());
    }
}
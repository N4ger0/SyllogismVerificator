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
                new Proposition("predicate", "subject", true, false),
                new Proposition("predicate", "subject", false, true),
                new Proposition("predicate", "subject", false, true)
        ));

        RuleResult result = mediumTermRule.evaluate(polysyllogism);

        assertTrue(result.isValid());
        assertEquals("", result.toString());
    }

    @Test
    void evaluateInvalid() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("predicate", "subject", false, false),
                new Proposition("predicate", "subject", false, true),
                new Proposition("predicate", "subject", false, false)
        ));

        RuleResult result = mediumTermRule.evaluate(polysyllogism);

        assertFalse(result.isValid());
        assertNotEquals("", result.toString());
    }
}
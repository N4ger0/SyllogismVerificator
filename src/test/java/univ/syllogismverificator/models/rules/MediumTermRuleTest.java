package univ.syllogismverificator.models.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Proposition;
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
    // TEST FIGURE 1
    @Test
    void evaluateValid1() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("p", "m", true, true),
                new Proposition("m", "s", true, true),
                new Proposition("p", "s", true, true)
        ));

        RuleResult result = mediumTermRule.evaluate(polysyllogism);

        assertTrue(result.isValid());
        //assertEquals("", result.toString());
    }

    @Test
    void evaluateInvalid1() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("p", "m", false, true),
                new Proposition("m", "s", false, true),
                new Proposition("p", "s", true, true)
        ));

        RuleResult result = mediumTermRule.evaluate(polysyllogism);

        assertFalse(result.isValid());
        //assertEquals("", result.toString());
    }

    // TEST FIGURE 2
    @Test
    void evaluateValid2() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("m", "p", true, false),
                new Proposition("m", "s", true, true),
                new Proposition("p", "s", true, true)
        ));

        RuleResult result = mediumTermRule.evaluate(polysyllogism);

        assertTrue(result.isValid());
        //assertEquals("", result.toString());
    }

    @Test
    void evaluateInvalid2() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("m", "p", true, true),
                new Proposition("m", "s", false, true),
                new Proposition("p", "s", true, false)
        ));

        RuleResult result = mediumTermRule.evaluate(polysyllogism);

        assertFalse(result.isValid());
        //assertEquals("", result.toString());
    }
    // TEST FIGURE 2
    @Test
    void evaluateValid3() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("p", "m", false, true),
                new Proposition("s", "m", true, false),
                new Proposition("p", "s", true, true)
        ));

        RuleResult result = mediumTermRule.evaluate(polysyllogism);

        assertTrue(result.isValid());
        //assertEquals("", result.toString());
    }

    @Test
    void evaluateInvalid3() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("p", "m", false, false),
                new Proposition("s", "m", false, false),
                new Proposition("p", "s", false, false)
        ));

        RuleResult result = mediumTermRule.evaluate(polysyllogism);

        assertFalse(result.isValid());
        //assertEquals("", result.toString());
    }

    // TEST FIGURE 2
    @Test
    void evaluateValid4() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("m", "p", false, true),
                new Proposition("s", "m", true, true),
                new Proposition("p", "s", false, false)
        ));

        RuleResult result = mediumTermRule.evaluate(polysyllogism);

        assertTrue(result.isValid());
        //assertEquals("", result.toString());
    }

    @Test
    void evaluateInvalid4() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("m", "p", false, true),
                new Proposition("s", "m", false, false),
                new Proposition("p", "s", true, true)
        ));

        RuleResult result = mediumTermRule.evaluate(polysyllogism);

        assertFalse(result.isValid());
        //assertEquals("", result.toString());
    }
}
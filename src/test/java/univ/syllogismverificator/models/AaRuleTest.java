package univ.syllogismverificator.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import univ.syllogismverificator.models.rules.AaRule;
import univ.syllogismverificator.models.rules.NnRule;
import univ.syllogismverificator.models.rules.RuleResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AaRuleTest {

    private AaRule aaRule;

    @BeforeEach
    void setUp() {
        aaRule = new AaRule();
    }

    @Test
    void evaluateValidAllAffirmative() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("a", "b", true, true),
                new Proposition("b", "c", false, true),
                new Proposition("a", "c", true, true)
        ));

        RuleResult result = polysyllogism.accept(aaRule);

        assertTrue(result.isValid());

        // TODO: Error messages
        // assertEquals("", result.toString());
    }

    @Test
    void evaluateValidOneNegative() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("a", "b", true, true),
                new Proposition("b", "c", false, false),
                new Proposition("a", "c", true, true)
        ));

        RuleResult result = polysyllogism.accept(aaRule);

        assertTrue(result.isValid());

        // TODO: Error messages
        // assertEquals("", result.toString());
    }

    @Test
    void evaluateInvalid() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("a", "b", true, true),
                new Proposition("b", "c", false, true),
                new Proposition("a", "c", true, false)
        ));

        RuleResult result = polysyllogism.accept(aaRule);

        assertFalse(result.isValid());
        assertNotEquals("", result.toString());
    }

    @Test
    void evaluatePolysyllogismValid() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("a", "b", true, true),
                new Proposition("b", "c", false, true),
                new Proposition("c", "d", false, true),
                new Proposition("d", "e", false, true),
                new Proposition("e", "f", false, true),
                new Proposition("a", "f", true, true)
        ));

        RuleResult result = polysyllogism.accept(aaRule);

        assertTrue(result.isValid());

        // TODO: Error messages
        // assertEquals("", result.toString());
    }

    @Test
    void evaluatePolysyllogismInvalid() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("a", "b", true, true),
                new Proposition("b", "c", false, true),
                new Proposition("c", "d", false, true),
                new Proposition("d", "e", false, true),
                new Proposition("e", "f", false, true),
                new Proposition("a", "f", true, false)
        ));

        RuleResult result = polysyllogism.accept(aaRule);

        assertFalse(result.isValid());

        // TODO: Error messages
        // assertNotEquals("", result.toString());
    }
}
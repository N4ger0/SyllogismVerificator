package univ.syllogismverificator.models.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import univ.syllogismverificator.Traductor;
import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Proposition;
import univ.syllogismverificator.models.rules.NnRule;
import univ.syllogismverificator.models.rules.RuleResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NnRuleTest {

    private NnRule nnRule;

    @BeforeEach
    void setUp() {
        nnRule = new NnRule();
    }

    @Test
    void evaluateValid() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("a", "b", true, true),
                new Proposition("b", "c", false, false),
                new Proposition("a", "c", true, false)
        ));

        RuleResult result = polysyllogism.accept(nnRule);

        assertTrue(result.isValid());

        // TODO: Error messages
        // assertEquals("", result.toString());
    }

    @Test
    void evaluateInvalid() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("a", "b", true, false),
                new Proposition("b", "c", false, false),
                new Proposition("a", "c", true, false)
        ));

        RuleResult result = polysyllogism.accept(nnRule);

        assertFalse(result.isValid());
        assertEquals(Traductor.get("rnn_invalid"), result.toString());
    }

    @Test
    void evaluatePolysyllogismValid() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("a", "b", true, true),
                new Proposition("b", "c", false, false),
                new Proposition("c", "d", false, false),
                new Proposition("d", "e", false, true),
                new Proposition("e", "f", false, false),
                new Proposition("a", "f", true, false)
        ));

        RuleResult result = polysyllogism.accept(nnRule);

        assertTrue(result.isValid());

        // TODO: Error messages
        // assertEquals("", result.toString());
    }

    @Test
    void evaluatePolysyllogismInvalid() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("a", "b", true, false),
                new Proposition("b", "c", false, false),
                new Proposition("c", "d", false, false),
                new Proposition("d", "e", false, false),
                new Proposition("e", "f", false, false),
                new Proposition("a", "f", true, true)
        ));

        RuleResult result = polysyllogism.accept(nnRule);
        assertFalse(result.isValid());
        assertEquals(Traductor.get("rnn_invalid"), result.toString());
    }
}
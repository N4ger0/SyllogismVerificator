package univ.syllogismverificator.models.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import univ.syllogismverificator.Traductor;
import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Proposition;
import univ.syllogismverificator.models.rules.AaRule;
import univ.syllogismverificator.models.rules.NnRule;
import univ.syllogismverificator.models.rules.PRule;
import univ.syllogismverificator.models.rules.RuleResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PRuleTest {

    private PRule pRule;

    @BeforeEach
    void setUp() {
        pRule = new PRule();
    }

    @Test
    void evaluateValidOneParticular() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("a", "b", true, true),
                new Proposition("b", "c", false, true),
                new Proposition("a", "c", false, true)
        ));

        RuleResult result = polysyllogism.accept(pRule);

        assertTrue(result.isValid());

        // TODO: Error messages
        // assertEquals("", result.toString());
    }

    @Test
    void evaluateValidNoParticular() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("a", "b", true, true),
                new Proposition("b", "c", true, false),
                new Proposition("a", "c", false, true)
        ));

        RuleResult result = polysyllogism.accept(pRule);

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

        RuleResult result = polysyllogism.accept(pRule);

        assertFalse(result.isValid());
        assertEquals(Traductor.get("rp_invalid"), result.toString());
    }

    @Test
    void evaluatePolysyllogismValid() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("a", "b", true, true),
                new Proposition("b", "c", false, true),
                new Proposition("c", "d", true, true),
                new Proposition("d", "e", true, true),
                new Proposition("e", "f", false, true),
                new Proposition("a", "f", false, true)
        ));

        RuleResult result = polysyllogism.accept(pRule);

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

        RuleResult result = polysyllogism.accept(pRule);

        assertFalse(result.isValid());
        assertEquals(Traductor.get("rp_invalid"), result.toString());
    }
}
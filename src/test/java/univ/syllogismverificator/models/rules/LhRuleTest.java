package univ.syllogismverificator.models.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Proposition;
import univ.syllogismverificator.models.Syllogism;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AaRuleTest {

    private LhRule lhRule;

    @BeforeEach
    void setUp() {
        lhRule = new LhRule();
    }

    @Test
    void evaluateValidAllUniversal() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("a", "b", true, false),
                new Proposition("b", "c", true, false),
                new Proposition("a", "c", true, false)
        ));

        RuleResult result = polysyllogism.accept(lhRule);

        assertTrue(result.isValid());

        // TODO: Error messages
        // assertEquals("", result.toString());
    }

    @Test
    void evaluateValidAllUniversalFigureInversion() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("b", "a", true, false),
                new Proposition("b", "c", true, false),
                new Proposition("a", "c", true, false)
        ));

        RuleResult result = polysyllogism.accept(lhRule);

        assertTrue(result.isValid());

        // TODO: Error messages
        // assertEquals("", result.toString());
    }

    @Test
    void evaluateValidParticularConclusion() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("a", "b", false, true),
                new Proposition("b", "c", false, true),
                new Proposition("a", "c", false, true)
        ));

        RuleResult result = polysyllogism.accept(lhRule);

        assertTrue(result.isValid());

        // TODO: Error messages
        // assertEquals("", result.toString());
    }

    @Test
    void evaluateValidParticularConclusionUniversalPremises() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("a", "b", true, false),
                new Proposition("b", "c", true, false),
                new Proposition("a", "c", false, true)
        ));

        RuleResult result = polysyllogism.accept(lhRule);

        assertTrue(result.isValid());

        // TODO: Error messages
        // assertEquals("", result.toString());
    }

    @Test
    void evaluateInvalidFirstParticular() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("a", "b", false, true),
                new Proposition("b", "c", true, false),
                new Proposition("a", "c", true, false)
        ));

        RuleResult result = polysyllogism.accept(lhRule);

        assertFalse(result.isValid());

        // TODO: Error messages
        // assertEquals("", result.toString());
    }

    @Test
    void evaluateInvalidFirstParticularFigureInversion() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("b", "a", false, true),
                new Proposition("b", "c", true, false),
                new Proposition("a", "c", true, false)
        ));

        RuleResult result = polysyllogism.accept(lhRule);

        assertFalse(result.isValid());

        // TODO: Error messages
        // assertEquals("", result.toString());
    }

    @Test
    void evaluateInvalidLastParticular() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("a", "b", true, false),
                new Proposition("b", "c", false, true),
                new Proposition("a", "c", true, false)
        ));

        RuleResult result = polysyllogism.accept(lhRule);

        assertFalse(result.isValid());

        // TODO: Error messages
        // assertEquals("", result.toString());
    }

    @Test
    void evaluateInvalidLastParticularFigureInversion() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("a", "b", true, false),
                new Proposition("c", "b", false, true),
                new Proposition("a", "c", true, false)
        ));

        RuleResult result = polysyllogism.accept(lhRule);

        assertFalse(result.isValid());

        // TODO: Error messages
        // assertEquals("", result.toString());
    }

    @Test
    void evaluateInvalidAllParticular() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("a", "b", false, true),
                new Proposition("b", "c", false, true),
                new Proposition("a", "c", true, false)
        ));

        RuleResult result = polysyllogism.accept(lhRule);

        assertFalse(result.isValid());

        // TODO: Error messages
        // assertEquals("", result.toString());
    }

    @Test
    void testIeo() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("c", "b", false, true),
                new Proposition("b", "a", true, false),
                new Proposition("a", "c", false, false)
        ));

        RuleResult result = polysyllogism.accept(lhRule);

        assertFalse(result.isValid());
    }

    @Test
    void testOao() {
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("b", "c", false, false),
                new Proposition("b", "a", true, true),
                new Proposition("a", "c", false, false)
        ));

        RuleResult result = polysyllogism.accept(lhRule);

        assertFalse(result.isValid());
    }
}
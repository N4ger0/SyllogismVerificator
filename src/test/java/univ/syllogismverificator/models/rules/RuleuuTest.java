package univ.syllogismverificator.models.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Proposition;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RuleuuTest {

    private Ruleuu rule;

    @BeforeEach
    void setUp() {
        rule = new Ruleuu();
    }

    @Test
    public void evaluateUniversalUniversal(){
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("U", "U", true, false),
                new Proposition("U", "U", true, false),
                new Proposition("U", "U", true, false)
        ));
        assertFalse(rule.evaluate(polysyllogism).isValid());
    }

    @Test
    public void evaluateUniversalParticular(){
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("U", "U", true, false),
                new Proposition("U", "U", true, false),
                new Proposition("U", "U", false, false)
        ));
        assertTrue(rule.evaluate(polysyllogism).isValid());
    }

    @Test
    public void evaluateParticularUniversal(){
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("U", "U", false, false),
                new Proposition("U", "U", false, false),
                new Proposition("U", "U", true, false)
        ));
        assertTrue(rule.evaluate(polysyllogism).isValid());
    }

    @Test
    public void evaluateParticularParticular(){
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("U", "U", false, false),
                new Proposition("U", "U", false, false),
                new Proposition("U", "U", false, false)
        ));
        assertTrue(rule.evaluate(polysyllogism).isValid());
    }
}
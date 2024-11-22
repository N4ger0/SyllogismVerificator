package univ.syllogismverificator.models.rules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import univ.syllogismverificator.Traductor;
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
    public void evaluateTrueAllUniversal(){
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("U", "U", true, false),
                new Proposition("U", "U", true, false),
                new Proposition("U", "U", true, false)
        ));

        assertTrue(rule.evaluate(polysyllogism).isValid());
        assertEquals(rule.evaluate(polysyllogism).toString(), Traductor.get("ruu_valid_universal_conclusion"));
    }

    @Test
    public void evaluateTrueAllParticularExceptConclusion(){
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("U", "U", false, false),
                new Proposition("U", "U", false, false),
                new Proposition("U", "U", true, false)
        ));
        assertTrue(rule.evaluate(polysyllogism).isValid());
        assertEquals(rule.evaluate(polysyllogism).toString(),Traductor.get("ruu_valid_universal_conclusion"));
    }

    @Test
    public void evaluateTrueAllParticular(){
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("U", "U", false, false),
                new Proposition("U", "U",
                        false, false),
                new Proposition("U", "U", false, false)
        ));
        assertTrue(rule.evaluate(polysyllogism).isValid());
        assertEquals(rule.evaluate(polysyllogism).toString(),Traductor.get("ruu_valid_particular_premises"));
    }

    @Test
    public void evaluateFalse(){
        Polysyllogism polysyllogism = new Polysyllogism(List.of(
                new Proposition("U", "U", true, false),
                new Proposition("U", "U", true, false),
                new Proposition("U", "U", false, false)
        ));

        assertFalse(rule.evaluate(polysyllogism).isValid());
        assertEquals(rule.evaluate(polysyllogism).toString(),Traductor.get("ruu_invalid"));
    }
}
package univ.syllogismverificator.models;

import java.util.ArrayList;
import java.util.List;

public class Polysyllogism {
    public final List<Proposition> propositions = new ArrayList<>();

    /**
     * Apply a rule to the polysyllogism
     * @param rule the rule to apply
     * @return the result of the rule
     */
    public RuleResult accept(Rule rule) {
        return rule.evaluate(this);
    }
}

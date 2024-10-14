package univ.syllogismverificator.models;

import java.util.ArrayList;
import java.util.List;

public class Polysyllogism {
    private final List<Proposition> propositions;

    public Polysyllogism(List<Proposition> propositions){
        this.propositions = propositions;
    }

    /**
     * Apply a rule to the polysyllogism
     * @param rule the rule to apply
     * @return the result of the rule
     */
    public RuleResult accept(Rule rule) {
        return rule.evaluate(this);
    }
}

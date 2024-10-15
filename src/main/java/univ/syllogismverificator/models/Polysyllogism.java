package univ.syllogismverificator.models;

import univ.syllogismverificator.models.rules.Rule;
import univ.syllogismverificator.models.rules.RuleResult;

import java.util.List;
import java.util.stream.Stream;

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

    public Stream<Proposition> stream(){
        return propositions.stream();
    }
}

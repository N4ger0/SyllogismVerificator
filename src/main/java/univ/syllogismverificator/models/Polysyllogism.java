package univ.syllogismverificator.models;

import univ.syllogismverificator.models.rules.Rule;
import univ.syllogismverificator.models.rules.RuleResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Polysyllogism {
    private final List<Proposition> propositions;
    private final Proposition conclusion;

    public Polysyllogism(List<Proposition> propositions){
        this.propositions = new ArrayList<>(propositions);
        this.conclusion = this.propositions.removeLast();
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

    public List<Proposition> getPropositions() {
        return new ArrayList<>(propositions);
    }

    public Proposition getConclusion() {
        return conclusion;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        propositions.forEach(p -> builder.append(p).append("\n"));
        return builder.append(conclusion).toString();
    }

    public boolean isMidTerm(String term) {
        return !term.equals(conclusion.predicate) && !term.equals(conclusion.subject);
    }
}

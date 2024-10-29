package univ.syllogismverificator.models;

import univ.syllogismverificator.models.rules.Rule;
import univ.syllogismverificator.models.rules.RuleResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * A class that represents a polysyllogism.
 */
public class Polysyllogism {
    private final List<Proposition> propositions;
    private final Proposition conclusion;

    /**
     * Creates a polysyllogism from a list of propsitions.
     * The last element of the list will be considered as the conclusion.
     *
     * @param propositions The list of propositions
     */
    public Polysyllogism(List<Proposition> propositions){
        this.propositions = new ArrayList<>(propositions);
        this.conclusion = this.propositions.removeLast();
    }

    /**
     * Apply a rule to the polysyllogism
     *
     * @param rule the rule to apply
     * @return the result of the rule
     */
    public RuleResult accept(Rule rule) {
        return rule.evaluate(this);
    }

    /**
     * Returns a stream that allows to iterate on the propositions.
     * @return a stream created from the propositions.
     */
    public Stream<Proposition> stream(){
        return propositions.stream();
    }

    /**
     * Returns a list that contains the propositions.
     *
     * @return a new list that contains the polysyllogim's propositions.
     */
    public List<Proposition> getPropositions() {
        return new ArrayList<>(propositions);
    }

    /**
     * Returns the conclusion of the polysyllogism.
     *
     * @return a Proposition that represents the conclusion.
     */
    public Proposition getConclusion() {
        return conclusion;
    }

    /**
     * Returns a string that represents the polysyllogism.
     *
     * @return the string.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        propositions.forEach(p -> builder.append(p).append("\n"));
        return builder.append(conclusion).toString();
    }

    /**
     * Computes whether a term is a mid term of the polysyllogism.
     * @param term the term that is to be checked.
     * @return The result of the computation.
     */
    public boolean isMidTerm(String term) {
        return !term.equals(conclusion.predicate) && !term.equals(conclusion.subject);
    }
}

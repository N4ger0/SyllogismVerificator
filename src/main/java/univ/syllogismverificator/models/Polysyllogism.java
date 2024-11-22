package univ.syllogismverificator.models;

import univ.syllogismverificator.models.rules.Rule;
import univ.syllogismverificator.models.rules.RuleResult;

import java.util.ArrayList;
import java.util.List;
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

    //Builder by copy to make the conclusion universal
    public Polysyllogism(Polysyllogism polysyllogism){
        this.propositions = new ArrayList<>(polysyllogism.propositions);
        this.conclusion = new Proposition(polysyllogism.conclusion.predicate, polysyllogism.conclusion.subject, true, polysyllogism.conclusion.quality);
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

    public String toStringConclusion() {
        return conclusion.toString();
    }

    public boolean isConclusionUniversal() {
        return conclusion.quantity;
    }

    /**
     * Computes whether a term is a mid term of the polysyllogism.
     * @param term the term that is to be checked.
     * @return The result of the computation.
     */
    public boolean isMidTerm(String term) {
        return !term.equals(conclusion.predicate) && !term.equals(conclusion.subject);
    }

    private void swapProps(int i, int j) {
        Proposition temp = propositions.get(i);
        propositions.set(i, propositions.get(j));
        propositions.set(j, temp);
    }

    /**
     * Sorts the polysyllogism's propositions list to make the polysyllogism solvable.
     * If the syllogism is invalid, the method will try to sort it as much as possible.
     * @return a boolean that states whether the sorting process has been successfully achieved or not
     */
    public boolean sort() {
        String currentTerm = conclusion.predicate;

        for (int i = 0; i < propositions.size(); i++) {
            int j = i;
            boolean isDone = false;
            while (j < propositions.size() && !isDone) {
                if (currentTerm.equals(propositions.get(j).predicate))
                {
                    swapProps(i, j);
                    currentTerm = propositions.get(i).subject;
                    isDone = true;

                } else if (currentTerm.equals(propositions.get(j).subject)) {
                    swapProps(i, j);
                    currentTerm = propositions.get(i).predicate;
                    isDone = true;
                }
                j++;
            }

            if (!isDone)
                return false;
        }

        System.out.println(currentTerm + " " + conclusion.subject);
        return (conclusion.subject.equals(currentTerm));
    }

    public boolean equals(Object o) {
        if (o.getClass() != this.getClass()) {
            return false;
        }

        Polysyllogism p = (Polysyllogism)o;
        boolean result = conclusion.equals(p.conclusion);

        for (int i = 0; i < propositions.size(); i++) {
            result = result && propositions.get(i).equals(p.propositions.get(i));
        }

        return result;
    }
}

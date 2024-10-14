package univ.syllogismverificator.models;

/**
 * Represent a syllogism rule
 */
public interface Rule {

    /**
     * Evaluate the rule on a polysyllogism
     * @param polysyllogism the polysyllogism to evaluate
     * @return the result of the rule
     */
    RuleResult evaluate(Polysyllogism polysyllogism);
}

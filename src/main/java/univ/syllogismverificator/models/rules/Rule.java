package univ.syllogismverificator.models.rules;

import univ.syllogismverificator.models.Polysyllogism;

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

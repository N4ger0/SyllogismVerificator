package univ.syllogismverificator.models.rules;

import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Proposition;

/**
 * Resolves the nn rule.
 */
public class NnRule implements Rule {
    @Override
    public RuleResult evaluate(Polysyllogism polysyllogism) {
        boolean arePremisesOnlyNegative = true;

        for (Proposition prop : polysyllogism.getPropositions()) {
            arePremisesOnlyNegative = arePremisesOnlyNegative && !prop.quality;
        }

        // TODO: custom message
        return new RuleResult(!arePremisesOnlyNegative, "Regle Nn : ");
    }
}

package univ.syllogismverificator.models.rules;

import univ.syllogismverificator.Traductor;
import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Proposition;

/**
 * Resolves the p rule.
 */
public class PRule implements Rule {
    @Override
    public RuleResult evaluate(Polysyllogism polysyllogism) {
        boolean isOnePremiseParticular = false;

        for (Proposition prop : polysyllogism.getPropositions()) {
            isOnePremiseParticular = isOnePremiseParticular || !prop.quantity;
        }

        if (isOnePremiseParticular) {
            boolean isConclusionParticular = !polysyllogism.getConclusion().quantity;

            if (isConclusionParticular) {
                return new RuleResult(true, Traductor.get("rp_valid_particular_conclusion"));
            } else {
                return new RuleResult(false, Traductor.get("rp_invalid"));
            }
        } else {
            return new RuleResult(true, Traductor.get("rp_valid_no_particular_premise"));
        }
    }
}

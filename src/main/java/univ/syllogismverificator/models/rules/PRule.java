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

        // TODO: custom message
        if (isOnePremiseParticular) {
            boolean result = !polysyllogism.getConclusion().quantity;

            if (result) {
                return new RuleResult(true, Traductor.get("rule_p_correct"));
            } else {
                return new RuleResult(false, Traductor.get("rule_p_incorrect"));
            }
        } else {
            return new RuleResult(true, "Regle P : Aucune prémisse n'est particulière !");
        }
    }
}

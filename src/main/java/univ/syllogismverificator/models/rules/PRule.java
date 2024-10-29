package univ.syllogismverificator.models.rules;

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
                return new RuleResult(true, "Regle P : Une prémisse est particulière, et la conclusion l'est aussi.");
            } else {
                return new RuleResult(false, "Regle P : Une prémisse est particulière, mais la conclusion ne l'est pas.");
            }
        } else {
            return new RuleResult(true, "Regle P : Aucune prémisse n'est particulière !");
        }
    }
}

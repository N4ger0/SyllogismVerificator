package univ.syllogismverificator.models.rules;

import univ.syllogismverificator.Traductor;
import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Proposition;

/**
 * Resolves the pp rule.
 */
public class PpRule implements Rule {
    @Override
    public RuleResult evaluate(Polysyllogism polysyllogism) {
        boolean areAllPremisesParticular = true;

        for (Proposition prop : polysyllogism.getPropositions()) {
            areAllPremisesParticular = areAllPremisesParticular && !prop.quantity;
        }

        if(areAllPremisesParticular)
            return new RuleResult(false, Traductor.get("rpp_invalid"));
        return new RuleResult(true, Traductor.get("rpp_valid"));
    }
}

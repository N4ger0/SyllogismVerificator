package univ.syllogismverificator.models.rules;

import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Proposition;

public class PpRule implements Rule {
    @Override
    public RuleResult evaluate(Polysyllogism polysyllogism) {
        boolean areAllPremisesParticular = true;

        for (Proposition prop : polysyllogism.getPropositions()) {
            areAllPremisesParticular = areAllPremisesParticular && !prop.quantity;
        }

        // TODO: custom message
        return new RuleResult(!areAllPremisesParticular, "Regle Pp : ");
    }
}

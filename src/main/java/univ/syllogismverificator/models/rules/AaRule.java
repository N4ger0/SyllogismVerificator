package univ.syllogismverificator.models.rules;

import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Proposition;

public class AaRule implements Rule {
    @Override
    public RuleResult evaluate(Polysyllogism polysyllogism) {
        boolean areAllPremisesAffirmative = true;

        for (Proposition prop : polysyllogism.getPropositions()) {
            areAllPremisesAffirmative = areAllPremisesAffirmative && prop.quality;
        }

        // TODO: custom message
        if (areAllPremisesAffirmative) {
            boolean result = areAllPremisesAffirmative && polysyllogism.getConclusion().quality;

            if (result) {
                return new RuleResult(result, "Regle Aa : Toutes les prémises sont affirmatives, la conclusion aussi.");
            } else {
                return new RuleResult(result, "Regle Aa : Toutes les prémisses sont affirmatives, mais la conclusion ne l'est pas.");
            }
        } else {
            return new RuleResult(true, "Regle Aa : Toutes les prémisses ne sont pas affirmatives !");
        }
    }
}

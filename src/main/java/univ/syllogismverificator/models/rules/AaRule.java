package univ.syllogismverificator.models.rules;

import univ.syllogismverificator.Traductor;
import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Proposition;

/**
 * Resolves the aa rule.
 */
public class AaRule implements Rule {
    @Override
    public RuleResult evaluate(Polysyllogism polysyllogism) {
        boolean areAllPremisesAffirmative = true;

        for (Proposition prop : polysyllogism.getPropositions()) {
            areAllPremisesAffirmative = areAllPremisesAffirmative && prop.quality;
        }

        // TODO: custom message
        if (areAllPremisesAffirmative) {
            boolean result = polysyllogism.getConclusion().quality;

            if (result) {
                return new RuleResult(true, Traductor.get("rule_aa_correct"));
            } else {
                return new RuleResult(false, Traductor.get("rule_aa_incorrect"));
            }
        } else {
            return new RuleResult(true, Traductor.get("rule_aa_correct"));
        }
    }
}

package univ.syllogismverificator.models.rules;

import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Proposition;

public class NnRule implements Rule {
    @Override
    public RuleResult evaluate(Polysyllogism polysyllogism) {
        boolean result = false;

        for (Proposition prop : polysyllogism.getPropositions()) {
            result = result || prop.quality;
        }

        // TODO: custom message
        return new RuleResult(result, "");
    }
}

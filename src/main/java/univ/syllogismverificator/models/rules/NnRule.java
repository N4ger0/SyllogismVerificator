package univ.syllogismverificator.models.rules;

import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Proposition;

public class NnRule implements Rule {
    @Override
    public RuleResult evaluate(Polysyllogism polysyllogism) {
        boolean result = true;

        for (Proposition prop : polysyllogism.getPropositions()) {
            result = result && !prop.quality;
        }

        return new RuleResult(result, "Coucou :)");
    }
}

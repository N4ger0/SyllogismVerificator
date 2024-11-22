package univ.syllogismverificator.models.rules;

import univ.syllogismverificator.Traductor;
import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Proposition;

/**
 * Resolves the n rule.
 */
public class NRule implements Rule{
    @Override
    public RuleResult evaluate(Polysyllogism polysyllogism) {
        // TODO: test
        if (!polysyllogism.getConclusion().quality)
            return new RuleResult(true, Traductor.get("Rn est respectée : la conclusion est négative."));

        for (Proposition proposition : polysyllogism.getPropositions()){
            if (!proposition.quality){
                if (polysyllogism.getConclusion().quality){
                    return new RuleResult(false, Traductor.get("rn_invalid"));
                }
            }
        }
        return new RuleResult(true, "rn_valid");
    }
}

/*
premisse conclusion
V V V
F V F
V F V
F F V


*/
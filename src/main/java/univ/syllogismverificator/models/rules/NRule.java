package univ.syllogismverificator.models.rules;

import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Proposition;

public class NRule implements Rule{
    @Override
    public RuleResult evaluate(Polysyllogism polysyllogism) {
        if (!polysyllogism.getConclusion().quality){
            for (Proposition proposition : polysyllogism.getPropositions()){
                if (!proposition.quality){
                    return new RuleResult(true, "");
                }
            }
            return new RuleResult(false, "");
        }
        return new RuleResult(true, "");
    }
}

/*
premisse conclusion
V V V
F V F
V F V
F F V


*/
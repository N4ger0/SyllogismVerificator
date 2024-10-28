package univ.syllogismverificator.models.rules;

import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Proposition;

public class Ruleuu implements Rule{
    @Override
    public RuleResult evaluate(Polysyllogism polysyllogism) {
        boolean arePropositionsUniversal = true;

        for (Proposition proposition : polysyllogism.getPropositions())
            arePropositionsUniversal = arePropositionsUniversal && proposition.quantity;

        if (arePropositionsUniversal && !polysyllogism.getConclusion().quantity)
            return new RuleResult(false, "");
        else
            return new RuleResult(true, "");    }
}

/*
premisse conclusion
U U F
U P V
P U V
P P V
 */

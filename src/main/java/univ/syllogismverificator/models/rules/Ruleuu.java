package univ.syllogismverificator.models.rules;

import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Proposition;

/**
 * Resolves the uu rule.
 */
public class Ruleuu implements Rule{
    @Override
    public RuleResult evaluate(Polysyllogism polysyllogism) {
        boolean arePropositionsUniversal = true;

        for (Proposition proposition : polysyllogism.getPropositions())
            arePropositionsUniversal = arePropositionsUniversal && proposition.quantity;


        if (polysyllogism.getConclusion().quantity)
            return new RuleResult(true, "ruu_valid_universal_conclusion");
        else if (!arePropositionsUniversal)
            return new RuleResult(true, "ruu_valid_particular_premises");
        else
            return new RuleResult(false, "ruu_invalid");
    }
}
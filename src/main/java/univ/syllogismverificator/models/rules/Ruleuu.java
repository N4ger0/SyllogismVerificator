package univ.syllogismverificator.models.rules;

import univ.syllogismverificator.Traductor;
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
            return new RuleResult(true, Traductor.get("ruu_valid_universal_conclusion"));
        else if (!arePropositionsUniversal)
            return new RuleResult(true, Traductor.get("ruu_valid_particular_premises"));
        return new RuleResult(false, Traductor.get("ruu_invalid"));
    }
}
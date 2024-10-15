package univ.syllogismverificator.models.rules;

import univ.syllogismverificator.models.Polysyllogism;

public class Ruleuu implements Rule{
    @Override
    public RuleResult evaluate(Polysyllogism polysyllogism) {
        return new RuleResult(polysyllogism.stream().noneMatch(proposition -> proposition.quantity) || !polysyllogism.getConclusion().quantity, "U Rule Violation: All propositions must be universal.");
    }
}

/*
premisse conclusion
U U F
U P V
P U V
P P V
 */

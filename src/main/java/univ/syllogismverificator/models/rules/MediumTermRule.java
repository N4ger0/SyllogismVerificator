package univ.syllogismverificator.models.rules;

import univ.syllogismverificator.models.Polysyllogism;

public class MediumTermRule implements Rule{
    @Override
    public RuleResult evaluate(Polysyllogism polysyllogism) {
        return new RuleResult(polysyllogism.stream().anyMatch(proposition -> proposition.quantity), "Medium Term Rule Violation: At least one proposition must be universal.");
    }
}

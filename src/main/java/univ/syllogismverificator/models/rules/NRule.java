package univ.syllogismverificator.models.rules;

import univ.syllogismverificator.models.Polysyllogism;

public class NRule implements Rule{
    @Override
    public RuleResult evaluate(Polysyllogism polysyllogism) {
        return new RuleResult(polysyllogism.stream().anyMatch(proposition -> !proposition.quality) || polysyllogism.getConclusion().quality, "N Rule Violation: At least one proposition must be negative.");
    }
}

/*
premisse conclusion
V V V
F V F
V F V
F F V


*/
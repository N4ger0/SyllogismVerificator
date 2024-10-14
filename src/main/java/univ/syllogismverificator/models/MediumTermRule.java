package univ.syllogismverificator.models;

public class MediumTermRule implements Rule{
    @Override
    public RuleResult evaluate(Polysyllogism polysyllogism) {
        return new RuleResult(polysyllogism.stream().anyMatch(proposition -> proposition.quantity), "Medium Term Rule Violation: At least one proposition must be universal.");
    }
}

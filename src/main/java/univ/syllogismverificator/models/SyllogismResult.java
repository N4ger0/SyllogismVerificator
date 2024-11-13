package univ.syllogismverificator.models;

import univ.syllogismverificator.models.rules.RuleResult;

import java.util.List;

/**
 * Represents the result of the solver given a polysyllogism.
 * The result contains the validity of the polysiyllogism and information on
 * every rule that has been checked during the validation process.
 */
public class SyllogismResult {
    private final boolean valid;
    private final List<RuleResult> results;

    /**
     * Creates a result from rule results and whether the polysyllogism is valid or not.
     * @param results The list of rule results that will be added to the ruleResult.
     * @param valid The validity of the polysyllogism.
     */
    public SyllogismResult(List<RuleResult> results, boolean valid) {
        this.valid = valid;
        this.results = results;
    }

    @Override
    public String toString() {
        return results.stream()
                .map(RuleResult::toString)
                .reduce((a, b) -> a + "\n" + b)
                .orElse("");
    }

    public void addRuleResult(RuleResult ruleResult) {
        results.add(ruleResult);
    }

    /**
     * Returns the valifity of the result.
     * @return a boolean that represents the validity of the result.
     */
    public boolean isValid() {
        return valid;
    }

    public List<RuleResult> getResults() {
        return results;
    }

    public int getResultCount() {
        return results.size();
    }
}

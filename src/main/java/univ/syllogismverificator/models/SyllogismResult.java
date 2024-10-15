package univ.syllogismverificator.models;

import univ.syllogismverificator.models.rules.RuleResult;

import java.util.List;

public class SyllogismResult {
    private final boolean valid;
    private final List<RuleResult> results;

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
}

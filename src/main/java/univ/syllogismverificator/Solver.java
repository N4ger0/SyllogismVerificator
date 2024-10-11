package univ.syllogismverificator;

import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Rule;
import univ.syllogismverificator.models.RuleResult;
import univ.syllogismverificator.models.SyllogismResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Solver {
    private final List<Rule> rules = new ArrayList<>();

    public SyllogismResult solve(Polysyllogism polysyllogism){
        List<RuleResult> results = rules.stream()
                .map(polysyllogism::accept)
                .collect(Collectors.toList());
        boolean valid = results.stream().allMatch(RuleResult::isValid);
        return new SyllogismResult(results, valid);
    }
}

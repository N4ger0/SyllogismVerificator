package univ.syllogismverificator;

import univ.syllogismverificator.models.*;
import univ.syllogismverificator.models.rules.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Solver {
    private final List<Rule> rules = new ArrayList<>();

    public Solver(Rule... rules){
        this.rules.addAll(List.of(rules));
    }
    public Solver(){
        this(new MediumTermRule(),
                new NnRule(),
                new NnRule(),
                new PRule(),
                new PpRule(),
                new AaRule(),
                new Ruleuuu());
    }

    /**
     * Solve a polysyllogism
     * @param polysyllogism the polysyllogism to solve
     * @return the results of the syllogism
     */
    public SyllogismResult solve(Polysyllogism polysyllogism){
        List<RuleResult> results = rules.stream()
                .map(polysyllogism::accept)
                .collect(Collectors.toList());
        boolean valid = results.stream().allMatch(RuleResult::isValid);
        return new SyllogismResult(results, valid);
    }
}

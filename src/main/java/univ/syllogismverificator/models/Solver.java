package univ.syllogismverificator.models;

import univ.syllogismverificator.models.*;
import univ.syllogismverificator.models.rules.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class that allows to determine whether polysyllogisms are
 * valid with a given set of rules.
 */
public class Solver {
    private final List<Rule> rules = new ArrayList<>();

    /**
     * Creates a solver that will verify a polysyllogism from a given set of rules.
     *
     * @param rules A variadic argument that represents the rules.
     */
    public Solver(Rule... rules){
        this.rules.addAll(List.of(rules));
    }

    /**
     * Default constructor for solvers. Creates a solver using all the standard rules,
     * aka Raa, Rlh, Rmt, Rnn, Rn, Rpp, Rp and Ruu
     */
    public Solver(){
        this(
                new MediumTermRule(),
                new NRule(),
                new NnRule(),
                new PRule(),
                new PpRule(),
                new AaRule(),
                new LhRule(),
                new Ruleuu()
        );
    }

    /**
     * Solve a polysyllogism
     * @param polysyllogism the polysyllogism to solve
     * @return the results of the syllogism
     */
    public SyllogismResult solve(Polysyllogism polysyllogism, boolean checkRmt, boolean checkRlh, boolean checkRnn, boolean checkRn, boolean checkRaa, boolean checkRpp, boolean checkRp, boolean checkRuu){
        List<RuleResult> results = null;
        if (checkRmt) {
            results.add(rules.get(0).evaluate(polysyllogism));
        }
        if (checkRlh) {
            results.add(rules.get(1).evaluate(polysyllogism));
        }
        if (checkRnn) {
            results.add(rules.get(2).evaluate(polysyllogism));
        }
        if (checkRn) {
            results.add(rules.get(3).evaluate(polysyllogism));
        }
        if (checkRaa) {
            results.add(rules.get(4).evaluate(polysyllogism));
        }
        if (checkRpp) {
            results.add(rules.get(5).evaluate(polysyllogism));
        }
        if (checkRp) {
            results.add(rules.get(6).evaluate(polysyllogism));
        }
        if (checkRuu) {
            results.add(rules.get(7).evaluate(polysyllogism));
        }
        boolean valid = true;
        if (results != null) {
            valid = results.stream().allMatch(RuleResult::isValid);
        }

        return new SyllogismResult(results, valid);
    }

    /**
     * Solve a polysyllogism
     * @param polysyllogism the polysyllogism to solve
     * @return the results of the syllogism
     */
    public SyllogismResult solve(Polysyllogism polysyllogism, boolean checkRmt, boolean checkRlh, boolean checkRnn, boolean checkRn, boolean checkRaa, boolean checkRpp, boolean checkRp, boolean checkRuu, boolean checkInterestingSyllogism) {
        SyllogismResult res = solve(polysyllogism, checkRmt, checkRlh, checkRnn, checkRn, checkRaa, checkRpp, checkRp, checkRuu);
        if (res.isValid()) {
            if (checkInterestingSyllogism && !polysyllogism.isConclusionUniversal()) {
                SyllogismResult res_bis;
                Polysyllogism universalSyllogism = new Polysyllogism(polysyllogism);
                res_bis = solve(universalSyllogism, checkRmt, checkRlh, checkRnn, checkRn, checkRaa, checkRpp, checkRp, checkRuu);
                RuleResult interestingSRuleResult;
                if (res_bis.isValid()) {
                    String universalSyllogismString = "Ce syllogisme est ininteressant. \nVoici la conclusion universelle : ";
                    interestingSRuleResult = new RuleResult(false, universalSyllogismString.concat(universalSyllogism.toStringConclusion()));
                }
                else {
                    interestingSRuleResult = new RuleResult(true, "Ce syllogisme est interessant.");
                }
                res.addRuleResult(interestingSRuleResult);
            }
        }
        return res;
    }
}

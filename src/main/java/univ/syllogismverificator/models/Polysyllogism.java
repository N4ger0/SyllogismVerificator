package univ.syllogismverificator.models;

import java.util.ArrayList;
import java.util.List;

public class Polysyllogism {
    public final List<Proposition> propositions = new ArrayList<>();

    //TODO : Faire la méthode accept et le retour de RuleResult.
    public RuleResult accept(Rule rule) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}

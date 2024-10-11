package models;

import java.util.List;

public class SyllogismResult {
    private final boolean valid;
    private final List<RuleResult> results;

    //TODO : Faire le Constructeur.
    public SyllogismResult(List<RuleResult> results, boolean valid) {
        this.valid = valid;
        this.results = results;
    }
}

package models;

import java.util.List;

public class RuleResult {
    private final boolean valid;
    private final String message;

    public RuleResult(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }
}
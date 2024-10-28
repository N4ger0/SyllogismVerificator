package univ.syllogismverificator.models.rules;

public class RuleResult {
    private final boolean valid;
    private final String message;

    public RuleResult(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

    public boolean isValid() {
        return valid;
    }

    @Override
    public String toString() {
        return valid ? "" : message;
    }
}
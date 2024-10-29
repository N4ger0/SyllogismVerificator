package univ.syllogismverificator.models.rules;

/**
 * Represents a rule result after being applied on a polysyllogism.
 */
public class RuleResult {
    private final boolean valid;
    private final String message;

    /**
     * Creates a rule result
     *
     * @param valid The validity of the rule result
     * @param message Details on the rule result's success.
     */
    public RuleResult(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

    /**
     * Returns the polysyllogism's validity according to the rule.
     * @return a boolean that represents the validity.
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Creates a string that represents the result.
     */
    @Override
    public String toString() {
        return valid ? "" : message;
    }
}
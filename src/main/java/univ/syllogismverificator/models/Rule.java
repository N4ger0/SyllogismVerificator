package univ.syllogismverificator.models;

public interface Rule {
    RuleResult evaluate();

    String message();
}

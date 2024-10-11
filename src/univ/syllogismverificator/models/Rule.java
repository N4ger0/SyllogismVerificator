package models;

public interface Rule {
    RuleResult evaluate();

    String message();
}

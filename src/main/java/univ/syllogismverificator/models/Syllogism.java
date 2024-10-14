package univ.syllogismverificator.models;

import java.util.List;

/**
 * Represents a normal syllogism with 3 propositions
 */
public class Syllogism extends Polysyllogism {
    public Syllogism(Proposition major, Proposition minor, Proposition conclusion) {
        super(List.of(major, minor, conclusion));
    }
}

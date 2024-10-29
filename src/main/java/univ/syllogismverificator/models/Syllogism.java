package univ.syllogismverificator.models;

import java.util.List;

/**
 * Represents a normal syllogism with 3 propositions
 */
public class Syllogism extends Polysyllogism {

    /**
     * Creates a syllogism from 3 propositions.
     * @param major The major proposition of the syllogism.
     * @param minor The minor proposition of the syllogism.
     * @param conclusion The conclusion of the syllogism.
     */
    public Syllogism(Proposition major, Proposition minor, Proposition conclusion) {
        super(List.of(major, minor, conclusion));
    }
}

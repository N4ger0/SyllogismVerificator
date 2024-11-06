package univ.syllogismverificator.models;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that generates all the syllogisms that can exist and that
 * checks whether they are valid or not.
 */
public class SyllogismsGenerator {
    private List<Pair<Syllogism, SyllogismResult>> syllogisms = new ArrayList<>();

    private Proposition createPropositionFromLetters(int propositionLetters, String s, String p) {
        boolean quantity, quality;

        switch (propositionLetters) {
            case 0: // A
                quantity = true;
                quality = true;
                break;
            case 1: // E
                quantity = true;
                quality = false;
                break;
            case 2: // I
                quantity = false;
                quality = true;
                break;
            case 3: // O
                quantity = false;
                quality = false;
                break;
            default:
                throw new IllegalArgumentException("Illegal proposition letter: " + propositionLetters);
        }

        // FIXME: change if constructor is edited
        return new Proposition(p, s, quantity, quality);
    }

    private Syllogism createPolysyllogismFromFigureAndProps(int figure, int firstProp, int secondProp, int thirdProp) {
        Proposition majorPremise, minorPremise;
        Proposition conclusion = createPropositionFromLetters(thirdProp, "s", "p");

        switch (figure) {
            case 0: // figure 1
                majorPremise = createPropositionFromLetters(firstProp, "m", "p");
                minorPremise = createPropositionFromLetters(secondProp, "s", "m");
                break;
            case 1: // figure 2
                majorPremise = createPropositionFromLetters(firstProp, "p", "m");
                minorPremise = createPropositionFromLetters(secondProp, "s", "m");
                break;
            case 2: // figure 3
                majorPremise = createPropositionFromLetters(firstProp, "m", "p");
                minorPremise = createPropositionFromLetters(secondProp, "m", "s");
                break;
            case 3: // figure 4
                majorPremise = createPropositionFromLetters(firstProp, "p", "m");
                minorPremise = createPropositionFromLetters(secondProp, "m", "s");
                break;
            default:
                throw new IllegalArgumentException("Illegal figure: " + figure);
        }

        return new Syllogism(majorPremise, minorPremise, conclusion);
    }

    /**
     * Creates a SyllogismGenerator object that contains every syllogism
     * and their validity.
     */
    public SyllogismsGenerator() {
        Solver solver = new Solver();

        for (int figure = 0; figure < 4; figure++) {
            for (int firstProp = 0; firstProp < 4; firstProp++) {
                for (int secondProp = 0; secondProp < 4; secondProp++) {
                    for (int thirdProp = 0; thirdProp < 4; thirdProp++) {
                        Syllogism syllogism = createPolysyllogismFromFigureAndProps(figure, firstProp, secondProp, thirdProp);
                        SyllogismResult result = solver.solve(syllogism, true, true, true, true, true, true, true, true);
                        syllogisms.add(new Pair<>(syllogism, result));
                    }
                }
            }
        }
    }

    /**
     * Returns a list of all the syllogisms and their validity.
     *
     * @return a list of pairs (JavaFx standard class) of a Syllogism and a
     * SyllogismResult for all the 256 syllogisms.
     */
    public List<Pair<Syllogism, SyllogismResult>> getSyllogisms() {
        return this.syllogisms;
    }
}

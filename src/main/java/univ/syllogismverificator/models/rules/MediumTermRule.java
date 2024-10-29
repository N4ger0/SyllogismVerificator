package univ.syllogismverificator.models.rules;

import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Proposition;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Resolves the medium term rule.
 */
public class MediumTermRule implements Rule{
    @Override
    public RuleResult evaluate(Polysyllogism polysyllogism) {
        Set<String> goodMidTerms = new HashSet<>();
        Set<String> badMidTerms = new HashSet<>();
        List<Proposition> propositions = polysyllogism.getPropositions();
        AtomicReference<Proposition> previousProposition = new AtomicReference<>();
        propositions.forEach(proposition -> {
            if (polysyllogism.isMidTerm(proposition.subject)) { // Check if the subject of the proposition is a mid term.
                if (!goodMidTerms.contains(proposition.subject)) { // Check if the mid term is not already a good mid term.
                    if (proposition.quantity) { // Proposition is of type A or E.
                        goodMidTerms.add(proposition.subject); // The subject is a mid term and is universal in the proposition.
                    }
                    else { // Proposition is of type I or O.
                        if (previousProposition.get() != null) { // Proposition is not the first proposition.
                            if (previousProposition.get().isTerm(proposition.subject)) { // Subject is in the previous proposition.
                                badMidTerms.add(proposition.subject); // The subject is a mid term but not universal.
                            }
                        }
                    }
                }
            }
            if (polysyllogism.isMidTerm(proposition.predicate)){ // Check if the predicate of the proposition is a mid term.
                if (!goodMidTerms.contains(proposition.predicate)) { // Check if the mid term is not already a good mid term.
                    if (!proposition.quality) { // Proposition is of type E or O.
                        goodMidTerms.add(proposition.predicate); // The predicate is a mid term and is universal in the proposition.
                    }
                    else { // Proposition is of type A or I.
                        if (previousProposition.get() != null) { // Proposition is not the first proposition.
                            if  (previousProposition.get().isTerm(proposition.predicate)) { // predicate is in the previous proposition.
                                badMidTerms.add(proposition.predicate); // The predicate is a mid term and is not universal in the proposition.
                            }
                        }
                    }
                }
            }
            previousProposition.set(proposition);
        });

        if (!badMidTerms.isEmpty()) {
            //TODO: affichage réponse avec les termes moyens faux
            return new RuleResult(false, "Regle Mt : ");
        }
        //TODO: affichage réponse
        return new RuleResult(true, "Regle Mt : ");
    }
}

package univ.syllogismverificator.models.rules;

import univ.syllogismverificator.Traductor;
import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Proposition;

/**
 * Resolves the lh rule.
 */
public class LhRule implements Rule {
    private boolean getSubjectQuantity(Proposition proposition) {
        return proposition.quantity; //
    }

    private boolean getPredicateQuantity(Proposition proposition) {
        return !proposition.quality; //
    }

    @Override
    public RuleResult evaluate(Polysyllogism polysyllogism) {
        boolean isConclusionSubjectUniversal = getSubjectQuantity(polysyllogism.getConclusion()); //
        boolean isConclusionQuantityUniversal = getPredicateQuantity(polysyllogism.getConclusion()); //

        boolean isConclusionSubjectValid = true;
        // Checks if the subject of the conclusion is universal. If the subject is universal elsewhere, the rule is respected for it.
        if (isConclusionSubjectUniversal) {

            if (polysyllogism.isMidTerm(polysyllogism.getPropositions().getLast().predicate)) {
                isConclusionSubjectValid = getSubjectQuantity(polysyllogism.getPropositions().getLast());
            } else {
                isConclusionSubjectValid = getPredicateQuantity(polysyllogism.getPropositions().getLast());
            }
        }

        boolean isConclusionPredicateValid = true;
        // Checks if the subject of the conclusion is universal. If the subject is universal elsewhere, the rule is respected for it.
        if (isConclusionQuantityUniversal) {
            if (polysyllogism.isMidTerm(polysyllogism.getPropositions().getFirst().predicate)) {
                isConclusionPredicateValid = getSubjectQuantity(polysyllogism.getPropositions().getFirst());
            } else {
                isConclusionPredicateValid = getPredicateQuantity(polysyllogism.getPropositions().getFirst());
            }
        }

        // TODO: custom message
        if(isConclusionPredicateValid && isConclusionSubjectValid) return new RuleResult(true, Traductor.get("rule_lh_correct"));
        return new RuleResult(false, Traductor.get("rule_lh_incorrect"));
    }
}

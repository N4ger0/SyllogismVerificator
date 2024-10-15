package univ.syllogismverificator.models.rules;

import univ.syllogismverificator.models.Polysyllogism;
import univ.syllogismverificator.models.Proposition;

public class LhRule implements Rule {
    private boolean getSubjectQuantity(Proposition proposition) {
        return proposition.quantity;
    }

    private boolean getPredicateQuantity(Proposition proposition) {
        return !proposition.quality;
    }

    @Override
    public RuleResult evaluate(Polysyllogism polysyllogism) {
        boolean isConclusionSubjectUniversal = getSubjectQuantity(polysyllogism.getConclusion());
        boolean isConclusionQuantityUniversal = getPredicateQuantity(polysyllogism.getConclusion());

        boolean isConclusionSubjectValid = true;
        // Checks if the subject of the conclusion is universal. If the subject is universal elsewhere, the rule is respected for it.
        if (isConclusionSubjectUniversal) {
            boolean isMidTermPredicate = polysyllogism.getPropositions().getFirst().subject.equals(polysyllogism.getConclusion().subject);

            if (isMidTermPredicate) {
                isConclusionSubjectValid = getSubjectQuantity(polysyllogism.getPropositions().getFirst());
            } else {
                isConclusionSubjectValid = getPredicateQuantity(polysyllogism.getPropositions().getFirst());
            }
        }

        boolean isConclusionPredicateValid = true;
        // Checks if the subject of the conclusion is universal. If the subject is universal elsewhere, the rule is respected for it.
        if (isConclusionQuantityUniversal) {
            boolean isMidTermPredicate = polysyllogism.getPropositions().getLast().predicate.equals(polysyllogism.getConclusion().subject);

            if (isMidTermPredicate) {
                isConclusionPredicateValid = getSubjectQuantity(polysyllogism.getPropositions().getLast());
            } else {
                isConclusionPredicateValid = getPredicateQuantity(polysyllogism.getPropositions().getLast());
            }
        }

        // TODO: custom message
        return new RuleResult(isConclusionPredicateValid && isConclusionSubjectValid, "");
    }
}

package univ.syllogismverificator.models;

/**
 * Represents a proposition of a syllogism/polysyllogism
 */
public class Proposition {
    public final boolean quantity;
    public final boolean quality;
    public final String predicate ;
    public final String subject ;

    /**
     * Creates a proposition form a predicate, a subject and a quality.
     * @param predicate The predicate of the proposition.
     * @param subject The subject of the proposition.
     * @param quantity The quantity of the proposition.
     * @param quality The quality of the proposition.
     */
    public Proposition(String predicate, String subject, boolean quantity, boolean quality){
        this.predicate = predicate;
        this.subject = subject;
        this.quantity = quantity;
        this.quality = quality;
    }

    /**
     * Returns a string that represents the proposition.
     * @return a string that represents the proposition.
     */
    @Override
    public String toString() {
        String returnString = "";

        if (quantity && quality)
            returnString += "A ";
        else if (quantity && !quality)
            returnString += "E ";
        else if (!quantity && quality)
            returnString += "I ";
        else
            returnString += "O ";

        return returnString + subject + " " + predicate;
    }

    /**
     * Returns whether a term is part of the proposition or not.
     * @param term
     * @return
     */
    public boolean isTerm(String term){
        return (term.equals(predicate) || term.equals(subject));
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() != this.getClass()) {
            return false;
        }

        Proposition p = (Proposition)o;
        return this.predicate.equals(p.predicate) && this.subject.equals(p.subject);
    }
}

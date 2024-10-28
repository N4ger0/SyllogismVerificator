package univ.syllogismverificator.models;

public class Proposition {
    public final boolean quantity;
    public final boolean quality;
    public final String predicate ;
    public final String subject ;

    public Proposition(String predicate, String subject, boolean quantity, boolean quality){
        this.predicate = predicate;
        this.subject = subject;
        this.quantity = quantity;
        this.quality = quality;
    }

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

    public boolean isTerm(String term){
        return (term.equals(predicate) || term.equals(subject));
    }
}

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
        String q = quantity ? "Universal" : "Particular";
        String q2 = quality ? "Affirmative" : "negative";
        return q + " " + q2 + ": " + subject + " " + predicate;
    }
}

package univ.syllogismverificator.models;

public class Proposition {
    private final boolean quantity;
    private final boolean quality;
    private final String predicate ;
    private final String subject ;

    public Proposition(String predicate, String subject, boolean quantity, boolean quality){
        this.predicate = predicate;
        this.subject = subject;
        this.quantity = quantity;
        this.quality = quality;
    }
}

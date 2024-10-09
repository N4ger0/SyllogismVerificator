package univ.syllogismverificator.models;

public class QuantifierQuality {
    enum Type {
        A, E, I, O
    }
    private Type type ;
    private String name ;
    QuantifierQuality(Type type, String name){
        this.type = type;
        this.name = name;
    }
    Type getType(){
        return this.type ;
    }
    String getName(){
        return this.name ;
    }
}

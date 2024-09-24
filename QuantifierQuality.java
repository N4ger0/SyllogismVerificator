public class QuantifierQuality {
    enum Class {
        A, E, I, O
    }
    private Class classe ;
    private String name ;
    QuantifierQuality(Class classe, String name){
        this.classe = classe;
        this.name = name;
    }
    Class getClasse(){
        return this.classe ;
    }
    String getName(){
        return this.name ;
    }
}

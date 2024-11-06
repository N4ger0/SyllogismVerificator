package univ.syllogismverificator.controllers;

import javafx.beans.property.SimpleStringProperty;

public class SyllogismData {

    private final SimpleStringProperty qql1;
    private final SimpleStringProperty qql2;
    private final SimpleStringProperty qql3;
    private final SimpleStringProperty figure;
    private final SimpleStringProperty validity;

    public SyllogismData(String qql1, String qql2, String qql3, String figure, String validity) {
        this.qql1 = new SimpleStringProperty(qql1);
        this.qql2 = new SimpleStringProperty(qql2);
        this.qql3 = new SimpleStringProperty(qql3);
        this.figure = new SimpleStringProperty(figure);
        this.validity = new SimpleStringProperty(validity);
    }

    public String getQql1() {
        return qql1.get();
    }

    public String getFigure(){
        return figure.get();
    }

    public String getQql2() {
        return qql2.get();
    }

    public String getQql3() {
        return qql3.get();
    }

    public String getValidity() {
        return validity.get();
    }
}

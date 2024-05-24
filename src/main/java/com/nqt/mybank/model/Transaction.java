package com.nqt.mybank.model;


//A transaction consists of an id,an amount,
// a timestamp and a reference string ("eating out at mcdonalds").
// The API should return JSON and the timestamps should be
// formatted nicely as yyyy-MM-dd’T’HH:mm’Z'. Use an embedded Tomcat,
// a servlet and the appropriate services for this.
// Note: You’ll need an additional Jackson library,
// if you are using Java8+ datettimes for the timestamp. Find out which one!


import java.sql.Timestamp;

public class Transaction {

    private String id;

    private int amount;

    private Timestamp timestamp;

    private String reference;

    private String slogan;

    public Transaction() {
    }

    public Transaction(String id, int amount, Timestamp timestamp, String reference, String slogan) {
        this.id = id;
        this.amount = amount;
        this.timestamp = timestamp;
        this.reference = reference;
        this.slogan = slogan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }
}

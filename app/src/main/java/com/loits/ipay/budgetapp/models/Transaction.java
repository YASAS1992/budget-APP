package com.loits.ipay.budgetapp.models;

import java.util.Date;

public class Transaction {


    private String Name;
    private double Amount;
    private String Description;
    private String Category;
    private String type;
    private Date date;
    private int transactionID;

    public Transaction(double Amount, String Description, Date date, int transactionID, String name,String type) {
        this.Amount=Amount;
        this.Description=Description;
        this.date=date;
        this.Name=name;
        this.transactionID=transactionID;
        this.type = type;
    }

    public int getID() {
        return transactionID;
    }

    public void setID(int transactionID) {
        this.transactionID = transactionID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

//    public String getCategory() {
//        return Category;
//    }

//    public void setCategory(String category) {
//        Category = category;
//    }

    public String getType() {
        return type;
    }

//    public void setType(int type) {
//        type = type;
//    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}


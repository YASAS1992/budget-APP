package com.loits.ipay.budgetapp.models;
import java.util.Date;

public class Transaction {


    private String Name;
    private double Amount;
    private String Description;
    private String Category;
    private int Type;
    private String date;
    private int transactionID;

    public Transaction(String name, double Amount, String Description, int Type, String Category, String date, int transactionID) {
        this.Amount=Amount;
        this.Description=Description;
        this.Type=Type;
        this.Category=Category;
        this.date=date;
        this.Name=name;
        this.transactionID=transactionID;
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

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}


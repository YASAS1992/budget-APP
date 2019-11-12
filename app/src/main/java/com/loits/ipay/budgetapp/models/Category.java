package com.loits.ipay.budgetapp.models;
import java.io.Serializable;

public class Category implements Serializable{
    private int id;
    private String Name;
    private double Amount;//propotion of total income.

    public Category(int id, String name, double Amount) {
        this.id = id;
        Name = name;
        this.Amount = Amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}


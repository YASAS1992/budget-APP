package com.loits.ipay.budgetapp.models;
import java.io.Serializable;

public class Category implements Serializable{
    private int id;
    private String Name;
    private double Propotion;//propotion of total income.

    public Category(int id, String name, double propotion) {
        this.id = id;
        Name = name;
        Propotion = propotion;
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

    public double getPropotion() {
        return Propotion;
    }

    public void setPropotion(double propotion) {
        Propotion = propotion;
    }
}


package com.loits.ipay.budgetapp.models;

import java.util.Date;

public class Expense extends Transaction{

    Category category;

    public Expense(double Amount, String Description, Date date, String name, Category category, int id) {
        super(Amount, Description, date,id,name,"EXPENSE");
        this.category=category;
    }

    public Category getCategory() {
        return category;
    }


}

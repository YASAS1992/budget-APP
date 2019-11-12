package com.loits.ipay.budgetapp.models;
import java.util.Date;

public class Income extends Transaction {

    public Income(double Amount, String Description, Date date, int id, String name) {
        super(Amount, Description, date, id,name,"INCOME");
    }


}

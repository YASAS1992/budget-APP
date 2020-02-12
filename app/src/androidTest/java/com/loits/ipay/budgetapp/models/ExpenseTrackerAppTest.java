package com.loits.ipay.budgetapp.models;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ExpenseTrackerAppTest {

    ExpenseTrackerApp expenseTrackerApp;

    @Before
    public void setUp() throws Exception {
        expenseTrackerApp = new ExpenseTrackerApp();

    }

    @Test
    public void testAddNewIncome(){
        String actual = expenseTrackerApp.addNewIncome(new Income(100.0,"tets",new Date(),1,"test name"));
        Assert.assertEquals("Success",actual);
    }

    @Test
    public void testUpdateIncome(){
        expenseTrackerApp.addNewIncome(new Income(100.0,"tets",new Date(),1,"test name"));
        String actual = expenseTrackerApp.updateIcome(new Transaction(100.0,"tets",new Date(),1,"test name","abc"));
        Assert.assertEquals("Income updated Successfully",actual);
    }

    @Test
    public void testDeleteIncome(){
        expenseTrackerApp.addNewIncome(new Income(100.0,"tets",new Date(),1,"test name"));
        String actual = expenseTrackerApp.deleteIncome(1);//Expense Delete Successfully
        Assert.assertEquals("Income Delete Successfully",actual);
    }


    @Test
    public void testAddNewCategory(){
        expenseTrackerApp.addNewCategory(new Category(1,"test",100.0));
        Assert.assertEquals("Success",expenseTrackerApp
                .addNewExpense(new Expense(100.0,"des",new Date(),"exp",expenseTrackerApp.getUniqueCategory(1),1)));
    }


    @Test
    public void testAddNewExpense(){
        expenseTrackerApp.addNewCategory(new Category(1,"test",100.0));
    }

    @Test
    public void testUpdateExpense(){
        expenseTrackerApp.addNewExpense(new Expense(100.0,"des",new Date(),"exp",expenseTrackerApp.getUniqueCategory(1),1));
        String actual = expenseTrackerApp.updateExpense(new Transaction(100.0,"tets",new Date(),1,"test name","abc"));
        Assert.assertEquals("Expense updated Successfully",actual);
    }

    @Test
    public void testDeleteExpense(){
        expenseTrackerApp.addNewExpense(new Expense(100.0,"des",new Date(),"exp",expenseTrackerApp.getUniqueCategory(1),1));
        String actual = expenseTrackerApp.deleteExpense(1);//Expense Delete Successfully
        Assert.assertEquals("Expense Delete Successfully",actual);
    }

    @After
    public void tearDown() throws Exception {
        expenseTrackerApp = null;
    }
}
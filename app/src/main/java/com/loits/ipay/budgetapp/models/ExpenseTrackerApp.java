package com.loits.ipay.budgetapp.models;

import java.util.ArrayList;

public class ExpenseTrackerApp {
    private static ArrayList<Transaction> transactions;
    private static ArrayList<Category> categories;

    public ExpenseTrackerApp() {
        transactions = new ArrayList<>();
        categories = new ArrayList<>();
    }

    public void addNewTransaction(Transaction transaction){
        transactions.add(transaction);
    }

    public String updateTransaction(int id, Transaction transaction){
        for (int i = 0; i < transactions.size(); i++) {
            if(transactions.get(i).getID()==id){
                transactions.add(i,transaction);
                return "Transaction updated Successfully";
            }
        }
        return "Transaction not found";
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public Transaction getUniqueTransaction(int id){
        for (int i = 0; i < transactions.size(); i++) {
            if(transactions.get(i).getID()==id)
                return transactions.get(i);
        }

        return null;
    }

    public String deleteTransaction(int id){
        for (int i = 0; i < transactions.size(); i++) {
            if(transactions.get(i).getID()==id){
               transactions.remove(i);
               return "Transaction Delete Successfully";
            }
        }
        return "Transaction Not Found";
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        transactions = transactions;
    }

    public void addNewCategory(Category category){
        categories.add(category);
    }

    public String updateCategory(int id, Category category){
        for (int i = 0; i < categories.size(); i++) {
            if(categories.get(i).getId()==id){
                categories.add(i,category);
                return "Category Updated Successfully";
            }
        }
        return "Category not found";
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public Category getUniqueCategory(int id){
        for (int i = 0; i < categories.size(); i++) {
            if(categories.get(i).getId()==id)
                return categories.get(i);
        }

        return null;
    }

    public void setCategories(ArrayList<Category> categories) {
        categories = categories;
    }

    public String deleteCategory(int id){
        for (int i = 0; i < categories.size(); i++) {
            if(categories.get(i).getId()==id){
                categories.remove(i);
                return "Category Delete Successfully";
            }
        }
        return "Category Not Found";
    }

    public double getTotalIncome(){
        double totalIncome = 0.0;

        for (int i = 0; i < transactions.size(); i++) {
            if(transactions.get(i).getType()==0){
                totalIncome += transactions.get(i).getAmount();
            }
        }

        return totalIncome;
    }

    public double getTotalExpense(){
        double totalExpense = 0.0;

        for (int i = 0; i < transactions.size(); i++) {
            if(transactions.get(i).getType()==1){
                totalExpense += transactions.get(i).getAmount();
            }
        }

        return totalExpense;
    }
}

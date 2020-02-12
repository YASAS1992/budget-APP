package com.loits.ipay.budgetapp.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ExpenseTrackerApp {
    private static ArrayList<Transaction> transactions;
    private static ArrayList<Category> categories;
    private static ArrayList<Income> incomes;
    private static ArrayList<Expense> expenses;

    public ExpenseTrackerApp() {
        transactions = new ArrayList<>();
        categories = new ArrayList<>();
        incomes = new ArrayList<>();
        expenses = new ArrayList<>();
    }


    public String addNewIncome(Income income){
        try{
            incomes.add(income);
            return "Success";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String updateIcome(Transaction t){
        for (int i = 0; i < incomes.size(); i++) {
            if(incomes.get(i).getID()==t.getID()){
                incomes.get(i).setAmount(t.getAmount());
                incomes.get(i).setDate(t.getDate());
                incomes.get(i).setName(t.getName());
                incomes.get(i).setDescription(t.getDescription());
                return "Income updated Successfully";
            }
        }
        return "Income not found";
    }

    public ArrayList<Income> getIncomes(){return incomes;}

    public Income getUniqueIncome(int id){
        for (int i = 0; i < incomes.size(); i++) {
            if(incomes.get(i).getID()==id)
                return incomes.get(i);
        }

        return null;
    }

    public double getTotalIncomeForThisMonth(){
        double total = 0.0;
        for (int i = 0; i < incomes.size(); i++) {
            if(isCurrentMonth(incomes.get(i).getDate()))
                total += incomes.get(i).getAmount();
        }
        return total;
    }

    public String deleteIncome(int id){
        for (int i = 0; i < incomes.size(); i++) {
            if(incomes.get(i).getID()==id){
                incomes.remove(i);
                return "Income Delete Successfully";
            }
        }
        return "Income Not Found";
    }

    public String addNewExpense(Expense expense){
        try{
            expenses.add(expense);
            return "Success";
        }catch (Exception e){
            return e.getMessage();
        }

    }

    public String updateExpense(Transaction t){
        for (int i = 0; i < expenses.size(); i++) {
            if(expenses.get(i).getID()==t.getID()){
                expenses.get(i).setAmount(t.getAmount());
                expenses.get(i).setDate(t.getDate());
                expenses.get(i).setName(t.getName());
                expenses.get(i).setDescription(t.getDescription());
                return "Expense updated Successfully";
            }
        }
        return "Expense not found";
    }

    public ArrayList<Expense> getExpenses(){return expenses;}

    public Expense getUniqueExpense(int id){
        for (int i = 0; i < expenses.size(); i++) {
            if(expenses.get(i).getID()==id)
                return expenses.get(i);
        }

        return null;
    }

    public double getTotalExpenseForThisMonth(){
        double total = 0.0;
        for (int i = 0; i < expenses.size(); i++) {
            if(isCurrentMonth(expenses.get(i).getDate()))
                total += expenses.get(i).getAmount();
        }
        return total;
    }

    public String deleteExpense(int id){
        for (int i = 0; i < expenses.size(); i++) {
            if(expenses.get(i).getID()==id){
                expenses.remove(i);
                return "Expense Delete Successfully";
            }
        }
        return "Expense Not Found";
    }


    public ArrayList<Transaction> getAllTransactions(){
        ArrayList<Transaction> transactions = new ArrayList<>();

        for (int i = 0; i < incomes.size(); i++) {
            Transaction transaction = incomes.get(i);
            transactions.add(transaction);
        }

        for (int i = 0; i < expenses.size(); i++) {
            Transaction transaction = expenses.get(i);
            transactions.add(transaction);
        }

        Collections.sort(transactions, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction t1, Transaction t2) {
                    return Long.valueOf(t1.getDate().getTime()).compareTo(t2.getDate().getTime());
            }
        });

        return transactions;
    }


    public String addNewCategory(Category category){
        try{
            categories.add(category);
            return "Success";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String updateCategory(int id, Category category){
        for (int i = 0; i < categories.size(); i++) {
            if(categories.get(i).getId()==id){
                categories.get(i).setAmount(category.getAmount());
                categories.get(i).setName(category.getName());
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

    public double getCurrentCatSum(int id){
        double catSum = 0.0;

        for (int i = 0; i < expenses.size(); i++) {
            if(expenses.get(i).getCategory().getId()==id && isCurrentMonth(expenses.get(i).getDate())){
                catSum += expenses.get(i).getAmount();
            }
        }

        return catSum;
    }

    public boolean isCurrentMonth(Date date){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date);
        cal2.setTime(new Date());

        if(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
            if(cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

}

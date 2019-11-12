package com.loits.ipay.budgetapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.loits.ipay.budgetapp.Adapters.CategorySpnAdapter;
import com.loits.ipay.budgetapp.MainActivity;
import com.loits.ipay.budgetapp.R;
import com.loits.ipay.budgetapp.models.Category;
import com.loits.ipay.budgetapp.models.Expense;
import com.loits.ipay.budgetapp.models.Income;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.StatementEvent;

public class HomeFragment extends Fragment {

    MainActivity mainActivity;
    private LinearLayout lytAddTransaction,lytAddCategory;
    private TextView tvAddTransaction,tvAddCategory,typeError,catError;

    private EditText etTrnsName,etTrnsAmount,etTrnsDescription,etCatName, etCatAmount;
    Spinner spnCategory;
    RadioGroup grpType;
    RadioButton rBtnIncome,rBtnExpense;
    TextView etSaveTrns,etCloseTrns,etSaveCat,etCloseCat,tvTotalIncome,tvTotalExpense,tvBalance;
    LinearLayout lytExpense,lytBalance,lytIncome,lytDebt,lytGraph;

    Category selectedCategory;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        mainActivity = (MainActivity)getActivity();
        lytAddTransaction = v.findViewById(R.id.lytAddTransaction);
        lytAddCategory = v.findViewById(R.id.lytAddCategory);
        tvAddTransaction = v.findViewById(R.id.tvAddTransaction);
        tvAddCategory = v.findViewById(R.id.tvAddCategory);

        tvTotalIncome = v.findViewById(R.id.tvTotalIncome);
        tvTotalExpense = v.findViewById(R.id.tvTotalExpense);
        tvBalance = v.findViewById(R.id.tvBalance);

        lytExpense = v.findViewById(R.id.lytExpense);
        lytBalance = v.findViewById(R.id.lytBalance);
        lytIncome = v.findViewById(R.id.lytIncome);
        lytDebt = v.findViewById(R.id.lytDebt);
        lytGraph = v.findViewById(R.id.lytGraph);

        //Add Transaction
        etTrnsName = v.findViewById(R.id.etTrnsName);
        etTrnsAmount = v.findViewById(R.id.etTrnsAmount);
        etTrnsDescription = v.findViewById(R.id.etTrnsDescription);
        spnCategory = v.findViewById(R.id.spnCategory);
        grpType = v.findViewById(R.id.grpType);
        rBtnIncome = v.findViewById(R.id.rBtnIncome);
        rBtnExpense = v.findViewById(R.id.rBtnExpense);
        typeError = v.findViewById(R.id.typeError);
        catError = v.findViewById(R.id.catError);
        etSaveTrns = v.findViewById(R.id.etSaveTrns);
        etCloseTrns = v.findViewById(R.id.etCloseTrns);

        //Add Category
        etCatName = v.findViewById(R.id.etCatName);
        etCatAmount = v.findViewById(R.id.etCatProp);
        etSaveCat = v.findViewById(R.id.etSaveCat);
        etCloseCat = v.findViewById(R.id.etCloseCat);

        getSummaryData();

        tvAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTransaction();
            }
        });

        tvAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCtegory();
            }
        });

        rBtnExpense.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    spnCategory.setVisibility(View.VISIBLE);
                }
            }
        });

        rBtnIncome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    spnCategory.setVisibility(View.GONE);
                }
            }
        });

        return v;
    }

    public void getSummaryData(){

        tvTotalIncome.setText(String.valueOf(mainActivity.trackerApp.getTotalIncomeForThisMonth()));
        tvTotalExpense.setText(String.valueOf(mainActivity.trackerApp.getTotalExpenseForThisMonth()));
        tvBalance.setText(String.valueOf(mainActivity.trackerApp.getTotalIncomeForThisMonth()-mainActivity.trackerApp.getTotalExpenseForThisMonth()));

        if(mainActivity.trackerApp.getTotalIncomeForThisMonth()==0.0 && mainActivity.trackerApp.getTotalExpenseForThisMonth()==0.0){
            lytGraph.setVisibility(View.GONE);
        }else {
            visualizeSummarry(mainActivity.trackerApp.getTotalIncomeForThisMonth(),mainActivity.trackerApp.getTotalExpenseForThisMonth());
        }

    }

    public void visualizeSummarry(double in , double ex){

        lytGraph.setVisibility(View.VISIBLE);

        double expensesW = 0;
        double balanceW = 0;
        double incomeW = 0;
        double debtW = 0;

        if(in>=ex){
            incomeW = 1;
            expensesW = ex/in;
            balanceW = (in - ex)/in;
        }else{
            expensesW = 1;
            incomeW = in/ex;
            debtW = (ex - in)/ex;
        }

        lytExpense.setLayoutParams(getLayoutWeights(1-expensesW));
        lytBalance.setLayoutParams(getLayoutWeights(1-balanceW));
        lytIncome.setLayoutParams(getLayoutWeights(1-incomeW));
        lytDebt.setLayoutParams(getLayoutWeights(1-debtW));

    }

    public void addTransaction(){
        lytAddTransaction.setVisibility(View.VISIBLE);
        CategorySpnAdapter adapter = new CategorySpnAdapter(getContext(),mainActivity.trackerApp.getCategories());
        spnCategory.setAdapter(adapter);

        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCategory = mainActivity.trackerApp.getCategories().get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        etSaveTrns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date = new Date();

                if(rBtnExpense.isChecked()){

                    if(etTrnsName.getText().toString().isEmpty()){
                        etTrnsName.requestFocus();
                        etTrnsName.setError("Please enter name.");
                    }else if(etTrnsAmount.getText().toString().isEmpty() || Double.parseDouble(etTrnsAmount.getText().toString())==0.0){
                        etTrnsAmount.requestFocus();
                        etTrnsAmount.setError("Please enter amount");
                    }else if(selectedCategory== null){
                        catError.setVisibility(View.VISIBLE);
                    }else if(Double.parseDouble(etTrnsAmount.getText().toString())>(selectedCategory.getAmount() - mainActivity.trackerApp.getCurrentCatSum(selectedCategory.getId()))){
                        Log.d("Cat", String.valueOf(selectedCategory.getAmount()));
                        Log.d("Cat-R", String.valueOf(mainActivity.trackerApp.getCurrentCatSum(selectedCategory.getId())));
                        Log.d("Cat-N", selectedCategory.getName()+"||"+String.valueOf(selectedCategory.getAmount()));
                        etTrnsAmount.requestFocus();
                        etTrnsAmount.setError("Max amount can be added to this category is "+ String.valueOf(selectedCategory.getAmount() - mainActivity.trackerApp.getCurrentCatSum(selectedCategory.getId())));
                    }else{
                        Expense e = new Expense(Double.parseDouble(etTrnsAmount.getText().toString()),
                                etTrnsDescription.getText().toString(),
                                date,
                                etTrnsName.getText().toString(),
                                selectedCategory,
                                mainActivity.trackerApp.getExpenses().size());

                        mainActivity.trackerApp.addNewExpense(e);
                        getSummaryData();
                        lytAddTransaction.setVisibility(View.GONE);
                    }

                }else if(rBtnIncome.isChecked()){

                    if(etTrnsName.getText().toString().isEmpty()){
                        etTrnsName.requestFocus();
                        etTrnsName.setError("Please enter name.");
                    }else if(etTrnsAmount.getText().toString().isEmpty() || Double.parseDouble(etTrnsAmount.getText().toString())==0.0){
                        etTrnsAmount.requestFocus();
                        etTrnsAmount.setError("Please enter amount");
                    }else{
                        Income i = new Income(Double.parseDouble(etTrnsAmount.getText().toString()),
                                etTrnsDescription.getText().toString(),
                                date,
                                mainActivity.trackerApp.getIncomes().size(),
                                etTrnsName.getText().toString());

                        mainActivity.trackerApp.addNewIncome(i);
                        getSummaryData();
                        lytAddTransaction.setVisibility(View.GONE);
                    }

                }else {
                    typeError.setVisibility(View.VISIBLE);
                }


            }
        });


        etCloseTrns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lytAddTransaction.setVisibility(View.GONE);
            }
        });

    }

    public void addCtegory(){
        lytAddCategory.setVisibility(View.VISIBLE);

        etSaveCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etCatName.getText().toString().isEmpty()){
                    etCatName.requestFocus();
                    etCatName.setError("Please enter category name.");
                }else if(etCatAmount.getText().toString().isEmpty() || Double.parseDouble(etCatAmount.getText().toString()) == 0.0){
                    etCatAmount.requestFocus();
                    etCatAmount.setError("Please enter amount;");
                }else{
                    Category category = new Category(mainActivity.trackerApp.getCategories().size(),
                            etCatName.getText().toString(),
                            Double.parseDouble(etCatAmount.getText().toString()));

                    mainActivity.trackerApp.addNewCategory(category);
                    getSummaryData();
                    lytAddCategory.setVisibility(View.GONE);
                }

            }
        });

        etCloseCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lytAddCategory.setVisibility(View.GONE);
            }
        });
    }

    private LinearLayout.LayoutParams getLayoutWeights(double w){
        return new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                (float) w
        );
    }

}
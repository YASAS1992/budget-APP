package com.loits.ipay.budgetapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.loits.ipay.budgetapp.Adapters.CategorySpnAdapter;
import com.loits.ipay.budgetapp.MainActivity;
import com.loits.ipay.budgetapp.R;
import com.loits.ipay.budgetapp.models.Category;
import com.loits.ipay.budgetapp.models.Transaction;


import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFragment extends Fragment {

    MainActivity mainActivity;
    private LinearLayout lytAddTransaction,lytAddCategory;
    private TextView tvAddTransaction,tvAddCategory;

    private EditText etTrnsName,etTrnsAmount,etTrnsDescription,etCatName,etCatProp;
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
        etSaveTrns = v.findViewById(R.id.etSaveTrns);
        etCloseTrns = v.findViewById(R.id.etCloseTrns);

        //Add Category
        etCatName = v.findViewById(R.id.etCatName);
        etCatProp = v.findViewById(R.id.etCatProp);
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

        return v;
    }

    public void getSummaryData(){

        tvTotalIncome.setText(String.valueOf(mainActivity.trackerApp.getTotalIncome()));
        tvTotalExpense.setText(String.valueOf(mainActivity.trackerApp.getTotalExpense()));
        tvBalance.setText(String.valueOf(mainActivity.trackerApp.getTotalIncome()-mainActivity.trackerApp.getTotalExpense()));

        if(mainActivity.trackerApp.getTotalIncome()==0.0 && mainActivity.trackerApp.getTotalExpense()==0.0){
            lytGraph.setVisibility(View.GONE);
        }else {
            visualizeSummarry(mainActivity.trackerApp.getTotalIncome(),mainActivity.trackerApp.getTotalExpense());
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

                int type;

                if(rBtnExpense.isChecked()){
                    type = 1;
                }else{
                    type = 0;
                }

                Transaction transaction = new Transaction(etTrnsName.getText().toString(),
                        Double.parseDouble(etTrnsAmount.getText().toString()),
                        etTrnsDescription.getText().toString(),
                        type,
                        selectedCategory.getName(),
                        formatter.format(date),
                        mainActivity.trackerApp.getTransactions().size());
                mainActivity.trackerApp.addNewTransaction(transaction);
                getSummaryData();
                lytAddTransaction.setVisibility(View.GONE);
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
                Category category = new Category(mainActivity.trackerApp.getCategories().size(),
                        etCatName.getText().toString(),
                        Double.parseDouble(etCatProp.getText().toString()));

                mainActivity.trackerApp.addNewCategory(category);
                getSummaryData();
                lytAddCategory.setVisibility(View.GONE);
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
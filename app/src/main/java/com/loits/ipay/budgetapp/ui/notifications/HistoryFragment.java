package com.loits.ipay.budgetapp.ui.notifications;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.loits.ipay.budgetapp.models.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoryFragment extends Fragment {

    MainActivity mainActivity;
    ListView lvHistory;

    LinearLayout lytBottom,lytEdit,lytDelete,lytClose,lytEditTransaction;
    Transaction selectedTransaction;
    TextView etSaveCat,etCloseCat,etSaveTrns,etCloseTrns,typeError,catError,tvTransactionTitle;

    private EditText etTrnsName,etTrnsAmount,etTrnsDescription,etCatName, etCatAmount;
    Spinner spnCategory;
    RadioGroup grpType;
    RadioButton rBtnIncome,rBtnExpense;

    Category selectedCategory;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity)getActivity();
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        lvHistory = v.findViewById(R.id.lvHistory);

        lytBottom = v.findViewById(R.id.lytBottom);
        lytEdit = v.findViewById(R.id.lytEdit);
        lytDelete = v.findViewById(R.id.lytDelete);
        lytClose = v.findViewById(R.id.lytClose);
        lytEditTransaction = v.findViewById(R.id.lytEditTransaction);
        etSaveCat = v.findViewById(R.id.etSaveCat);
        etCloseCat = v.findViewById(R.id.etCloseCat);


        tvTransactionTitle = v.findViewById(R.id.tvTransactionTitle);
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

        loadList();

        lytEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lytEditTransaction.setVisibility(View.VISIBLE);
                if(selectedTransaction.getType().equals("INCOME")){
                    rBtnIncome.setChecked(true);
                }else if(selectedTransaction.getType().equals("EXPENSE")){
                    rBtnExpense.setChecked(true);
                    CategorySpnAdapter adapter = new CategorySpnAdapter(getContext(),mainActivity.trackerApp.getCategories());
                    spnCategory.setAdapter(adapter);
                }
                lytBottom.setVisibility(View.GONE);
                updateTransaction();
            }
        });

        lytDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTransaction.getType().equals("INCOME")){
                    mainActivity.trackerApp.deleteIncome(selectedTransaction.getID());
                }else if(selectedTransaction.getType().equals("EXPENSE")){
                    mainActivity.trackerApp.deleteExpense(selectedTransaction.getID());
                }
                lytBottom.setVisibility(View.GONE);
                loadList();
            }
        });

        lytClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lytBottom.setVisibility(View.GONE);
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

    public void loadList(){
        HistoryListAdapter adapter = new HistoryListAdapter(getContext(),R.layout.history_list_item,mainActivity.trackerApp.getAllTransactions());
        lvHistory.setAdapter(adapter);
    }


    public void updateTransaction(){

        tvTransactionTitle.setText("Update Transaction");

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
                        etTrnsAmount.requestFocus();
                        etTrnsAmount.setError("Max amount can be added to this category is "+ String.valueOf(selectedCategory.getAmount() - mainActivity.trackerApp.getCurrentCatSum(selectedCategory.getId())));
                    }else{
                        Transaction t = new Transaction(Double.parseDouble(etTrnsAmount.getText().toString()),
                                etTrnsDescription.getText().toString(),
                                date,
                                selectedTransaction.getID(),
                                etTrnsName.getText().toString(),
                                "EXPENSE");

                        mainActivity.trackerApp.updateExpense(t);
                    }

                }else if(rBtnIncome.isChecked()){

                    if(etTrnsName.getText().toString().isEmpty()){
                        etTrnsName.requestFocus();
                        etTrnsName.setError("Please enter name.");
                    }else if(etTrnsAmount.getText().toString().isEmpty() || Double.parseDouble(etTrnsAmount.getText().toString())==0.0){
                        etTrnsAmount.requestFocus();
                        etTrnsAmount.setError("Please enter amount");
                    }else{
                        Transaction t = new Transaction(Double.parseDouble(etTrnsAmount.getText().toString()),
                                etTrnsDescription.getText().toString(),
                                date,
                                selectedTransaction.getID(),
                                etTrnsName.getText().toString(),
                                "INCOME");

                        mainActivity.trackerApp.updateIcome(t);
                    }

                }else {
                    typeError.setVisibility(View.VISIBLE);
                }
                lvHistory.setAdapter(null);
                loadList();
                lytEditTransaction.setVisibility(View.GONE);

            }
        });


        etCloseTrns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lytEditTransaction.setVisibility(View.GONE);
            }
        });

    }


    public class HistoryListAdapter extends ArrayAdapter {

        Context context;
        ArrayList<Transaction> transactions;
        int resource;

        public HistoryListAdapter(Context context, int resource,ArrayList<Transaction> transactions) {
            super(context, resource, transactions);
            this.context = context;
            this.resource = resource;
            this.transactions = transactions;
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder = null;

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            rowView = inflater.inflate(R.layout.history_list_item, parent, false);
            holder = new ViewHolder();
            holder.tvName = rowView.findViewById(R.id.tvName);
            holder.tvAmount = rowView.findViewById(R.id.tvAmount);
            holder.tvDate = rowView.findViewById(R.id.tvDate);
            holder.tvType = rowView.findViewById(R.id.tvType);

            if(transactions.size()>position){
                holder.tvName.setText(transactions.get(position).getName());
                holder.tvAmount.setText(String.valueOf(transactions.get(position).getAmount()));
                holder.tvDate.setText(transactions.get(position).getDate().toString());
                holder.tvType.setText(transactions.get(position).getType());

            }

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lytBottom.setVisibility(View.VISIBLE);
                    selectedTransaction = transactions.get(position);
                }
            });

            return rowView;
        }

        class ViewHolder{
            TextView tvName,tvAmount,tvDate,tvType;
        }
    }

}
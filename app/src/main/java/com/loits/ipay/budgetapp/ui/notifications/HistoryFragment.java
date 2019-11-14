package com.loits.ipay.budgetapp.ui.notifications;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.loits.ipay.budgetapp.MainActivity;
import com.loits.ipay.budgetapp.R;
import com.loits.ipay.budgetapp.models.Transaction;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    MainActivity mainActivity;
    ListView lvHistory;

    LinearLayout lytBottom,lytEdit,lytDelete,lytClose,lytEditTransaction;
    Transaction selectedTransaction;
    TextView etSaveCat,etCloseCat;

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

        loadList();

        lytEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTransaction.getType().equals("INCOME")){
                    mainActivity.trackerApp.updateIcome(selectedTransaction);
                }else if(selectedTransaction.getType().equals("EXPENSE")){
                    mainActivity.trackerApp.updateIcome(selectedTransaction);
                }
                lytBottom.setVisibility(View.GONE);
                loadList();
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

        return v;
    }

    public void loadList(){
        HistoryListAdapter adapter = new HistoryListAdapter(getContext(),R.layout.history_list_item,mainActivity.trackerApp.getAllTransactions());
        lvHistory.setAdapter(adapter);
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
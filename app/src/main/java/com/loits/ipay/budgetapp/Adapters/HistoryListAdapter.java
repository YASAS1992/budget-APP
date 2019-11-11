package com.loits.ipay.budgetapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.loits.ipay.budgetapp.R;
import com.loits.ipay.budgetapp.models.Transaction;

import java.util.ArrayList;

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
    public View getView(int position, View convertView, ViewGroup parent) {
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
            holder.tvDate.setText(transactions.get(position).getDate());

            if(transactions.get(position).getType() == 0){
                holder.tvType.setText("Income");
            }else{
                holder.tvType.setText("Expense");
            }
        }

        return rowView;
    }

    class ViewHolder{
        TextView tvName,tvAmount,tvDate,tvType;
    }
}

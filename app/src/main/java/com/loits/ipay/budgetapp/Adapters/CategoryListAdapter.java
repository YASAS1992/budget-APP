package com.loits.ipay.budgetapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.loits.ipay.budgetapp.R;
import com.loits.ipay.budgetapp.models.Category;
import com.loits.ipay.budgetapp.models.Expense;
import com.loits.ipay.budgetapp.models.ExpenseTrackerApp;

import java.util.ArrayList;

public class CategoryListAdapter extends ArrayAdapter {

    Context context;
    ArrayList<Expense> expenses;
    ArrayList<Category> categories;
    int resource;
    ExpenseTrackerApp trackerApp;

    public CategoryListAdapter(Context context, int resource, ArrayList<Category> categories,ExpenseTrackerApp trackerApp) {
        super(context, resource, categories);
        this.context = context;
        this.resource = resource;
        this.categories = categories;
        this.trackerApp = trackerApp;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder holder = null;
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        rowView = inflater.inflate(R.layout.category_list_item, parent, false);
        holder = new ViewHolder();
        holder.tvName = rowView.findViewById(R.id.tvName);
        holder.tvAmount1 = rowView.findViewById(R.id.tvAmount1);
        holder.tvAmount2 = rowView.findViewById(R.id.tvAmount2);
        holder.lyt1 = rowView.findViewById(R.id.lyt1);
        holder.lyt2 = rowView.findViewById(R.id.lyt2);

        if (categories.size()>0){
            holder.tvName.setText(categories.get(position).getName());
            holder.tvAmount1.setText(String.valueOf(trackerApp.getCurrentCatSum(categories.get(position).getId())));
            holder.tvAmount2.setText(String.valueOf(categories.get(position).getAmount()));
            double w = trackerApp.getCurrentCatSum(categories.get(position).getId())/categories.get(position).getAmount();
            holder.lyt2.setLayoutParams(getLayoutWeights(w));
            holder.lyt1.setLayoutParams(getLayoutWeights(1-w));
        }

        return rowView;
    }



    class ViewHolder {
        TextView tvName,tvAmount1,tvAmount2;
        LinearLayout lyt1,lyt2;
    }

    private LinearLayout.LayoutParams getLayoutWeights(double w){
        return new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                (float) w
        );
    }
}

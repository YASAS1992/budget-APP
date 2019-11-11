package com.loits.ipay.budgetapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.loits.ipay.budgetapp.R;
import com.loits.ipay.budgetapp.models.Category;

import java.util.ArrayList;

public class CategorySpnAdapter extends BaseAdapter implements SpinnerAdapter {

    Context context;
    ArrayList<Category> categories;

    public CategorySpnAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View rowView = view;
        ViewHolder holder = null;

//        if (rowView == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            rowView = inflater.inflate(R.layout.category_spinner_item, viewGroup, false);
            holder = new ViewHolder();
            holder.textView = rowView.findViewById(R.id.textView);

//        }else {
//            holder = (ViewHolder) rowView.getTag();
//        }

        if(categories.size()>i){
            holder.textView.setText(categories.get(i).getName());
        }

        return rowView;
    }

    class ViewHolder{
        TextView textView;
    }
}

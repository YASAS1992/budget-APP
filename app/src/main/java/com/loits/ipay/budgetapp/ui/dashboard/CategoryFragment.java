package com.loits.ipay.budgetapp.ui.dashboard;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.loits.ipay.budgetapp.MainActivity;
import com.loits.ipay.budgetapp.R;
import com.loits.ipay.budgetapp.models.Category;
import com.loits.ipay.budgetapp.models.Expense;
import com.loits.ipay.budgetapp.models.ExpenseTrackerApp;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {

    MainActivity mainActivity;
    ListView lvCategory;
    LinearLayout lytBottom,lytEdit,lytClose,lytEditCategory;
    Category selectedCategory;
    EditText etCatName,etCatProp;
    TextView etSaveCat,etCloseCat;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity)getActivity();
        View v = inflater.inflate(R.layout.fragment_category, container, false);
        lvCategory = v.findViewById(R.id.lvCategory);
        lytBottom = v.findViewById(R.id.lytBottom);
        lytEdit = v.findViewById(R.id.lytEdit);
        lytClose = v.findViewById(R.id.lytClose);
        lytEditCategory = v.findViewById(R.id.lytEditCategory);
        etCatName = v.findViewById(R.id.etCatName);
        etCatProp = v.findViewById(R.id.etCatProp);
        etSaveCat = v.findViewById(R.id.etSaveCat);
        etCloseCat = v.findViewById(R.id.etCloseCat);
        loadList();


        lytEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lytEditCategory.setVisibility(View.VISIBLE);
            }
        });

        lytClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lytBottom.setVisibility(View.GONE);
            }
        });

        etSaveCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etCatName.getText().toString().isEmpty()){
                    etCatName.requestFocus();
                    etCatName.setError("Please enter category name.");
                }else if(etCatProp.getText().toString().isEmpty() || Double.parseDouble(etCatProp.getText().toString()) == 0.0){
                    etCatProp.requestFocus();
                    etCatProp.setError("Please enter amount;");
                }else{
                    Category category = new Category(mainActivity.trackerApp.getCategories().size(),
                            etCatName.getText().toString(),
                            Double.parseDouble(etCatProp.getText().toString()));

                    category.setId(selectedCategory.getId());

                    mainActivity.trackerApp.updateCategory(selectedCategory.getId(),category);
                    lytEditCategory.setVisibility(View.GONE);
                    lvCategory.setAdapter(null);
                    loadList();
                }
            }
        });

        etCloseCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lytEditCategory.setVisibility(View.GONE);
            }
        });

        return v;
    }

    public void loadList(){
        CategoryListAdapter adapter = new CategoryListAdapter(getContext(),R.layout.history_list_item,mainActivity.trackerApp.getCategories(),mainActivity.trackerApp);
        lvCategory.setAdapter(adapter);
    }

    class CategoryListAdapter extends ArrayAdapter {

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
        public View getView(final int position, View convertView, ViewGroup parent) {
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


            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lytBottom.setVisibility(View.VISIBLE);
                    selectedCategory = categories.get(position);
                }
            });

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

}
package com.loits.ipay.budgetapp.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.loits.ipay.budgetapp.Adapters.CategoryListAdapter;
import com.loits.ipay.budgetapp.Adapters.HistoryListAdapter;
import com.loits.ipay.budgetapp.MainActivity;
import com.loits.ipay.budgetapp.R;

public class CategoryFragment extends Fragment {

    MainActivity mainActivity;
    ListView lvCategory;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity)getActivity();
        View v = inflater.inflate(R.layout.fragment_category, container, false);
        lvCategory = v.findViewById(R.id.lvCategory);
        loadList();
        return v;
    }

    public void loadList(){
        CategoryListAdapter adapter = new CategoryListAdapter(getContext(),R.layout.history_list_item,mainActivity.trackerApp.getCategories(),mainActivity.trackerApp);
        lvCategory.setAdapter(adapter);
    }
}
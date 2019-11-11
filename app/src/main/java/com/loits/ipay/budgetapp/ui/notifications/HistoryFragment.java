package com.loits.ipay.budgetapp.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.loits.ipay.budgetapp.Adapters.HistoryListAdapter;
import com.loits.ipay.budgetapp.MainActivity;
import com.loits.ipay.budgetapp.R;

public class HistoryFragment extends Fragment {

    MainActivity mainActivity;

    ListView lvHistory;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity)getActivity();
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        lvHistory = v.findViewById(R.id.lvHistory);

        loadList();

        return v;
    }

    public void loadList(){
        HistoryListAdapter adapter = new HistoryListAdapter(getContext(),R.layout.history_list_item,mainActivity.trackerApp.getTransactions());
        lvHistory.setAdapter(adapter);
    }
}
package com.loits.ipay.budgetapp.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.loits.ipay.budgetapp.MainActivity;
import com.loits.ipay.budgetapp.R;

public class DashboardFragment extends Fragment {

    MainActivity mainActivity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity)getActivity();
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }
}
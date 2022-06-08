package com.alexllanas.jefitproject.features.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alexllanas.jefitproject.R;
import com.alexllanas.jefitproject.ui.MainActivity;

public class BusinessDetailFragment extends Fragment {

    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mainActivity = ((MainActivity) requireActivity());

        return inflater.inflate(R.layout.fragment_business_detail, container, false);
    }

    private void configureToolbar() {
        // Use SafeArgs to pass City name when navigating to this fragment and set name in title.
        mainActivity.setToolbarTitle(R.string.temp_string);
        mainActivity.setBackNavigationIcon(true);
    }
}

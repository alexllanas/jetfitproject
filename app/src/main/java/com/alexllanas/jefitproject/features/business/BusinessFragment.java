package com.alexllanas.jefitproject.features.business;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alexllanas.jefitproject.R;
import com.alexllanas.jefitproject.databinding.FragmentBusinessBinding;
import com.alexllanas.jefitproject.ui.MainActivity;

public class BusinessFragment extends Fragment {

    private FragmentBusinessBinding binding;
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBusinessBinding.inflate(inflater, container, false);
        mainActivity = ((MainActivity) requireActivity());

        String cityName = BusinessFragmentArgs.fromBundle(getArguments()).getCityName();
        configureToolbar(cityName);

        return binding.getRoot();
    }

    private void configureToolbar(String cityName) {
        // Use SafeArgs to pass City name when navigating to this fragment and set name in title.

        mainActivity.setToolbarTitle(getResources().getString(R.string.business_list_title, cityName));
        mainActivity.setBackNavigationIcon(true);
    }


}

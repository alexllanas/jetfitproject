package com.alexllanas.jefitproject.features.business;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alexllanas.jefitproject.R;
import com.alexllanas.jefitproject.databinding.FragmentBusinessBinding;
import com.alexllanas.jefitproject.ui.MainActivity;
import com.alexllanas.jefitproject.ui.MainState;
import com.alexllanas.jefitproject.ui.MainViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BusinessFragment extends Fragment {

    private FragmentBusinessBinding binding;
    private BusinessAdapter businessAdapter;
    private MainActivity mainActivity;
    private MainViewModel mainViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBusinessBinding.inflate(inflater, container, false);
        mainActivity = ((MainActivity) requireActivity());
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        String cityName = BusinessFragmentArgs.fromBundle(getArguments()).getCityName();
        configureToolbar(cityName);

        businessAdapter = new BusinessAdapter();
        binding.recyclerViewBusiness.setAdapter(businessAdapter);
        binding.recyclerViewBusiness.setLayoutManager(new LinearLayoutManager(mainActivity));
        mainViewModel.mainState.observe(mainActivity, mainState -> {
            businessAdapter.submitList(mainState.businessList);
        });


        return binding.getRoot();
    }

    private void configureToolbar(String cityName) {
        // Use SafeArgs to pass City name when navigating to this fragment and set name in title.

        mainActivity.setToolbarTitle(getResources().getString(R.string.business_list_title, cityName));
        mainActivity.setBackNavigationIcon(true);
    }


}

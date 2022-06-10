package com.alexllanas.jefitproject.features.business;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alexllanas.jefitproject.R;
import com.alexllanas.jefitproject.databinding.FragmentBusinessBinding;
import com.alexllanas.jefitproject.features.city.HomeFragmentDirections;
import com.alexllanas.jefitproject.ui.MainActivity;
import com.alexllanas.jefitproject.ui.MainViewModel;
import com.alexllanas.jefitproject.util.NetworkHelper;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BusinessFragment extends Fragment implements BusinessClickListener {

    private FragmentBusinessBinding binding;
    private BusinessAdapter businessAdapter;
    private MainActivity mainActivity;
    private MainViewModel mainViewModel;
    private String cityName;
    private NetworkHelper networkHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBusinessBinding.inflate(inflater, container, false);
        mainActivity = ((MainActivity) requireActivity());
        mainViewModel = new ViewModelProvider(mainActivity).get(MainViewModel.class);

        setupUI();
        initRecyclerView();
        subscribeObservers();

        return binding.getRoot();
    }

    private void setupUI() {
        cityName = BusinessFragmentArgs.fromBundle(getArguments()).getCityName();
        mainViewModel.getBusinesses(cityName);
        configureToolbar(cityName);
        networkHelper = new NetworkHelper(getActivity());
    }

    private void initRecyclerView() {
        businessAdapter = new BusinessAdapter(this);
        binding.recyclerViewBusiness.setAdapter(businessAdapter);
        binding.recyclerViewBusiness.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void subscribeObservers() {
        mainViewModel.businessList().observe(getViewLifecycleOwner(), businessList -> {
            businessAdapter.submitList(businessList);
        });
        mainViewModel.isLoading().observe(mainActivity, isLoading -> {
            if (isLoading) {
                binding.recyclerViewBusiness.setVisibility(View.GONE);
            } else {
                binding.recyclerViewBusiness.setVisibility(View.VISIBLE);
            }
        });
    }

    private void configureToolbar(String cityName) {
        mainActivity.setToolbarTitle(getResources().getString(R.string.business_list_title, cityName));
        mainActivity.setBackNavigationIcon(true);
    }


    @Override
    public void onBusinessClicked(int position) {
        Navigation
                .findNavController(binding.getRoot())
                .navigate(
                        BusinessFragmentDirections.actionBusinessFragmentToBusinessDetailFragment());
    }

    @Override
    public void onLikeButtonClicked(int position) {
        Business business = businessAdapter.getBusiness(position);
        mainViewModel.likeBusiness(business);
        mainViewModel.getBusinesses(cityName);
    }
}

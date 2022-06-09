package com.alexllanas.jefitproject.features.city;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alexllanas.jefitproject.R;
import com.alexllanas.jefitproject.databinding.FragmentHomeBinding;
import com.alexllanas.jefitproject.ui.MainActivity;
import com.alexllanas.jefitproject.ui.MainViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment implements CityClickListener {

    private CityAdapter cityAdapter;
    private FragmentHomeBinding binding;
    private MainActivity mainActivity;
    private MainViewModel mainViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        mainActivity = ((MainActivity) requireActivity());
        mainViewModel = new ViewModelProvider(mainActivity).get(MainViewModel.class);
//        mainViewModel.getCities();

        configureToolbar();
        initRecyclerView();

        return binding.getRoot();
    }

    private void configureToolbar() {
        mainActivity.setToolbarTitle(getString(R.string.title_cities));
        mainActivity.setBackNavigationIcon(false);
    }

    private void initRecyclerView() {
        cityAdapter = new CityAdapter(this);

        mainViewModel.cityList().observe(getViewLifecycleOwner(), cityList -> {
            cityAdapter.submitList(cityList);
        });

        mainViewModel.isLoading().observe(mainActivity, isLoading -> {
            if (isLoading) {
                binding.recyclerViewCity.setVisibility(View.GONE);
            } else {
                binding.recyclerViewCity.setVisibility(View.VISIBLE);
            }
        });


        binding.recyclerViewCity.setAdapter(cityAdapter);
        binding.recyclerViewCity.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    @Override
    public void onCityClicked(int position) {
        String cityName = cityAdapter.getCity(position).getName();
        Navigation
                .findNavController(binding.getRoot())
                .navigate(
                        HomeFragmentDirections.actionHomeFragmentToBusinessFragment(cityName)
                );
    }
}

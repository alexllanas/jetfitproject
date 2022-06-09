package com.alexllanas.jefitproject.features.city;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alexllanas.jefitproject.R;
import com.alexllanas.jefitproject.databinding.FragmentHomeBinding;
import com.alexllanas.jefitproject.ui.MainActivity;
import com.alexllanas.jefitproject.ui.MainState;
import com.alexllanas.jefitproject.ui.MainViewModel;
import com.alexllanas.jefitproject.util.StaticData;

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
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getCities();

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

        mainViewModel.mainState.observe(mainActivity, mainState -> {
            cityAdapter.submitList(mainState.cityList);
        });

        binding.recyclerViewCity.setAdapter(cityAdapter);
        binding.recyclerViewCity.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    @Override
    public void onCityClicked(int position) {
        String cityName = cityAdapter.getCity(position).getName();
        mainViewModel.getBusinesses(cityName);
        Navigation
                .findNavController(binding.getRoot())
                .navigate(
                        HomeFragmentDirections.actionHomeFragmentToBusinessFragment(cityName)
                );
    }
}

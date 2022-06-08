package com.alexllanas.jefitproject.features.city;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alexllanas.jefitproject.databinding.FragmentHomeBinding;
import com.alexllanas.jefitproject.util.StaticData;

public class HomeFragment extends Fragment {

    private CityAdapter cityAdapter;

    private FragmentHomeBinding homeBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false);

        initRecyclerView();

        return homeBinding.getRoot();
    }

    private void initRecyclerView() {
        cityAdapter = new CityAdapter();
        cityAdapter.submitList(StaticData.CITY_NAMES);
        homeBinding.recyclerViewCity.setAdapter(cityAdapter);
        homeBinding.recyclerViewCity.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}

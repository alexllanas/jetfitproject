package com.alexllanas.jefitproject.features.detail;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alexllanas.jefitproject.R;
import com.alexllanas.jefitproject.databinding.FragmentBusinessDetailBinding;
import com.alexllanas.jefitproject.features.business.Business;
import com.alexllanas.jefitproject.features.business.Location;
import com.alexllanas.jefitproject.ui.MainActivity;
import com.alexllanas.jefitproject.ui.MainViewModel;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import dagger.hilt.android.AndroidEntryPoint;

@RequiresApi(api = Build.VERSION_CODES.N)
@AndroidEntryPoint
public class BusinessDetailFragment extends Fragment {

    private FragmentBusinessDetailBinding binding;
    private MainViewModel mainViewModel;
    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBusinessDetailBinding.inflate(inflater, container, false);
        mainActivity = ((MainActivity) requireActivity());
        mainViewModel = new ViewModelProvider(mainActivity).get(MainViewModel.class);
        String businessId = BusinessDetailFragmentArgs.fromBundle(getArguments()).getBusinessId();
        mainViewModel.getBusiness(businessId);
        subscribeObservers();

        return binding.getRoot();
    }

    private void subscribeObservers() {
        mainViewModel.business().observe(mainActivity, this::setupUI);
    }

    private void setupUI(Business business) {
        configureToolbar();
        setupHeader(business);
        setupDetails(business);
    }

    private void setupDetails(Business business) {
        binding.detailsSection.textRating.setText(getString(R.string.rating, business.rating));
        binding.detailsSection.textCategory.setText(getCategoryText(business.categories));
        binding.detailsSection.textAddress.setText(getAddressText(business.location));
    }

    private String getCategoryText(ArrayList<Map<String, String>> categories) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int index = 0; index < categories.size() - 2; index++) {
            String category = categories.get(index).get("title") + ", ";
            stringBuilder.append(category);
        }
        String lastCategory = categories.get(categories.size() - 1).get("title");
        stringBuilder.append(lastCategory);
        return stringBuilder.toString();
    }

    private String getAddressText(Location location) {
        return String.format("%s\n%s, %s %s", location.address, location.city, location.state, location.zipCode);
    }

    private void setupHeader(Business business) {
        binding.headerSection.headerBusinessName.setText(business.getName());
        binding.headerSection.headerButtonLike.setOnClickListener(v -> {
            mainViewModel.likeBusiness(business);
            mainViewModel.getBusiness(business.businessId);
        });
        if (business.isLiked) {
            binding.headerSection.headerButtonLike.setImageResource(R.drawable.ic_favorite_filled_red);
        } else {
            binding.headerSection.headerButtonLike.setImageResource(R.drawable.ic_favorite_outlined_white);
        }
        setHeaderImage(business);
    }

    private void setHeaderImage(Business business) {
        Uri uri = Uri.parse(business.imageUrl);
        SimpleDraweeView draweeView = (SimpleDraweeView) binding.headerSection.imageBusinessHeader;
        draweeView.setImageURI(uri);
    }

    private void configureToolbar() {
        // Use SafeArgs to pass City name when navigating to this fragment and set name in title.
        mainActivity.setToolbarTitle(null);
        mainActivity.setBackNavigationIcon(true);
    }
}

package com.alexllanas.jefitproject.features.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexllanas.jefitproject.BuildConfig;
import com.alexllanas.jefitproject.R;
import com.alexllanas.jefitproject.databinding.FragmentBusinessDetailBinding;
import com.alexllanas.jefitproject.features.business.Business;
import com.alexllanas.jefitproject.features.business.Location;
import com.alexllanas.jefitproject.ui.MainActivity;
import com.alexllanas.jefitproject.ui.MainViewModel;
import com.facebook.common.logging.LoggingDelegate;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Map;

import dagger.hilt.android.AndroidEntryPoint;

@RequiresApi(api = Build.VERSION_CODES.N)
@AndroidEntryPoint
public class BusinessDetailFragment extends Fragment {

    private FragmentBusinessDetailBinding binding;
    private MainViewModel mainViewModel;
    private MainActivity mainActivity;
    private ReviewAdapter reviewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBusinessDetailBinding.inflate(inflater, container, false);
        mainActivity = ((MainActivity) requireActivity());
        mainViewModel = new ViewModelProvider(mainActivity).get(MainViewModel.class);
        initRecyclerView();
        getData();
        subscribeObservers();

        return binding.getRoot();
    }

    private void initRecyclerView() {
        reviewAdapter = new ReviewAdapter();
        binding.reviewsSection.recyclerViewCity.setAdapter(reviewAdapter);
        binding.reviewsSection.recyclerViewCity.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void getData() {
        String businessId = BusinessDetailFragmentArgs.fromBundle(getArguments()).getBusinessId();
        mainViewModel.getBusinessDetails(businessId);
    }

    private void subscribeObservers() {
        mainViewModel.business().observe(mainActivity, this::setupUI);
        mainViewModel.isLoading().observe(mainActivity, isLoading -> {
            if (isLoading) {
//                binding.recyclerViewBusiness.setVisibility(View.GONE);
                binding.getRoot().setAlpha(0.2f);
            } else {
//                binding.recyclerViewBusiness.setVisibility(View.VISIBLE);
                binding.getRoot().setAlpha(1f);
//                binding.recyclerViewBusiness.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupUI(Business business) {
        configureToolbar();
        setupHeader(business);
        setupDetails(business);
    }

    private void setupDetails(Business business) {
//        if (business.reviews != null) {
        reviewAdapter.submitList(business.reviews);
//        }
        binding.detailsSection.textRating.setText(getString(R.string.rating, business.rating));
        binding.detailsSection.textCategory.setText(getCategoryText(business.categories));
        binding.detailsSection.textAddress.setText(getAddressText(business.location));
        binding.detailsSection.textAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGoogleMapsIntent(business);
            }
        });
    }

    private void createGoogleMapsIntent(Business business) {
        Uri gmmIntentUri = Uri.parse(String.format("http://maps.google.com/maps?q=loc:%s,%s (%s)", business.coordinates.latitude, business.coordinates.longitude, business.name));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
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
        mainActivity.setToolbarTitle(null);
        mainActivity.setBackNavigationIcon(true);
    }
}

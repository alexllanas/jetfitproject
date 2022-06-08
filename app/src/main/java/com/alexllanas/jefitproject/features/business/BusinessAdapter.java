package com.alexllanas.jefitproject.features.business;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexllanas.jefitproject.R;

import java.util.ArrayList;

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.BusinessViewHolder> {

    private ArrayList<Business> businesses = new ArrayList<>();

    @NonNull
    @Override
    public BusinessAdapter.BusinessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_business, parent, false);
        return new BusinessAdapter.BusinessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessAdapter.BusinessViewHolder holder, int position) {
        Business business = businesses.get(position);
        bind(holder, business);
    }

    @Override
    public int getItemCount() {
        return businesses.size();
    }

    public void submitList(ArrayList<Business> items) {
        businesses = items;
    }

    private void bind(BusinessAdapter.BusinessViewHolder holder, Business business) {
        holder.businessNameTextView.setText(business.getName());
        fillIcon(business.isLiked(), holder);
    }

    private void fillIcon(boolean fill, BusinessAdapter.BusinessViewHolder holder) {
        if (fill) {
            holder.likeButton.setImageResource(R.drawable.ic_favorite_filled_red);
        } else {
            holder.likeButton.setImageResource(R.drawable.ic_favorite_outlined);
        }
    }

    public class BusinessViewHolder extends RecyclerView.ViewHolder {

        public TextView businessNameTextView;
        public ImageView likeButton;

        public BusinessViewHolder(@NonNull View itemView) {
            super(itemView);
            businessNameTextView = (TextView) itemView.findViewById(R.id.business_name);
            likeButton = (ImageView) itemView.findViewById(R.id.button_like);
        }
    }
}

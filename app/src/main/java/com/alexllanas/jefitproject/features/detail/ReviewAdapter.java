package com.alexllanas.jefitproject.features.detail;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexllanas.jefitproject.R;
import com.alexllanas.jefitproject.features.city.City;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private ArrayList<Review> reviews = new ArrayList<>();

    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_review, parent, false);
        return new ReviewAdapter.ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.excerptTextView.setText(review.content);
        if (review.user != null) {
            holder.userNameTextView.setText(String.format("- %s", review.user.name));
        }
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void submitList(ArrayList<Review> items) {
        reviews = items;
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        public TextView excerptTextView;
        public TextView userNameTextView;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            excerptTextView = (TextView) itemView.findViewById(R.id.excerpt_text);
            userNameTextView = (TextView) itemView.findViewById(R.id.user_name);
        }

    }
}

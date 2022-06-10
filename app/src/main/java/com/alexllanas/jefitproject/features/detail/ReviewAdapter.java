package com.alexllanas.jefitproject.features.detail;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;

import com.alexllanas.jefitproject.R;
import com.alexllanas.jefitproject.features.business.Business;
import com.alexllanas.jefitproject.features.business.BusinessAdapter;
import com.alexllanas.jefitproject.features.city.City;

import java.util.ArrayList;
import java.util.Objects;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder holder, int position) {
        Review review = differ.getCurrentList().get(position);
        holder.excerptTextView.setText(review.content);
        if (review.user != null) {
            holder.userNameTextView.setText(String.format("- %s", review.user.name));
        }
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void submitList(ArrayList<Review> items) {
        differ.submitList(items);
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {

        public TextView excerptTextView;
        public TextView userNameTextView;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            excerptTextView = (TextView) itemView.findViewById(R.id.excerpt_text);
            userNameTextView = (TextView) itemView.findViewById(R.id.user_name);
        }
    }

    DiffUtil.ItemCallback<Review> DIFF_CALLBACK = new DiffUtil.ItemCallback<Review>() {
        @Override
        public boolean areItemsTheSame(@NonNull Review oldItem, @NonNull Review newItem) {
            return Objects.equals(oldItem.id, newItem.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Review oldItem, @NonNull Review newItem) {
            return oldItem.equals(newItem);
        }
    };

    private final AsyncListDiffer<Review> differ =
            new AsyncListDiffer<>(
                    new ReviewAdapter.ReviewRecyclerChangeCallback(this),
                    new AsyncDifferConfig.Builder<>(DIFF_CALLBACK).build()
            );

    static class ReviewRecyclerChangeCallback implements ListUpdateCallback {
        private ReviewAdapter adapter;

        ReviewRecyclerChangeCallback(ReviewAdapter adapter) {
            this.adapter = adapter;
        }


        @Override
        public void onInserted(int position, int count) {
            adapter.notifyItemRangeChanged(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            adapter.notifyDataSetChanged();

        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            adapter.notifyDataSetChanged();

        }

        @Override
        public void onChanged(int position, int count, @Nullable Object payload) {
            adapter.notifyItemRangeChanged(position, count, payload);
        }
    }
}

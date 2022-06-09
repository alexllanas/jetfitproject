package com.alexllanas.jefitproject.features.business;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;

import com.alexllanas.jefitproject.R;

import java.util.ArrayList;
import java.util.Collections;

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.BusinessViewHolder> {

    private final ArrayList<Business> businesses = new ArrayList<>();

    @NonNull
    @Override
    public BusinessAdapter.BusinessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_business, parent, false);
        return new BusinessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessAdapter.BusinessViewHolder holder, int position) {
        Business business = differ.getCurrentList().get(position);
        bind(holder, business);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void submitList(ArrayList<Business> items) {
        differ.submitList(items == null ? Collections.emptyList() : items);
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

    public static class BusinessViewHolder extends RecyclerView.ViewHolder {

        public TextView businessNameTextView;
        public ImageView likeButton;

        public BusinessViewHolder(@NonNull View itemView) {
            super(itemView);
            businessNameTextView = (TextView) itemView.findViewById(R.id.business_name);
            likeButton = (ImageView) itemView.findViewById(R.id.button_like);
        }
    }

    /**
     * Diff Util
     */
    DiffUtil.ItemCallback<Business> DIFF_CALLBACK = new DiffUtil.ItemCallback<Business>() {
        @Override
        public boolean areItemsTheSame(@NonNull Business oldItem, @NonNull Business newItem) {
            return oldItem.businessId == newItem.businessId;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Business oldItem, @NonNull Business newItem) {
            return oldItem.equals(newItem);
        }
    };

    private final AsyncListDiffer<Business> differ =
            new AsyncListDiffer<>(
                    new BusinessRecyclerChangeCallback(this),
                    new AsyncDifferConfig.Builder<>(DIFF_CALLBACK).build()
            );

    static class BusinessRecyclerChangeCallback implements ListUpdateCallback {
        private BusinessAdapter adapter;

        BusinessRecyclerChangeCallback(BusinessAdapter adapter) {
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

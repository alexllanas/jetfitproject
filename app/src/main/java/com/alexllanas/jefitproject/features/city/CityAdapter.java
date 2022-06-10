package com.alexllanas.jefitproject.features.city;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexllanas.jefitproject.R;

import java.util.ArrayList;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private ArrayList<City> cities = new ArrayList<>();

    private final CityClickListener cityClickListener;

    public CityAdapter(CityClickListener cityClickListener) {
        this.cityClickListener = cityClickListener;
    }

    @NonNull
    @Override
    public CityAdapter.CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_city, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapter.CityViewHolder holder, int position) {
        String cityName = cities.get(position).getName();
        holder.cityNameTextView.setText(cityName);
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public void submitList(ArrayList<City> items) {
        cities = items;
    }

    public City getCity(int position) {
        if (cities.isEmpty()) {
            return null;
        }
        return cities.get(position);
    }

    public class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView cityNameTextView;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            cityNameTextView = (TextView) itemView.findViewById(R.id.city_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            cityClickListener.onCityClicked(getAdapterPosition());
        }
    }
}

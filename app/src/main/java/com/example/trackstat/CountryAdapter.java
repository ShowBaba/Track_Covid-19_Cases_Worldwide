package com.example.trackstat;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder>{

    //define a constructor that takes an array list of countries as an arg
    //also define an array list of countries to use through out the class
    ArrayList<Countries> countries;
    public CountryAdapter(ArrayList<Countries> countries){
        this.countries = countries;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        this method will inflate the layout, country_list_item
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).
                inflate(R.layout.country_list_item, parent, false);
        return new CountryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        //in this method get data and the current position and bind it to the holder
        Countries country = countries.get(position);
        holder.bind(country);
        if(position %2 == 1)
        {
//            holder.lyt_content_view.setBackgroundColor(Color.WHITE);
            holder.tvRecovered.setBackgroundColor(Color.WHITE);
            holder.tvCountryName.setBackgroundColor(Color.WHITE);
            holder.tvDeaths.setBackgroundColor(Color.WHITE);
            holder.tvTotalCases.setBackgroundColor(Color.WHITE);
        }
        else
        {
            holder.tvRecovered.setBackgroundColor(Color.argb(0, 121, 121, 121));
            holder.tvCountryName.setBackgroundColor(Color.argb(0, 121, 191, 121));
            holder.tvDeaths.setBackgroundColor(Color.argb(0, 121, 191, 121));
            holder.tvTotalCases.setBackgroundColor(Color.argb(0, 121, 121, 121));
        }

    }

    @Override
    public int getItemCount() {
        //here return the size of the countries array
        return countries.size();
    }

    //create view holder
    public class CountryViewHolder extends RecyclerView.ViewHolder{

        //declare text views as member of the class
        TextView tvCountryName;
        TextView tvTotalCases;
        TextView tvDeaths;
        TextView tvRecovered;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            //call the findViewById here
            tvCountryName = itemView.findViewById(R.id.country_name);
            tvTotalCases = itemView.findViewById(R.id.total_cases);
            tvDeaths = itemView.findViewById(R.id.deaths);
            tvRecovered = itemView.findViewById(R.id.recovered);
        }

        //create a method to bind data to the text views
        public void bind(Countries countries){
            tvCountryName.setText(countries.countryName);
            tvTotalCases.setText(countries.totalCases);
            tvDeaths.setText(countries.deaths);
            tvRecovered.setText(countries.recovered);

        }
    }
}

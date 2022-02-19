package com.example.loginsignupapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

public class AdapterCar extends RecyclerView.Adapter<AdapterCar.ViewHolder> {

    private List<Car> mData;
    private LayoutInflater mInflater;
    private Context context;


    private final AdapterCar.ItemClickListener mClickListener = new ItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            // get restaurant data
            Car car = mData.get(position);
            // upload restaurant data
            // goto details activity
            Intent i = new Intent(context, CarDetailsActivity.class);
            i.putExtra("car", (Serializable)car);
            context.startActivity(i);
        }
    };


    // data is passed into the constructor
    AdapterCar(Context context, List<Car> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;

    }



    // inflates the row layout from xml when needed
    @Override
    public AdapterCar.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_car, parent, false);
        return new AdapterCar.ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(AdapterCar.ViewHolder holder, int position) {
       Car car = mData.get(position);
        holder.tvName.setText(car.getName());
        //holder.ivPhoto.setImageDrawable(rest.getPhoto());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;
        ImageView ivPhoto;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvNameCarRow);
            ivPhoto = itemView.findViewById(R.id.ivPhotoCarRow);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

            //Open new page: Intent i = new Intent(, SignupActivity.class);



        }
    }

    // convenience method for getting data at click position
    Car getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    /*
    void setClickListener(AdapterRestaurant.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }*/

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

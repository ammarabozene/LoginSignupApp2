package com.example.loginsignupapp;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;


public class AdapterCar extends RecyclerView.Adapter<AdapterCar.ViewHolder> {

    private List<Car> mData;
    private LayoutInflater mInflater;
    private Context context;
    private FirebaseServices fbs;

    private final AdapterCar.ItemClickListener mClickListener = new ItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Car car = mData.get(position);
            Intent i = new Intent(context, CarDetailsActivity.class);
            i.putExtra("car", car);

            context.startActivity(i);
        }
    };

    // data is passed into the constructor
    AdapterCar(Context context, List<Car> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
        this.fbs = FirebaseServices.getInstance();
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
        fbs.getStorage().getReference().child(car.getPhoto()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(car.getPhoto()).into(holder.ivPhoto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, e.getMessage());
            }
        });

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrol    led off screen
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
        }
    }

    // convenience method for getting data at click position
    Car getItem(int id) {
        return mData.get(id);
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

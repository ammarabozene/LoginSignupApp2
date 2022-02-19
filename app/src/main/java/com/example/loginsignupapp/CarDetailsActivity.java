package com.example.loginsignupapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CarDetailsActivity extends AppCompatActivity {

    private TextView tvName,tvHistory,tvDescription;
    private ImageView ivPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        connectComponents();
        Intent i = this.getIntent();
       Car car = (Car) i.getSerializableExtra("car");

        tvName.setText(car.getName());
        tvDescription.setText(car.getDescription());
        tvHistory.setText(car.getHistory());
        Picasso.get().load(car.getPhoto()).into(ivPhoto);
    }

    private void connectComponents() {
        tvName = findViewById(R.id.tvNameCarDetails);
        tvDescription = findViewById(R.id.tvDescriptionCarDetails);
        tvHistory= findViewById(R.id.tvHistoryCarDetails);
        ivPhoto = findViewById(R.id.ivPhotoCarDetails);
    }
}


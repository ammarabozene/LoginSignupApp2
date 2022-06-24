package com.example.loginsignupapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CarDetailsActivity extends AppCompatActivity {

    private TextView tvName, tvHistory, tvDescription, tvCategory;
    private ImageView ivPhoto;

    /*
        private String address;
    private RestCat category;
    private String photo;
    private String phone;
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        connectComponents();
        Intent i = this.getIntent();
        Car car = (Car) i.getSerializableExtra("car");

        tvName.setText(car.getName());
        tvHistory.setText(car.getHistory());
        tvDescription.setText(car.getDescription());
        tvCategory.setText(car.getCategory().toString());
        Picasso.get().load(car.getPhoto()).into(ivPhoto);
    }

    private void connectComponents() {
        tvName = findViewById(R.id.tvNameCarDetails);
        tvHistory = findViewById(R.id.tvHistoryCarDetails);
        tvDescription = findViewById(R.id.tvDescriptionCarDetails);
        tvCategory = findViewById(R.id.tvCategoryCarDetails);
        ivPhoto = findViewById(R.id.ivPhotoCarDetails);
    }
}
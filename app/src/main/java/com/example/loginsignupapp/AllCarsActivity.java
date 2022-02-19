package com.example.loginsignupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class AllCarsActivity extends AppCompatActivity {

        private RecyclerView rvAllCar;
        AdapterCar adapter;
        FirebaseServices fbs;
        ArrayList<Car> cars;
        MyCallback myCallback;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_all_car);

            fbs = FirebaseServices.getInstance();
           cars = new ArrayList<Car>();
            readData();
            myCallback = new MyCallback() {
                @Override
                public void onCallback(List<Car> restsList) {
                    RecyclerView recyclerView = findViewById(R.id.rvAllCar);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter = new AdapterCar(getApplicationContext(), cars);
                    recyclerView.setAdapter(adapter);
                }
            };


            // set up the RecyclerView
            RecyclerView recyclerView = findViewById(R.id.rvAllCar);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new AdapterCar(this,cars);
            recyclerView.setAdapter(adapter);
        }

        private void readData() {
            fbs. getFirestore().collection("cars")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    cars.add(document.toObject(Car.class));
                                }
                                myCallback.onCallback(cars);
                            } else {
                                Log.e("AllCarsActivity: readData()", "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
    }


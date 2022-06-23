package com.example.loginsignupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class AllCarActivity extends AppCompatActivity {

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
            ActionBar actionBar = getSupportActionBar();

            actionBar.setTitle("CARFORMATION");
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //case R.id.miSearch:
            // User chose the "Settings" item, show the app settings UI...
            //return true;

            case R.id.miProfile:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            case R.id.miSettings:

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);


        }


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


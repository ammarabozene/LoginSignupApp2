package com.example.loginsignupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.io.IOException;

import static android.content.ContentValues.TAG;


public class AddNewCar extends AppCompatActivity {
    private EditText etname, ethistory, etdescription;
    private Spinner spAddNewCar;
    private ImageView IvPhoto;
    private FirebaseServices fbs;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_car);

        connectComponents();
    }


    private void connectComponents() {
        etname = findViewById(R.id.etNameAddNewCar);
        ethistory = findViewById(R.id.etHistoryAddNewCar);
        etdescription = findViewById(R.id.etDescriptionAddNewCar);
        IvPhoto = findViewById(R.id.IvPhotoAddNewCar);
        spAddNewCar = findViewById(R.id.spinnerAddNewCar);
        fbs = FirebaseServices.getInstance();
        spAddNewCar.setAdapter(new ArrayAdapter<CarCategory>(this, android.R.layout.simple_selectable_list_item, CarCategory.values()));
    }

    public void add(View view) {
        // check if any field is empty
        String name,history,description, category, photo;
        name = etname.getText().toString();
        history = ethistory.getText().toString();
        description = etdescription.getText().toString();
        category = spAddNewCar.getSelectedItem().toString();
        if (IvPhoto.getDrawable() == null)
            photo = "no_image";
        else photo = IvPhoto.getDrawable().toString();

        if (name.trim().isEmpty()||history.trim().isEmpty() || description.trim().isEmpty() || category.trim().isEmpty() || photo.trim().isEmpty()) {
            Toast.makeText(this,"error : fileds are empty", Toast.LENGTH_SHORT).show();
            return;
        }
        Car car = new Car(Integer.parseInt(history), Integer.parseInt(description), Integer.parseInt(name),Integer.parseInt(photo), CarCategory.valueOf(category));
        fbs.getFire().collection("cars")
                .add(car)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void selectPhoto(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //startActivityForResult(Intent.createChooser(intent, "Select Picture"),40);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 40) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)  {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
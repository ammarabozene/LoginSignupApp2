package com.example.loginsignupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import static android.content.ContentValues.TAG;

public class AddNewCar extends AppCompatActivity {
    private EditText etchasis ,etColor, etEngine, etSize, etReleaseyear;
    private Spinner spAddNewCar;
    private ImageView IvPhoto;
    private FirebaseServices fbs;
    private Uri filePath;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_car);

        connectComonent();
    }


    private void connectComonent() {
        etchasis = findViewById(R.id.etChasisAddNewCar);
        etColor = findViewById(R.id.etColorAddNewCar);
        etEngine = findViewById(R.id.etEngineAddNewCar);
        etReleaseyear = findViewById(R.id.etReleaseyearAddNewCar);
        etSize = findViewById(R.id.etSizeAddNewCar);
        IvPhoto = findViewById(R.id.IvPhotoAddNewCar);
        spAddNewCar = findViewById(R.id.spinnerAddNewCar);
        fbs = FirebaseServices.getInstance();
        spAddNewCar.setAdapter(new ArrayAdapter<CarCategory>(this, android.R.layout.simple_selectable_list_item, CarCategory.values()));
        storageReference = fbs.getStorage().getReference();
    }

    public void add(View view) {
        // check if any field is empty
        String chasis ,color, engine, releaseyear, size, category, photo;
        chasis =etchasis.getText().toString();
        color = etColor.getText().toString();
        engine = etEngine.getText().toString();
        releaseyear = etReleaseyear.getText().toString();
        size = etSize.getText().toString();
        category = spAddNewCar.getSelectedItem().toString();
        if (IvPhoto.getDrawable() == null)
            photo = "no_image";
        else photo = IvPhoto.getDrawable().toString();

        if (chasis.trim().isEmpty()||color.trim().isEmpty() || engine.trim().isEmpty() || releaseyear.trim().isEmpty() ||
                size.trim().isEmpty() || category.trim().isEmpty() || photo.trim().isEmpty()) {
            Toast.makeText(this,"error : fileds are empty", Toast.LENGTH_SHORT).show();
            return;
        }
        Car car = new Car(Integer.parseInt(chasis),Integer.parseInt(color), Integer.parseInt(releaseyear) ,Integer.parseInt(engine), Integer.parseInt(size) , CarCategory.valueOf(category));
        fbs.getFirestore().collection("cars")
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
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),40);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 40) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        filePath = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        IvPhoto.setBackground(null);
                        IvPhoto.setImageBitmap(bitmap);
                        uploadImage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)  {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadImage()
    {
        if (filePath != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(AddNewCar.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast
                                    .makeText(AddNewCar.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });
        }
    }
}
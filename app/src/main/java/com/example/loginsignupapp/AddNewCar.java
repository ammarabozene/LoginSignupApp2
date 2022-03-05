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
import com.squareup.picasso.Picasso;

import java.io.IOException;

import static android.content.ContentValues.TAG;


public class AddNewCar extends AppCompatActivity {
    private static final String TAG = "AddNewCarActivity";
    private EditText etname, ethistory, etdescription;
    private Spinner spAddNewCar;
    private ImageView IvPhoto;
    private FirebaseServices fbs;
    private Uri filePath;
    private StorageReference storageReference;
    private String refAfterSuccessfullUpload = null;





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
        IvPhoto = findViewById(R.id.ivPhotoAddNewCar);
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
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),40);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 40) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    filePath = data.getData();
                    IvPhoto.setBackground(null);
                    Picasso.get().load(filePath).into(IvPhoto);
                    uploadImage();
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
            String fileNameStr = filePath.toString().substring(filePath.toString().lastIndexOf("/")+1);
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + fileNameStr);

            filePath.toString().substring(filePath.toString().lastIndexOf("/")+1);
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
                                    refAfterSuccessfullUpload = ref.toString();
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
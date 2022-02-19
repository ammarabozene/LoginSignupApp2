package com.example.loginsignupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity<auth> extends AppCompatActivity {

     private  EditText etUsername, etPassword;
    private FirebaseAuth auth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etusernameMain);
        etPassword = findViewById(R.id.etPasswordMain);
    }


    public void login(View view) {

        // TODO: 1- Get data from screen

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        auth = FirebaseAuth.getInstance();

        // TODO: 2- Data validation

        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            Toast.makeText(this, "username or password is missing", Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO: 3-  Check username and password with Firebase Authentication
        auth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // TODO: commands if successful
                        } else {


                            Toast.makeText(MainActivity.this, "Username or password is empty!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
    }

    public void Signup(View view) {
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);
    }
}
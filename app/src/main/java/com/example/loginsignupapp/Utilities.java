package com.example.loginsignupapp;

import android.app.AppComponentFactory;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
enum ErrorCodes
{
        IncorrectAuth, FieldsEmpty, True, False
}
enum CarCategory
{
        Dodge,Bmw,mercedes,audi,porsche,ferrari,lamborghini,toyota,mitsubishi,nissan
}

public class Utilities {
        private static Utilities instance;

        public Utilities()
        {}

        public static Utilities getInstance()
        {
                if (instance == null)
                        instance = new Utilities();

                return instance;
        }

        public boolean validateEmail(String username)
        {
                return true;
        }

        public boolean validatePassword(String password)
        {
                return true;
        }

        public boolean checkTrimEmpty(String text)
        {
                return text.trim().isEmpty();
        }
}
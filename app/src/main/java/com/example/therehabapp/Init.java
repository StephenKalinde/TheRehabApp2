package com.example.therehabapp;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class Init extends Application {

    @Override
    public void onCreate() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        super.onCreate();
    }
}

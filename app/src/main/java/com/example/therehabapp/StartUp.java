package com.example.therehabapp;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartUp extends Application {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    @Override
    public void onCreate() {
        super.onCreate();

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        if(user != null)
        {
            startActivity(new Intent(StartUp.this, Home.class));
        }

    }

}

package com.example.therehabapp;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_SCREEN_LENGTH = 5000;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_view);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                firebaseAuth = FirebaseAuth.getInstance();
                user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    startActivity(new Intent(SplashScreen.this, Home.class));
                    finishAffinity();
                }

                else{
                    Intent mainIntent = new Intent(SplashScreen.this, WelcomeNote.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }
            }
        },SPLASH_SCREEN_LENGTH);

    }


}

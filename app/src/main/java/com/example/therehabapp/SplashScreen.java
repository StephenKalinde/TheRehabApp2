package com.example.therehabapp;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;

public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_SCREEN_LENGTH = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher_view);

       new Handler().postDelayed(new Runnable(){
           @Override
           public void run(){
               Intent mainIntent= new Intent(SplashScreen.this, WelcomeNote.class);
               SplashScreen.this.startActivity(mainIntent);
               SplashScreen.this.finish();
           }
       },SPLASH_SCREEN_LENGTH);

    }
}

package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ThankYouNote extends AppCompatActivity

{

    private Button signUpBtn, exitBtn;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.thank_you_note);

        final String diagnosis = getIntent().getStringExtra("Diagnosis");


        signUpBtn = (Button) findViewById(R.id.signUp_btn);
        exitBtn =(Button) findViewById(R.id.exit_btn) ;

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (ThankYouNote.this, ProfileSetup.class);
                intent.putExtra("Diagnosis", diagnosis);

                startActivity(intent);

            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent (ThankYouNote.this, WelcomeNote.class));
                finishAffinity();   // restart process

            }
        });

    }


}



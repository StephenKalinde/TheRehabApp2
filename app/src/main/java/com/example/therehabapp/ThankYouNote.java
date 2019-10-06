package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ThankYouNote extends AppCompatActivity {


    private Button exitBtn, continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_anxiety);

        exitBtn = (Button) findViewById(R.id.exit_btn);
        continueBtn = (Button) findViewById(R.id.continue_btn);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ThankYouNote.this, ProfileSetup.class));
            }
        });
    }


}

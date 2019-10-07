package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EatingDisorderDiagnosis extends AppCompatActivity {

    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eating_disorder_diagnosis);

        nextBtn =(Button) findViewById(R.id.continue_btn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EatingDisorderDiagnosis.this, AboutEatingDisorder.class));
            }
        });
    }

}

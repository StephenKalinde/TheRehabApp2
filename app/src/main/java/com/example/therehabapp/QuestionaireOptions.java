package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class QuestionaireOptions extends AppCompatActivity {

    private Button addictionBtn, eatingDisorderBtn, anxietyBtn, depressionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionaire_options);

        addictionBtn= (Button) findViewById(R.id.addiction);
        anxietyBtn= (Button) findViewById(R.id.anxiety);
        eatingDisorderBtn=(Button) findViewById(R.id.eating_disorder);
        depressionBtn= (Button) findViewById(R.id.depression);

        addictionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuestionaireOptions.this, AddictionQns.class));
            }
        });

        anxietyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (QuestionaireOptions.this, AnxietyQns.class));
            }
        });

        depressionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuestionaireOptions.this, DepressionQns.class));
            }
        });

        eatingDisorderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QuestionaireOptions.this, EatingDisorderQns.class));
            }
        });

    }
}

package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EatingDisorderQns2 extends AppCompatActivity {

    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eating_disorder_qns_2);

        nextBtn = (Button) findViewById(R.id.next_btn2);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EatingDisorderQns2.this, EatingDisorderQns3.class));
            }
        });

    }

}

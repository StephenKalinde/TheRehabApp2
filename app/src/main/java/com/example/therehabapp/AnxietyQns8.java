package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AnxietyQns8 extends AppCompatActivity {


    private Button continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.anxiety_qns_8);

        continueBtn= (Button) findViewById(R.id.next_btn8);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AnxietyQns8.this, AnxietyDiagnosis.class));
            }
        });



    }
}

package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AnxietyQns7 extends AppCompatActivity {

    public Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anxiety_qns_7);

        nextBtn = (Button) findViewById(R.id.next_btn7) ;

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AnxietyQns7.this, AnxietyQns8.class));
            }
        }) ;
    }
}

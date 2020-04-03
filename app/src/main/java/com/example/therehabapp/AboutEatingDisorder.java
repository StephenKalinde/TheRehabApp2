package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AboutEatingDisorder extends AppCompatActivity {

    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_eating_disorder);

        nextBtn =(Button) findViewById(R.id.continue_btn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(AboutEatingDisorder.this, ThankYouNote.class);
                intent.putExtra("Diagnosis", "Eating Disorders");

                startActivity(intent);
            }
        });
    }
}

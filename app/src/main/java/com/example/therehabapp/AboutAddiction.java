package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutAddiction extends AppCompatActivity {

    private Button continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_addiction);

        continueBtn = (Button) findViewById(R.id.continue_btn);


        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AboutAddiction.this, ThankYouNote.class);
                intent.putExtra("Diagnosis", "Addiction");

                startActivity(intent);

            }
        });
    }

}

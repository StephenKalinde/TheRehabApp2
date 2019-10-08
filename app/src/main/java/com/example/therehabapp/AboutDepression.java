package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AboutDepression extends AppCompatActivity
{

    private Button finishBtn;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.about_depression);

         finishBtn= (Button) findViewById(R.id.continue_btn_depression);

         finishBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 startActivity(new Intent(AboutDepression.this, ThankYouNote.class));

             }
         });


     }
}

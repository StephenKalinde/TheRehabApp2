package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Introduction extends AppCompatActivity {

    private Button continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduction_view);

        continueBtn = (Button) findViewById(R.id.next_btn);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =  new Intent(Introduction.this, SignUp.class);
                intent.putExtra("Error","none");
                startActivity(intent);
            }
        });

    }

}

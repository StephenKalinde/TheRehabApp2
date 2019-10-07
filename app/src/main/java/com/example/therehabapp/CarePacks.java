package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CarePacks extends AppCompatActivity {

    private Button pack1, pack2, pack3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_setup);

        pack1= (Button) findViewById(R.id.btn_1);
        pack2= (Button) findViewById(R.id.btn_2);
        pack3= (Button) findViewById(R.id.btn_3);

        pack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarePacks.this, TermsAndConditions.class) );
            }
        });

        pack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarePacks.this, TermsAndConditions.class) );
            }
        });

        pack3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CarePacks.this, TermsAndConditions.class) );
            }
        });


    }
}

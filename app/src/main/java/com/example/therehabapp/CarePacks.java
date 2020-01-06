package com.example.therehabapp;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CarePacks extends AppCompatActivity {

    private Button pack1, pack2, pack3;

    private Button pack1Btn;
    private Button pack2Btn;
    private Button pack3Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.care_pack_view);

        pack1= (Button) findViewById(R.id.btn_1);
        pack2= (Button) findViewById(R.id.btn_2);
        pack3= (Button) findViewById(R.id.btn_3);

        pack1Btn= (Button) findViewById(R.id.select_btn);
        pack2Btn= (Button) findViewById(R.id.select_btn_2);
        pack3Btn= (Button) findViewById(R.id.select_btn_3);

        pack1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //add code here

                //start activity
                startActivity(new Intent(CarePacks.this, Home.class) );
            }
        });

        pack2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // add code here

                //start activity
                startActivity(new Intent(CarePacks.this, Home.class) );
            }
        });

        pack3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // add code here

                //start activity
                startActivity(new Intent(CarePacks.this, Home.class) );
            }
        });


    }
}

package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutAddiction extends AppCompatActivity {

    private Button continueBtn;
    private TextView heading;
    private TextView subHeading, subHeading_2, subHeading_3, subHeading_4;
    private TextView subText, subText2, subText3, subText4, subText5, subText6, subText7, subText8, subText9, subText10, subText11, subText12, subText13, subText14, subText15, subText16, subText17 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_addiction);

        continueBtn = (Button) findViewById(R.id.continue_btn);
        heading = (TextView) findViewById(R.id.heading_view);

        subHeading = (TextView) findViewById(R.id.sub_heading_view);
        subHeading_2= (TextView) findViewById(R.id.sub_heading_view_2);
        subHeading_3= (TextView) findViewById(R.id.sub_heading_view_3);
        subHeading_4= (TextView) findViewById(R.id.sub_heading_view_4);

        subText= (TextView) findViewById(R.id.sub_text_1);
        subText2= (TextView) findViewById(R.id.sub_text_2);
        subText3= (TextView) findViewById(R.id.sub_text_3);
        subText4= (TextView) findViewById(R.id.sub_text_4);
        subText5= (TextView) findViewById(R.id.sub_text_5);
        subText6= (TextView) findViewById(R.id.sub_text_6);
        subText7= (TextView) findViewById(R.id.sub_text_7);
        subText8= (TextView) findViewById(R.id.sub_text_8);
        subText9= (TextView) findViewById(R.id.sub_text_9);
        subText10= (TextView) findViewById(R.id.sub_text_10);
        subText11= (TextView) findViewById(R.id.sub_text_11);
        subText12= (TextView) findViewById(R.id.sub_text_12);
        subText13= (TextView) findViewById(R.id.sub_text_13);
        subText14= (TextView) findViewById(R.id.sub_text_14);
        subText15= (TextView) findViewById(R.id.sub_text_15);
        subText16= (TextView) findViewById(R.id.sub_text_16);
        subText17= (TextView) findViewById(R.id.sub_text_17);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AboutAddiction.this, ThankYouNote.class));

            }
        });
    }

}

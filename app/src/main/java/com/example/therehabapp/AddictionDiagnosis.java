package com.example.therehabapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class AddictionDiagnosis extends AppCompatActivity {


    private Button finishBtn;
    private TextView pageHeading;
    private TextView addictionHeading;
    private TextView subText;
    private TextView subText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.addiction_diagnosis);

        finishBtn = (Button) findViewById (R.id.continue_btn1);
        pageHeading = (TextView) findViewById (R.id.page_heading);
        addictionHeading = (TextView) findViewById (R.id.heading_view);
        subText= (TextView) findViewById(R.id.sub_text);
        subText2= (TextView) findViewById(R.id.sub_text_2);

    }
}

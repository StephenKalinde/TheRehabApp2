package com.example.therehabapp.ProgrammeClasses;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.therehabapp.R;

public class AnxietyWk1  extends AppCompatActivity {

    private Button previousBtn;
    private Button nextBtn;

    @Override
    protected void OnCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anxiety_wk_1);

        previousBtn= (Button) findViewById(R.id.previous_button);
        nextBtn = (Button) findViewById(R.id.next_button);
    }

}

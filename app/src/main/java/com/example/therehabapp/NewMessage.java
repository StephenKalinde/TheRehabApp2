package com.example.therehabapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NewMessage extends AppCompatActivity {

    private ImageButton cancelBtn;
    protected Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstance){

        super.onCreate(savedInstance);
        setContentView(R.layout.new_message_view);

        cancelBtn=(ImageButton) findViewById(R.id.cancel_btn);
        mToolBar= (Toolbar) findViewById(R.id.chat_toolbar);

        String nameTitle= getIntent().getStringExtra("userName");

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("UserName");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close activity to messages
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId()){

            case android.R.id.home:
                onBackPressed();
                return true;

        }

        return super.onOptionsItemSelected(item);

    }
}

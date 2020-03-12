package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class PeerProfile extends AppCompatActivity {

    private TextView nameView;
    private Toolbar mToolBar;
    private FloatingActionButton newMessageBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peer_profile_view);

        Intent intent = getIntent();

        final String userName= intent.getStringExtra("UserName");
        final String userEmail = intent.getStringExtra("UserEmail");


        nameView = (TextView) findViewById(R.id.user_name_view);
        mToolBar = (Toolbar) findViewById(R.id.peer_profile_toolbar);
        newMessageBtn= (FloatingActionButton) findViewById(R.id.new_message_btn);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(userName);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameView.setText(userName);

        newMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PeerProfile.this,NewMessage.class);
                intent.putExtra("UserName",userName);
                intent.putExtra("UserEmail",userEmail);

                startActivity(intent);

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

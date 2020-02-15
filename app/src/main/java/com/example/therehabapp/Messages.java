package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Messages extends AppCompatActivity {


    private Toolbar myToolBar;
    private FloatingActionButton newMessage;
    private ListView messagesListView;
    private SwipeRefreshLayout myRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_view);

        newMessage= (FloatingActionButton) findViewById(R.id.new_message_btn);
        myToolBar=(Toolbar)findViewById(R.id.messages_toolbar);
        messagesListView=(ListView) findViewById(R.id.messages_list_view);
        myRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.messages_refresh_layout);

        setSupportActionBar(myToolBar);
        getSupportActionBar().setTitle("Messages");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Messages.this, ContactsList.class);
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

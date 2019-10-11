package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Journal extends AppCompatActivity {


    private Toolbar mToolbar;
    private FloatingActionButton addNoteBtn;

    @Override
    protected void onCreate(Bundle savedInstance)
    {

        super.onCreate(savedInstance);
        setContentView(R.layout.journal_view);

        addNoteBtn=(FloatingActionButton) findViewById(R.id.add_note_btn);
        mToolbar=(Toolbar) findViewById(R.id.journal_toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Journal");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Journal.this, NewNote.class));
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


            //onclick menu items
            case R.id.delete:
                break;

            case R.id.share:
                break;

        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

}

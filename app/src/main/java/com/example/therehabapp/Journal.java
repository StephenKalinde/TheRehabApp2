package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Journal extends AppCompatActivity {

    private Toolbar mToolbar;
    private FloatingActionButton addNoteBtn;

    private List<NoteBuilder> myList ;
    private NoteBuilder noteBuilder;

    private NotesAdapter nAdapter;
    private RecyclerView notesRecycler;


    @Override
    protected void onCreate(Bundle savedInstance)
    {

        super.onCreate(savedInstance);
        setContentView(R.layout.journal_view);

        myList= new ArrayList<NoteBuilder>();
        prepareNotes(); //populates list with notes

        addNoteBtn=(FloatingActionButton) findViewById(R.id.add_note_btn);
        mToolbar=(Toolbar) findViewById(R.id.journal_toolbar);
        notesRecycler= (RecyclerView) findViewById(R.id.notes_recycler_view);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Journal");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //myView.setText(myList.size());

        RecyclerView.LayoutManager mylayoutManager= new LinearLayoutManager(this);
        notesRecycler.setLayoutManager(mylayoutManager);
        notesRecycler.setItemAnimator(new DefaultItemAnimator());

        nAdapter= new NotesAdapter(myList);
        notesRecycler.setAdapter(nAdapter);

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(Journal.this, NewNote.class);
                startActivityForResult(intent,1);

            }
        });
    }

    private void prepareNotes()
    {

        File directory;
        directory = getFilesDir();
        File[] files = directory.listFiles();

        for(int i = 0 ; i < files.length ; i++)
        {

            String filename= files[i].getName();
            NoteBuilder note = new NoteBuilder(filename,OpenNote(filename));
            myList.add(note);

        }


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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if(resultCode==RESULT_OK)
        {
           // prepareNotes();
            File directory;
            directory = getFilesDir();
            File[] files = directory.listFiles();

            String filename= files[files.length-1].getName();
            NoteBuilder note = new NoteBuilder(filename,OpenNote(filename));
            myList.add(note);
            
            nAdapter.onActivityResult(requestCode,1);;
        }

    }

    private String OpenNote(String fileName)
    {
        String content="";
        StringBuilder buf = new StringBuilder();

        try
        {
            InputStream in= openFileInput(fileName);
            if( in != null)
            {
                InputStreamReader temp= new InputStreamReader(in);
                BufferedReader reader = new BufferedReader(temp);
                String str;

                while((str= reader.readLine())!=null)
                {
                    buf.append(str +"\n");
                }
                in.close();
            }

            content = buf.toString();

        }

        catch(java.io.FileNotFoundException e){}catch (Throwable t)
        {
            Toast.makeText(this,"Exception"+t.toString(),Toast.LENGTH_LONG).show();
        }

        return content;
    }

}

package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class Journal extends AppCompatActivity implements NotesAdapter.OnNoteListener {

    private Toolbar mToolbar;
    private FloatingActionButton addNoteBtn;

    private List<NoteBuilder> myList ;
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

        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this);
        notesRecycler.setLayoutManager(layoutManager);
        notesRecycler.setItemAnimator(new DefaultItemAnimator());
        nAdapter= new NotesAdapter(myList, this);
        notesRecycler.setAdapter(nAdapter);

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(Journal.this, NewNote.class);
                startActivityForResult(intent,1);

            }
        });

        notesRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void prepareNotes()
    {


        File directory = getFilesDir();
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
        File directory;
        directory = getFilesDir();
        File[] files = directory.listFiles();

        if(resultCode==RESULT_OK)
        {

           // add new listing to list then update recycle view

            String filename= files[files.length-1].getName();
            NoteBuilder note = new NoteBuilder(filename,OpenNote(filename));
            myList.add(note);

            nAdapter.onActivityResult(requestCode,1);

        }

      /**  if(resultCode==2)
        {
            //remove old file

            String filename=data.getStringExtra("filename");
            NoteBuilder note= new NoteBuilder(filename, OpenNote(filename));

            File file= new File(directory,filename);
            file.delete();

            myList.add(note);
            nAdapter.onActivityResult(requestCode,1);

        } **/

    }

    /**Note handling**/

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

    private void DeleteNote(String filename)
    {
        File directory= getFilesDir();
        File file= new File(directory, filename);
        file.delete();
    }


    @Override
    public void onNoteClick(int position) {
        // insert code to open new activity here
        Intent intent = new Intent(this, NewNote.class);
        intent.putExtra("title",myList.get(position).getTitle().substring(0,myList.get(position).getTitle().length()-4));
        intent.putExtra("content",myList.get(position).getContent());
        startActivity(intent);

    }

    @Override
    public void onLongClick(int position) {
        File directory = getFilesDir();
        File[] files = directory.listFiles();
        String filename= files[position].getName();

        DeleteNote(filename);
        myList.remove(position);
        //prepareNotes();
        nAdapter.notifyDataSetChanged();
    }
}

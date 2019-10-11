package com.example.therehabapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewNote extends AppCompatActivity {

    private Toolbar noteToolbar;
    private Date mDate;
    private String currentDate;
    private EditText notePad;


    @Override
    protected void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.new_note);

        noteToolbar= (Toolbar) findViewById(R.id.note_toolbar);
        notePad=(EditText) findViewById(R.id.note_pad);

        notePad.setText(Open());

        mDate= new Date();
        Date date= Calendar.getInstance().getTime();
        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(date);

        setSupportActionBar(noteToolbar);
        getSupportActionBar().setTitle(currentDate);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()){

            case android.R.id.home:

                SaveNote(currentDate); //saveNote

                onBackPressed();
                return true;

        }

        return super.onOptionsItemSelected(item);

    }



    //saves notepad text
    private void SaveNote(String fileName)
    {
        try
        {
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput(fileName,0));
            out.write(notePad.toString());
            out.close();
            Toast.makeText(this, "Note Saved !", Toast.LENGTH_SHORT).show();

        }
        catch(Throwable t)
        {

            Toast.makeText(this, "Exception:"+ t.toString(), Toast.LENGTH_LONG).show();

        }
    }

    //filename
    public boolean FileExists(String filename)
    {
        File file = getBaseContext().getFileStreamPath(filename);
        return ((File) file).exists();
    }

    //open file with name fileName
    private String Open(String fileName)
    {
        String content = "";
        if(FileExists(fileName))
        {
            try
            {
                InputStream in = openFileInput(fileName);
                if( in != null){
                    InputStreamReader tmp = new InputStreamReader(in);
                    BufferedReader reader= new BufferedReader(tmp);
                    String str;
                    StringBuilder buf = new StringBuilder();
                    while((str = reader.readLine()) !=null)
                    {
                        buf.append(str + "\n");
                    }
                    in.close();
                    content = buf.toString();

                }
            }

            catch (java.io.FileNotFoundException e){} catch (Throwable t)
            {
                Toast.makeText(this, "Exception: "+ t.toString(), Toast.LENGTH_SHORT).show();
            }

        }

        return content;
    }
}

package com.example.therehabapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.therehabapp.Messaging.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewMessage extends AppCompatActivity {

    protected Toolbar mToolBar;
    private ListView threadsListView;
    private EditText messageEditView;
    private Button sendMessageBtn;
    private List<Message> myMessages;

    private DatabaseReference myThreadRef;

    @Override
    protected void onCreate(Bundle savedInstance){

        super.onCreate(savedInstance);
        setContentView(R.layout.new_message_view);

        myMessages = new ArrayList<Message>();

        mToolBar= (Toolbar) findViewById(R.id.chat_toolbar);
        threadsListView = findViewById(R.id.thread_list_view);
        messageEditView = findViewById(R.id.message_edit_view);
        sendMessageBtn = findViewById(R.id.send_btn);

        String inboxId= getIntent().getStringExtra("inboxid");
        myThreadRef = FirebaseDatabase.getInstance().getReference("Inboxes/"+inboxId);



        String nameTitle= getIntent().getStringExtra("userName");
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(nameTitle);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /** send message here **/

               //Date
                long millis = System.currentTimeMillis();
                java.sql.Date date= new java.sql.Date(millis);
                String dateString = date.toString();
                String time= "00.00";

                Message newMessage =new Message(messageEditView.getText().toString(),dateString,time);
                myThreadRef.push().setValue(newMessage);
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

    @Override
    protected void onStart() {
        super.onStart();

        MessagesThread threadAdapter = new MessagesThread(this,GetThread());
        threadsListView.setAdapter(threadAdapter);
        //threadAdapter.notifyAll();
    }

    private ArrayList<Message> GetThread(){

        final ArrayList<Message> myMessages = new ArrayList<>();

        myThreadRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

              for(DataSnapshot messageSnapshot: dataSnapshot.getChildren())
              {
                  Message message = messageSnapshot.getValue(Message.class);

                  myMessages.add(message);
              }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return myMessages;
    }


}

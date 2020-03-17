package com.example.therehabapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.therehabapp.Messaging.Message;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NewMessage extends AppCompatActivity {

    protected Toolbar mToolBar;
    private ListView threadsListView;
    private EditText messageEditView;
    private Button sendMessageBtn;
    private List<Message> myMessages;
    private MessagesThread threadAdapter;

    private DatabaseReference myThreadRef;

    @Override
    protected void onCreate(Bundle savedInstance){

        super.onCreate(savedInstance);
        setContentView(R.layout.new_message_view);

        myMessages = new ArrayList<>();

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
                messageEditView.setText("");
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

        threadAdapter = new MessagesThread(this,GetThread());
        threadsListView.setAdapter(threadAdapter);
        //threadAdapter.notifyDataSetChanged();
    }

    private List<Message> GetThread(){

        final List<Message> messages = new ArrayList<>();

        myThreadRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

              for(DataSnapshot messageSnapshot: dataSnapshot.getChildren())
              {
                  Message message = messageSnapshot.getValue(Message.class);

                  messages.add(message);


              }

                threadAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return messages;
    }

    private List<Message> TestMessages(){

        List<Message> newMessages = new ArrayList<>();

        Message msg_1=  new Message ("Hey man", "2020", "00.00");
        Message msg_2=  new Message ("Nothing much", "2020", "00.01");
        Message msg_3=  new Message ("What are you up to?", "2020", "00.03");

        newMessages.add(msg_1);
        newMessages.add(msg_2);
        newMessages.add(msg_3);

        return newMessages;
    }


}

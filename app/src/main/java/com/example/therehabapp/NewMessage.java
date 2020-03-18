package com.example.therehabapp;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

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

        myMessages =GetThread();

        threadAdapter = new MessagesThread(this,myMessages);
        threadsListView.setAdapter(threadAdapter);

        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /** send message here **/

               //Date
                long millis = System.currentTimeMillis();
                java.sql.Date date= new java.sql.Date(millis);
                String dateString = date.toString();

                //Time
                long mills =System.currentTimeMillis();
                java.util.Date dateTime= new java.util.Date(mills);
                String dateTimeString = dateTime.toString();

                String time = dateTimeString.substring(11,16);

                Toast.makeText(NewMessage.this, time, Toast.LENGTH_LONG).show();

                Message newMessage =new Message(messageEditView.getText().toString(),dateString,time);
                myThreadRef.push().setValue(newMessage);
                messageEditView.setText("");

            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

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

   /** @Override
    protected void onStart() {

        super.onStart();

        threadAdapter = new MessagesThread(this,GetThread());
        threadsListView.setAdapter(threadAdapter);

    }**/

    private List<Message> GetThread(){

        final List<Message> messages = new ArrayList<>();

        myThreadRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Message message = dataSnapshot.getValue(Message.class);

                messages.add(message);
                threadAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       /** myThreadRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

              for(DataSnapshot messageSnapshot: dataSnapshot.getChildren())
              {
                  Message message = messageSnapshot.getValue(Message.class);

                  messages.add(message);

              }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }); **/

        return messages;
    }

}

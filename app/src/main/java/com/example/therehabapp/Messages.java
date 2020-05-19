package com.example.therehabapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.therehabapp.Messaging.Message;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class Messages extends AppCompatActivity{

    private ListView threadsListView;
    private FloatingActionButton newMessageBtn;
    private Toolbar toolbar;
    private ArrayList<Message> threadsMessasges;

    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        threadsMessasges = new ArrayList<>();
        GetTopMessages();

        setContentView(R.layout.messages_view);

        firebaseDatabase = FirebaseDatabase.getInstance();

        toolbar = (Toolbar) findViewById(R.id.messages_toolbar);
        threadsListView = (ListView) findViewById(R.id.threads_list_view);
        newMessageBtn = (FloatingActionButton) findViewById(R.id.new_message_btn);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Messages");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Messages.this, ContactsList.class);
                startActivity(intent);

            }
        });

        threadsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //open inbox for thread at position {position}
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        final ProgressDialog progressDialogBox = new ProgressDialog(Messages.this, R.style.MyDialogTheme);
        progressDialogBox.setTitle("Loading");
        progressDialogBox.setCancelable(false);
        progressDialogBox.show();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                MessagesAdapter  adapter = new MessagesAdapter(Messages.this,threadsMessasges);
                threadsListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                progressDialogBox.cancel();

            }
        },3000);


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

    private void GetTopMessages ()
    {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String myUid = mAuth.getUid();
        DatabaseReference inboxIdsRef = FirebaseDatabase.getInstance().getReference("InboxIDs/"+myUid);
        inboxIdsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String id = dataSnapshot.getValue(String.class);            //inbox id
                FillTopMessage(id);

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

    }

    private void FillTopMessage(String inboxId)
    {

        Query messagesQuery = firebaseDatabase.getReference("Inboxes/"+inboxId).orderByKey().limitToLast(1); //thread firebase instance
        messagesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot messageSnapShot: dataSnapshot.getChildren())
                {

                    Message message = messageSnapShot.getValue(Message.class);
                    threadsMessasges.add(message);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {}

        });

    }

}

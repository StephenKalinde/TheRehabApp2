package com.example.therehabapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Messages extends AppCompatActivity {

    private ListView messagesListView;
    private FloatingActionButton newMessageBtn;
    private Toolbar toolbar;

    private ArrayList<String> inboxIds;
    private ArrayList<Message> topMessages;

    private DatabaseReference idRef;
    private DatabaseReference inboxesRef;
    private String uid;

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_view);

        count = 0;

        uid = FirebaseAuth.getInstance().getUid();
        idRef = FirebaseDatabase.getInstance().getReference("InboxIDs/"+uid);
        inboxesRef = FirebaseDatabase.getInstance().getReference("Inboxes/"+uid);

        inboxIds = new ArrayList<>();
        topMessages = new ArrayList<>();

        messagesListView = (ListView) findViewById(R.id.messages_list_view);
        newMessageBtn = (FloatingActionButton) findViewById(R.id.new_message_btn);
        toolbar = (Toolbar) findViewById(R.id.messages_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Messages");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(Messages.this, ""+count,Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Messages.this, ContactsList.class);
                startActivity(intent);

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

       // FillInboxIds();
        //FillTopMessages(inboxIds);

    }


    private void FillInboxIds()
    {

        final List<String> inboxIds = new ArrayList<>();

        idRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String id = dataSnapshot.getValue(String.class);
                inboxIds.add(id);

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

        count= inboxIds.size();

        for(String id: inboxIds)
        {

            count++;
            final ArrayList<Message> list = new ArrayList<>();

            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Inboxes/"+id);
            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot messageShot: dataSnapshot.getChildren())
                    {

                        Message message = messageShot.getValue(Message.class);
                        list.add(message);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            int lastIndex = list.size()-1;
            Message topMessage = list.get(lastIndex);
            topMessages.add(topMessage);

        }

    }

    private void FillTopMessages(List<String> idslist)
    {


        for(String id: idslist)
        {

            count++;
            final ArrayList<Message> list = new ArrayList<>();

            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Inboxes/"+id);
            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot messageShot: dataSnapshot.getChildren())
                    {

                        Message message = messageShot.getValue(Message.class);
                        list.add(message);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            int lastIndex = list.size()-1;
            Message topMessage = list.get(lastIndex);
            topMessages.add(topMessage);

        }

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

package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.therehabapp.Messaging.Message;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Messages extends AppCompatActivity {


    private Toolbar myToolBar;
    private FloatingActionButton newMessage;
    private ListView messagesListView;
    private Button testingBtn;

    private int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_view);

        myToolBar = (Toolbar) findViewById(R.id.messages_toolbar);
        testingBtn = (Button) findViewById(R.id.testing_btn);

        setSupportActionBar(myToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Messages");

        final List<String> inboxIds = GetInboxIds();
        final List<Message> topMessages = GetTopMessages(inboxIds);

        testingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Messages.this, ""+inboxIds.size(),Toast.LENGTH_LONG).show();

            }
        });

    }

    private List<String> GetInboxIds()
    {
        final List<String> myInboxIds = new ArrayList<>();

        String myUid = FirebaseAuth.getInstance().getUid();
        DatabaseReference threadsRef = FirebaseDatabase.getInstance().getReference("InboxIDs/"+myUid);

        threadsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot idSnapShot: dataSnapshot.getChildren())
                {

                    String id = idSnapShot.getValue(String.class);
                    myInboxIds.add(id);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return myInboxIds;
    }

    private List<Message> GetTopMessages(List<String> inboxIds)
    {

        final List<Message> myTopMessages = new ArrayList<>();
        int len = inboxIds.size();
        count= len;

        for(int i=0; i<len; i++ )
        {

            String inboxId = inboxIds.get(i);
            final List<Message> threadMessages = new ArrayList<>();

            DatabaseReference threadRef = FirebaseDatabase.getInstance().getReference("Inboxes/"+inboxId);

            threadRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot messageSnapshot: dataSnapshot.getChildren())
                    {

                        Message myMessage = messageSnapshot.getValue(Message.class);
                        threadMessages.add(myMessage);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            int lastIndex = threadMessages.size()-1;
            Message lastMessage = threadMessages.get(lastIndex);

            myTopMessages.add(lastMessage);

        }
        return myTopMessages;
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

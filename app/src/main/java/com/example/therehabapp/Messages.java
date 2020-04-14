package com.example.therehabapp;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private ArrayList<TopMessage> topMessages;

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
        FirebaseDatabase instance= FirebaseDatabase.getInstance();
        idRef =instance.getReference("InboxIDs/"+uid);
        inboxesRef = instance.getReference("Inboxes/"+uid);

        inboxIds = new ArrayList<>();
        topMessages = new ArrayList<>();

        messagesListView = (ListView) findViewById(R.id.messages_list_view);
        newMessageBtn = (FloatingActionButton) findViewById(R.id.new_message_btn);
        toolbar = (Toolbar) findViewById(R.id.messages_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Messages");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GetTopMessages();

        Log.d("ID LOGG" ,""+inboxIds.size());
        Log.d("Top Message LOGG" ,""+topMessages.size());
        //Log.d("TESTING LOG",""+topMessageList.size());
        TopMessagesAdapter adapter = new TopMessagesAdapter(Messages.this, topMessages);
        messagesListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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

        //FillInboxIds();
        //FillTopMessages(inboxIds);

    }

    //get topMessages
    private void GetTopMessages()
    {

        FillInboxIds();  //inbox ids

         //top messages file

        for(String inboxId : inboxIds)
        {

            DatabaseReference topMsgRef = FirebaseDatabase.getInstance().getReference("TopMessages/" + inboxId);  // topmsgs ref path

            topMsgRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TopMessage topMessage = dataSnapshot.getValue(TopMessage.class);
                    topMessages.add(topMessage);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }



    }

    private void FillInboxIds()
    {

        idRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {

                    String id = snapshot.getValue(String.class);
                    inboxIds.add(id);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
}

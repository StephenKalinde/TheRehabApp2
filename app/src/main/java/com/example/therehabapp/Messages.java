package com.example.therehabapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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

public class Messages extends AppCompatActivity{

    private ListView threadsListView;
    private FloatingActionButton newMessageBtn;
    private Toolbar toolbar;

    private ArrayList<String> inboxIDs;
    private ArrayList<String> inboxIDs2;
    private ArrayList<TopMessage> topMessages;

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference inboxIdsRef;
    private String uid;

    private ProgressDialog progressDialogBox;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_view);

        inboxIDs = new ArrayList<>();
        inboxIDs2 = new ArrayList<>();
        topMessages = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        uid= auth.getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        inboxIdsRef = firebaseDatabase.getReference("InboxIDs/"+uid);

        toolbar = (Toolbar) findViewById(R.id.messages_toolbar);
        threadsListView = (ListView) findViewById(R.id.threads_list_view);
        newMessageBtn = (FloatingActionButton) findViewById(R.id.new_message_btn);

        GetInboxIds();

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

    }

    @Override
    protected void onStart() {
        super.onStart();

        progressDialogBox = new ProgressDialog(Messages.this, R.style.MyDialogTheme);
        progressDialogBox.setTitle("Messages");
        progressDialogBox.setMessage("Loading...");
        progressDialogBox.setCancelable(false);
        progressDialogBox.show();



        progressDialogBox.cancel();

        Log.d("TopMessages: Size= ",""+topMessages.size());

        /**for(String id: inboxIDs)
        {

            TopMessage topMessage = GetTopMessage(id);
            topMessages.add(topMessage);

        } **/

        //set Adapter here
    }

    private void GetInboxIds()
    {

        final ArrayList<String> inboxIds = new ArrayList<>();
        Log.d("InboxIds BEFORE",""+inboxIds.size());

        inboxIdsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String id = dataSnapshot.getValue(String.class);
                //inboxIDs2.add(id);
                //inboxIds.add(id);

                ProcessTopMessages(id);
                //Log.d("InboxIds ID",""+id);

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

        //Log.d("InboxIds AFTER",""+inboxIds.size());
        //Log.d("InboxIds 2222",""+ inboxIDs2.size());

//        return inboxIds;

    }

    private synchronized void ProcessTopMessages(String inboxId)
    {

        DatabaseReference topMessageRef = firebaseDatabase.getReference("TopMessages/"+inboxId);
        final ArrayList<TopMessage> tpMsgs = new ArrayList<>();

        topMessageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                TopMessage message = dataSnapshot.getValue(TopMessage.class);
                tpMsgs.add(message);
                Log.d("TopMessages: Size= ",""+ message.Message);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {}

        });

        Log.d("TopMessages: Size= ",""+ tpMsgs.size());

        topMessages = tpMsgs;

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

    /*** have to wait for TopMessage objects to fully load before adding to list tpMsgs (await) **/
}

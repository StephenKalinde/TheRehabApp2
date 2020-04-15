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

        CallData2(new FirebaseCallBack2() {
            @Override
            public void onCallBack(ArrayList<TopMessage> messages) {

                //Log.d("Array Size: ",""+messages.size());
                //Log.d("Array: ",messages.toString());

            }
        });

        progressDialogBox.cancel();

        //set Adapter here
    }

    /**private void GetInboxIds()
    {

        inboxIdsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String id = dataSnapshot.getValue(String.class);
                CallData(new FirebaseCallBack() {
                    @Override
                    public void onCallBack(TopMessage topMsg) {

                        Log.d("TopMessages: Size= ",""+topMsg.Message);

                        topMessages.add(topMsg);

                    }
                },id);

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

        Log.d("TopMessages: Size= ",""+topMessages.size());

    }

    private void ProcessTopMessages(String inboxId)
    {

        final DatabaseReference topMessageRef = firebaseDatabase.getReference("TopMessages/"+inboxId);

        topMessageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                msg = dataSnapshot.getValue(TopMessage.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {}

        });

    } **/

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


    private void CallData(final FirebaseCallBack firebaseCallBack, String inboxId)
    {

        final DatabaseReference topMessageRef = firebaseDatabase.getReference("TopMessages/"+inboxId);
        Log.d("Debug: InboxId",inboxId);

        topMessageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                TopMessage msg = dataSnapshot.getValue(TopMessage.class);

                Log.d("Debug: TopMessage",msg.Message);

                firebaseCallBack.onCallBack(msg);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {}

        });

    }

    private void CallData2(final FirebaseCallBack2 firebaseCallBack2)
    {

        final ArrayList<String> inboxIdsList = new ArrayList<>();

        inboxIdsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot idSnapshot: dataSnapshot.getChildren())
                {

                    String id = idSnapshot.getValue(String.class);

                    synchronized (this){

                        inboxIdsList.add(id);
                    }

                }

                Log.d("Debug: InboxListSize",""+inboxIdsList.size());

                final ArrayList<TopMessage> topMsgs = new ArrayList<>();
                for(String id : inboxIdsList)
                {


                        CallData(new FirebaseCallBack() {
                            @Override
                            public void onCallBack(TopMessage topMsg) {

                                Log.d("Debug: TopMessagesCORE",topMsg.Message);

                                    topMsgs.add(topMsg);  //////////problem here

                            }
                        },id);

                }

                Log.d("Debug: TopMessagesSize",""+topMsgs.size());
                firebaseCallBack2.onCallBack(topMsgs);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {



            }

        });



    }

    private interface FirebaseCallBack{ void onCallBack(TopMessage topMsg);}

    private interface FirebaseCallBack2{ void onCallBack(ArrayList<TopMessage> messages);}
}

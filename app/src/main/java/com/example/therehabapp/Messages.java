package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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
public class Messages extends AppCompatActivity {


    private Toolbar myToolBar;
    private FloatingActionButton newMessage;
    private ListView messagesListView;

    private String uid;
    private int size=1;

    private DatabaseReference myInboxIDsRefs;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_view);

        newMessage= (FloatingActionButton) findViewById(R.id.new_message_btn);
        myToolBar=(Toolbar)findViewById(R.id.messages_toolbar);
        messagesListView=(ListView) findViewById(R.id.messages_list_view);

        uid = FirebaseAuth.getInstance().getUid();

        FirebaseDatabase fbDb = FirebaseDatabase.getInstance();
        myInboxIDsRefs = fbDb.getReference("InboxIDs/"+uid);

        setSupportActionBar(myToolBar);
        getSupportActionBar().setTitle("Messages");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Messages.this, ContactsList.class);
                startActivity(intent);

            }
        });

        /**newMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Messages.this, ""+size, Toast.LENGTH_LONG).show();
            }
        }); **/

    }

    @Override
    protected void onStart() {

        super.onStart();

        ArrayList<Message> topMessages = GetTopMessages();

        MessagesAdapter adapter = new MessagesAdapter(Messages.this, topMessages);
        messagesListView.setAdapter(adapter);

    }

    int count =0;
    private ArrayList<Message> GetTopMessages()
    {
        //list for top messages and all my inboxids
        final ArrayList<Message> myMessages = new ArrayList<>();
        ArrayList<Message> myTopMessages = new ArrayList<>();
        final ArrayList<String> myInboxIds = new ArrayList<>();


        //ref to get all my inbox ids;
        myInboxIDsRefs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for( DataSnapshot idDataSnapshot: dataSnapshot.getChildren()){
                    String inboxId = idDataSnapshot.getValue(String.class);
                    myInboxIds.add(inboxId);
                    count++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        size= count;

        for(int i=0; i< myInboxIds.size();i++){

            //form inbox ref
            DatabaseReference myInboxRef = FirebaseDatabase.getInstance().getReference("Inboxes/"+myInboxIds.get(i));

            //get all messages from ref
            myInboxRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot messageSnapShot: dataSnapshot.getChildren())
                    {
                        Message message = messageSnapShot.getValue(Message.class);
                        myMessages.add(message);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            //add topmessages to the arraylist
            int len = myMessages.size();
            int lastMessageIndex = len -1;
            Message lastMessage = myMessages.get(lastMessageIndex);
            myTopMessages.add(lastMessage);

            myMessages.clear();

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

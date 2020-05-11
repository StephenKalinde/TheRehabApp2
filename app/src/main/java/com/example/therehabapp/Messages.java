package com.example.therehabapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
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
    private ArrayList<TopMessage> topMessagesX;

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

        progressDialogBox.cancel();

        //set Adapter here
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

    private synchronized ArrayList<TopMessage> GetTopMessages ()
    {

        final ArrayList<TopMessage> topMessagesList = new ArrayList<>();

        inboxIdsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot idSnapshot: dataSnapshot.getChildren())
                {

                    String id = idSnapshot.getValue(String.class);
                    Log.d("Debugg: idGet",id);

                    TopMessage topMessage = GetTopMessage(id); //await this process

                    Log.d("Debugg: TopMessageOutCo",topMessage.Message);

                    topMessagesList.add(topMessage);
                    //topMessagesList.add(topMessage);



                }

                //done.countDown();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {



            }

        });


       // Log.d("Debugg: TopMessageLstOt",""+topMessagesList.size());
        return topMessagesList;

    }

    private TopMessage GetTopMessage(String inboxId)
    {

        final DatabaseReference topMessageRef = firebaseDatabase.getReference("TopMessages/"+inboxId);
        final ArrayList<TopMessage> topMessageCarry = new ArrayList<>();

        topMessageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                TopMessage topMessage = dataSnapshot.getValue(TopMessage.class);
                Log.d("Debugg: TopMessageGet",topMessage.Message);
                topMessageCarry.add(topMessage);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {}

        });

        TopMessage myTopMessage= new TopMessage();
        myTopMessage.Message="Hey";
        myTopMessage.InboxID="dgrbbbr";
        myTopMessage.Date="Now";
        myTopMessage.UID="myUser";
        myTopMessage.Time="11:30";
        Log.d("Debugg: TopMessageOut",myTopMessage.Message);
        return myTopMessage;

    }

}

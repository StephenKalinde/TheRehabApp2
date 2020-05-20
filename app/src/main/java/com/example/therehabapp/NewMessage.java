package com.example.therehabapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.therehabapp.Messaging.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewMessage extends AppCompatActivity {

    protected Toolbar mToolBar;
    private ListView threadsListView;
    private EditText messageEditView;
    private SwipeRefreshLayout myRefreshLayout;
    private Button sendMessageBtn;
    private ArrayList<Message> myMessages;
    private MessagesThread threadAdapter;

    private DatabaseReference myThreadRef;
    private DatabaseReference topMessageRef;
    private String uid;

    private String inboxId;
    private String destinationUID;
    private String peerName;
    private String myName;

    @Override
    protected void onCreate(Bundle savedInstance){

        super.onCreate(savedInstance);

        /** initialising data structures and variables**/
        myMessages = new ArrayList<>();

        destinationUID = getIntent().getStringExtra("destinationUID");
        inboxId= getIntent().getStringExtra("inboxid");
        peerName= getIntent().getStringExtra("userName");

        /**setting activity view **/
        setContentView(R.layout.new_message_view);

        /**pre sending database queries **/
        uid = FirebaseAuth.getInstance().getUid();
        myThreadRef = FirebaseDatabase.getInstance().getReference("Inboxes/"+inboxId);
        topMessageRef = FirebaseDatabase.getInstance().getReference("TopMessages/"+inboxId);
        FindMyName();
        GetThread();

        /**setting views **/

        mToolBar= (Toolbar) findViewById(R.id.chat_toolbar);
        threadsListView = findViewById(R.id.thread_list_view);
        messageEditView = findViewById(R.id.message_edit_view);
        sendMessageBtn = findViewById(R.id.send_btn);
        myRefreshLayout = findViewById(R.id.messages_refresh_layout);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(peerName);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /**send button onclick listener **/

        sendMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               //Date
                long millis = System.currentTimeMillis();
                java.sql.Date date= new java.sql.Date(millis);
                String dateString = date.toString();

                //Time
                long mills =System.currentTimeMillis();
                java.util.Date dateTime= new java.util.Date(mills);
                String dateTimeString = dateTime.toString();

                String time = dateTimeString.substring(11,16);

                Message newMessage =new Message(messageEditView.getText().toString(),dateString,time, uid,destinationUID,myName,inboxId,peerName);
                myThreadRef.push().setValue(newMessage);

                TopMessage topMessage = new TopMessage(messageEditView.getText().toString(), dateString,time, uid, inboxId);
                topMessageRef.setValue(topMessage);

                messageEditView.setText("");

            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    @Override
    protected void onStart() {

        super.onStart();

        final ProgressDialog progressDialogBox = new ProgressDialog(NewMessage.this, R.style.MyDialogTheme);
        progressDialogBox.setTitle("Loading");
        progressDialogBox.setCancelable(false);
        progressDialogBox.show();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                threadAdapter = new MessagesThread(NewMessage.this,myMessages);
                threadsListView.setAdapter(threadAdapter);
                threadAdapter.notifyDataSetChanged();


                progressDialogBox.cancel();


            }
        },1500);

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

    private void GetThread(){

        myThreadRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Message message = dataSnapshot.getValue(Message.class);
                myMessages.add(message);

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

    private void FindMyName()
    {

        DatabaseReference myNameRef = FirebaseDatabase.getInstance().getReference("Users/"+uid+"/Name");

        myNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                myName = dataSnapshot.getValue(String.class);
                //Log.d("DebuggMe: ",myName);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}

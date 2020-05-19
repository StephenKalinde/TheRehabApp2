package com.example.therehabapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.therehabapp.Messaging.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;

public class NewMessage extends AppCompatActivity {

    protected Toolbar mToolBar;
    private ListView threadsListView;
    private EditText messageEditView;
    private SwipeRefreshLayout myRefreshLayout;
    private Button sendMessageBtn;
    private List<Message> myMessages;
    private MessagesThread threadAdapter;

    private DatabaseReference myThreadRef;
    private DatabaseReference topMessageRef;
    private String uid;

    private String inboxId;
    private String userEmail;
    private String destinationUID;
    private String peerName;
    private String myName;

    @Override
    protected void onCreate(Bundle savedInstance){

        super.onCreate(savedInstance);
        setContentView(R.layout.new_message_view);

        myMessages = new ArrayList<>();

        mToolBar= (Toolbar) findViewById(R.id.chat_toolbar);
        threadsListView = findViewById(R.id.thread_list_view);
        messageEditView = findViewById(R.id.message_edit_view);
        sendMessageBtn = findViewById(R.id.send_btn);
        myRefreshLayout = findViewById(R.id.messages_refresh_layout);

        userEmail = getIntent().getStringExtra("userEmail");
        destinationUID = getIntent().getStringExtra("destinationUID");

        inboxId= getIntent().getStringExtra("inboxid");
        uid = FirebaseAuth.getInstance().getUid();
        myThreadRef = FirebaseDatabase.getInstance().getReference("Inboxes/"+inboxId);
        topMessageRef = FirebaseDatabase.getInstance().getReference("TopMessages/"+inboxId);

        peerName= getIntent().getStringExtra("userName");
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(peerName);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //myMessages.clear();

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

                Message newMessage =new Message(messageEditView.getText().toString(),dateString,time, uid,destinationUID,myName,inboxId);
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

        FindMyName();
        myMessages =GetThread();
        threadAdapter = new MessagesThread(NewMessage.this,myMessages);
        threadsListView.setAdapter(threadAdapter);
        threadAdapter.notifyDataSetChanged();

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

    private List<Message> GetThread(){

        final List<Message> messages = new ArrayList<>();

        myThreadRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Message message = dataSnapshot.getValue(Message.class);
                messages.add(message);

                threadAdapter.notifyDataSetChanged();

                /**
                 * if the uid of the message is not the same as the current uid (im the recipient), show notification
                 */

                /**if(!uid.equals(message.UID)){

                    Intent intent = new Intent(getApplicationContext(),NewMessage.class);
                    intent.putExtra("userEmail",userEmail);
                    intent.putExtra("userName",nameTitle);
                    intent.putExtra("inboxid",inboxId);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(getApplicationContext());
                    taskStackBuilder.addNextIntentWithParentStack(intent);

                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                    //notification builder
                    NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                            .setSmallIcon(R.drawable.logo)
                            .setContentTitle("New Message")
                            .setContentText(message.Message)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);

                    //show notification
                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
                    notificationManagerCompat.notify(NOTIFICATION_ID,notification.build());

                } **/

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

        return messages;

    }

    private void FindMyName()
    {

        DatabaseReference myNameRef = FirebaseDatabase.getInstance().getReference("Users/"+uid+"/Name");

        myNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                myName = dataSnapshot.getValue(String.class);
                Log.d("DebuggMe: ",myName);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}

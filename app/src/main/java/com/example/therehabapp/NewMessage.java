package com.example.therehabapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.therehabapp.Messaging.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private String inboxId;
    private String uid;
    private String CHANNEL_ID="default";

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

        inboxId= getIntent().getStringExtra("inboxid");
        uid = FirebaseAuth.getInstance().getUid();
        myThreadRef = FirebaseDatabase.getInstance().getReference("Inboxes/"+inboxId);
        topMessageRef = FirebaseDatabase.getInstance().getReference("TopMessages/"+inboxId);

        String nameTitle= getIntent().getStringExtra("userName");
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(nameTitle);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myMessages =GetThread();

        threadAdapter = new MessagesThread(NewMessage.this,myMessages);
        threadsListView.setAdapter(threadAdapter);
        threadAdapter.notifyDataSetChanged();

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

                Message newMessage =new Message(messageEditView.getText().toString(),dateString,time, uid);
                myThreadRef.push().setValue(newMessage);

                TopMessage topMessage = new TopMessage(messageEditView.getText().toString(), dateString,time, uid, inboxId);
                topMessageRef.setValue(topMessage);

                //FCM
                RemoteMessage.Builder remoteMessageBuilder = new RemoteMessage.Builder(inboxId);
                remoteMessageBuilder.addData("message",newMessage.Message);
                remoteMessageBuilder.addData("uid",newMessage.UID);

                RemoteMessage remoteMessage = remoteMessageBuilder.build();

                FirebaseMessaging.getInstance().send(remoteMessage);

                messageEditView.setText("");

            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    @Override
    protected void onStart() {

        super.onStart();

        //create notification channel
        CreateChannel();
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

                if(uid.equals(message.UID)) {

                    NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                            .setSmallIcon(R.drawable.logo)
                            .setContentTitle("New Message")
                            .setContentText(message.Message)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());

                    notificationManagerCompat.notify(100,notification.build());

                }

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

    /**
     * creates a channel for the notifications
     */

    private void CreateChannel()
    {

        if(Build.VERSION.SDK_INT >= 26)
        {

            String channelName = "messages_channel";
            String description = "This Is My Notification Channel";
            int importance =  NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,channelName,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }

    }

}

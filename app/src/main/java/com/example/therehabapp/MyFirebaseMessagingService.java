package com.example.therehabapp;

import android.app.Notification;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private int BROADCAST_NOTIFICATION_ID =1;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> messageData = remoteMessage.getData();
        String myUID = messageData.get("topic");
        String title = messageData.get("title");
        String data_type = messageData.get("data_type");
        String senderUID = messageData.get("senderUID");

        //tests
        Log.d("DebuggMe: Title", title);
        Log.d("DebuggMe: Data Type", data_type);
        Log.d("DebuggMe: Topic", myUID);
        Log.d("DebuggMe: Sender UID", senderUID);



    }


}

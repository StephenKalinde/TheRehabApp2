package com.example.therehabapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private int BROADCAST_NOTIFICATION_ID = 1;
    private int NOTIFICATION_ID_MSG = 100;
    private int NOTIFICATION_ID_REQUEST = 150;

    private String CHANNEL_ID= "default";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> messageData = remoteMessage.getData();
        String data_type = messageData.get("data_type");

        /** Handling for incoming messages*/

        if(data_type.equals("direct_message")) {

            String myUID = messageData.get("topic");
            String title = messageData.get("title");
            String senderUID = messageData.get("senderUID");
            String senderName = messageData.get("senderName");

            CreateNewMessageChannel();

            Intent intent = new Intent(getApplicationContext(), Messages.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(getApplicationContext());
            taskStackBuilder.addNextIntentWithParentStack(intent);

            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

            //notification builder
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(title)
                    .setContentText("New Message From " + senderName)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            //show notification
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
            notificationManagerCompat.notify(NOTIFICATION_ID_MSG, notificationBuilder.build());

        }

        if(data_type.equals("request"))
        {

            String title = messageData.get("title");
            String inboxId = messageData.get("inboxId");
            String peerEmail = messageData.get("peerEmail");
            CreateNewMessageChannel();

            Intent intent = new Intent(getApplicationContext(), AllPeers.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(getApplicationContext());
            taskStackBuilder.addNextIntentWithParentStack(intent);

            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

            //notification builder
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(title)
                    .setContentText(peerEmail)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            //show notification
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
            notificationManagerCompat.notify(NOTIFICATION_ID_REQUEST, notificationBuilder.build());

        }

    }

    private void CreateNewMessageChannel() {

        if (Build.VERSION.SDK_INT >= 26) {

            String channelName = "messages_channel";
            String description = "This Is My Notification Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }

    }

}

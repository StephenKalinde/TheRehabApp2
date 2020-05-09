package com.example.therehabapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseTokenService extends FirebaseMessagingService {

    private String TAG = "MyFirebase";
    private String CHANNEL_ID="default";
    private int NOTIFICATION_ID =100;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        //CreateChannel();

        RemoteMessage.Notification notification =remoteMessage.getNotification();
        String title = notification.getTitle();
        String body = notification.getBody();

        /**Intent intent = new Intent(getApplicationContext(),Messages.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(getApplicationContext());
        taskStackBuilder.addNextIntentWithParentStack(intent);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        //notification builder
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Tesing")
                .setContentText("body")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        //show notification
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICATION_ID,notificationBuilder.build()); **/

    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        sendRegistrationTokenToServer(token);

    }

    private void sendRegistrationTokenToServer(String token)
    {

        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference tokenRef = FirebaseDatabase.getInstance().getReference("Tokens/"+uid);
        tokenRef.setValue(token).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {

                    Log.d("DebuggMe: ","Token saved successfully");

                }

                else{

                    Log.d("DebuggMe: "," Token failed to save");

                }

            }
        });

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

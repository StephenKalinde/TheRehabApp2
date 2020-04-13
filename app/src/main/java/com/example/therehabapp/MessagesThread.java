package com.example.therehabapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.therehabapp.Messaging.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class MessagesThread extends ArrayAdapter<Message> {

    private Activity context;
    private List<Message> messagesList;
    private FirebaseAuth mAuth;
    private String uid;

    public MessagesThread(Activity context, List<Message> messagesList)
    {
        super(context , R.layout.message_item_view, messagesList);
        this.context = context;
        this.messagesList = messagesList;

        mAuth = FirebaseAuth.getInstance();
        uid= mAuth.getUid();

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View myView= inflater.inflate(R.layout.message_item_view,null,true);

        RelativeLayout myLayout = myView.findViewById(R.id.message_holder_view) ;
        TextView message_view_item= myView.findViewById(R.id.text_message_view);
        TextView time_view_item  = myView.findViewById(R.id.time_view);
        TextView date_view_item = myView.findViewById(R.id.date_view);

        Message message= messagesList.get(position);

        if(uid.equals(message.UID))
        {

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) myLayout.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            myLayout.setLayoutParams(params);

        }

        message_view_item.setText(message.Message);
        time_view_item.setText(message.Time);
        date_view_item.setText(message.Date);

        return myView;

    }
}

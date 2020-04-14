package com.example.therehabapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TopMessagesAdapter extends ArrayAdapter {

    private Activity context;
    private ArrayList<TopMessage> topMessages;

    public TopMessagesAdapter(Activity context, ArrayList<TopMessage> topMessages)
    {

        super(context , R.layout.top_message_view, topMessages);
        this.context= context;
        this.topMessages= topMessages;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View view  = inflater.inflate(R.layout.top_message_view,null,true);
        TextView messageView = (TextView) view.findViewById(R.id.message_view);
        TextView timeView = (TextView) view.findViewById(R.id.time_view);
        TextView uidView = (TextView) view.findViewById(R.id.uid);

        TopMessage topMessage = topMessages.get(position);

        messageView.setText(topMessage.Message);
        timeView.setText(topMessage.Time);
        uidView.setText(topMessage.UID);

        return view;

    }

}

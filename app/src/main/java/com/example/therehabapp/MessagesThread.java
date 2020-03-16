package com.example.therehabapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.therehabapp.Messaging.Message;

import java.util.List;

public class MessagesThread extends ArrayAdapter<Message> {

    private Activity context;
    private List<Message> messagesList;

    public MessagesThread(Activity context, List<Message> messagesList)
    {
        super(context , R.layout.message_item_view, messagesList);
        this.context = context;
        this.messagesList = messagesList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View myView= inflater.inflate(R.layout.message_list_item,null,true);

        TextView message_view_item= myView.findViewById(R.id.text_message_view);
        TextView time_view_item  = myView.findViewById(R.id.time_view);
        TextView date_view_item = myView.findViewById(R.id.date_view);

        message_view_item.setText(messagesList.get(position).Message);
        time_view_item.setText(messagesList.get(position).Time);
        date_view_item.setText(messagesList.get(position).Date);

        return myView;

    }
}

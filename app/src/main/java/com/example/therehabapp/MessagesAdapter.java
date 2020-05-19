package com.example.therehabapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.therehabapp.Messaging.Message;

import java.util.ArrayList;
import java.util.List;

public class MessagesAdapter extends ArrayAdapter {

    private Activity context;
    private ArrayList<Message> messagesList;

    public MessagesAdapter(Activity context, ArrayList<Message> messagesList)
    {

        super(context , R.layout.message_list_item, messagesList);
        this.context= context;
        this.messagesList = messagesList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem= inflater.inflate(R.layout.message_list_item,null,true);
        TextView messagePreviewView =(TextView)  listViewItem.findViewById(R.id.message_preview_view);
        TextView peerNameView = (TextView) listViewItem.findViewById(R.id.peer_name_view);
        TextView messageTimeView= (TextView) listViewItem.findViewById(R.id.message_time_view);

        Message message = messagesList.get(position);
        messagePreviewView.setText(message.Message);
        peerNameView.setText(message.SenderName);
        messageTimeView.setText(message.Time);

        /**


        ImageView profileImage= (ImageView) listViewItem.findViewById(R.id.peer_pic_view);
        TextView messagePreviewView =(TextView)  listViewItem.findViewById(R.id.message_preview_view);
        TextView peerNameView = (TextView) listViewItem.findViewById(R.id.peer_name_view);
        TextView messageTimeView= (TextView) listViewItem.findViewById(R.id.message_time_view);

        Message peerMessage = threadsList.get(position);

        //set image resource from db here
        messagePreviewView.setText(peerMessage.Message);
        peerNameView.setText(peerMessage.UID);
        messageTimeView.setText(peerMessage.Time);

        return listViewItem; **/

        return listViewItem;

    }
}

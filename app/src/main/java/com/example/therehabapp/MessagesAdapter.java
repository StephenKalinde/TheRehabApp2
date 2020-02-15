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

import java.util.List;

public class MessagesAdapter extends ArrayAdapter {

    private Activity context;
    private List<String> threadsList;

    public MessagesAdapter(Activity context, List<String> userList)
    {

        super(context , R.layout.message_list_item, userList);
        this.context= context;
        this.threadsList = userList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem= inflater.inflate(R.layout.message_list_item,null,true);

        ImageView profileImage= (ImageView) listViewItem.findViewById(R.id.peer_pic_view);
        TextView messagePreviewView =(TextView)  listViewItem.findViewById(R.id.message_preview_view);
        TextView peerNameView = (TextView) listViewItem.findViewById(R.id.peer_name_view);
        TextView messageTimeView= (TextView) listViewItem.findViewById(R.id.message_time_view);

        String peerMessage = threadsList.get(position);

        /**set image resource from db here**/
        //messagePreviewView.setText(peerMessage.MessagePreview);
        peerNameView.setText(peerMessage);
        //messageTimeView.setText(peerMessage.DateTime);

        return listViewItem;

    }
}

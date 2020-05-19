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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        final TextView peerNameView = (TextView) listViewItem.findViewById(R.id.peer_name_view);
        TextView messageTimeView= (TextView) listViewItem.findViewById(R.id.message_time_view);

        Message message = messagesList.get(position);

        final String senderName =message.SenderName;
        final String destUID = message.DestinationUID;

        messagePreviewView.setText(message.Message);
        messageTimeView.setText(message.Time);
        //get Destination name

        String myUid = FirebaseAuth.getInstance().getUid();

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users/"+myUid+"/Name/");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);

                if(name.equals(senderName))
                {

                    DatabaseReference destUserRef =FirebaseDatabase.getInstance().getReference("Users/" + destUID+"/Name/");
                    destUserRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String nameToView = dataSnapshot.getValue(String.class);
                            peerNameView.setText(nameToView);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

                else{

                    peerNameView.setText(senderName);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return listViewItem;

    }
}

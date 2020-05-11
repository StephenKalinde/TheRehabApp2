package com.example.therehabapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.therehabapp.Peer;
import com.example.therehabapp.PeerProfile;
import com.example.therehabapp.R;
import com.example.therehabapp.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RequestAdapter extends ArrayAdapter<Peer> {

    private Activity context ;
    private ArrayList<Peer> list;
    private FirebaseAuth auth;
    private DatabaseReference myRef;
    private DatabaseReference peersRef;
    private DatabaseReference peerRequestsRef;
    private FirebaseAnalytics firebaseAnalytics;
    private String uid;

    public RequestAdapter(Activity context, ArrayList<Peer> list) {
        super(context, R.layout.request_item_view, list);

        this.context = context;
        this.list = list;          //requests list

        auth = FirebaseAuth.getInstance();
        uid = auth.getUid();
        firebaseAnalytics = FirebaseAnalytics.getInstance(context);

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem= inflater.inflate(R.layout.request_item_view,null,true);

        TextView userNameView =(TextView)  listViewItem.findViewById(R.id.user_name_view);
        TextView userEmailView = (TextView) listViewItem.findViewById(R.id.user_email_view);
        RelativeLayout myLayout= (RelativeLayout) listViewItem.findViewById(R.id.layout_view);
        final Button acceptBtn= (Button) listViewItem.findViewById(R.id.accept_btn);

        final Peer peer= list.get(position);

        userNameView.setText(peer.EmailAddress);
        //userEmailView.setText(peer.EmailAddress);

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //add peer to peers folder
                myRef = FirebaseDatabase.getInstance().getReference("Peers/"+uid);   // my ref
                peersRef = FirebaseDatabase.getInstance().getReference("Peers/"+peer.UID);  //their ref

                //set peer to paths(ref)
                myRef.push().setValue(peer);
                peersRef.push().setValue(peer);

                //remove from request folder
                DeleteRequest(peer);
                acceptBtn.setText("ACCEPTED");

            }
        });

        return listViewItem;

    }

    private void DeleteRequest(final Peer peer)  //peer object from view
    {

        peerRequestsRef = FirebaseDatabase.getInstance().getReference("Requests/"+uid);
        peerRequestsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {   //whole requests/uid node

                for(DataSnapshot childShot: dataSnapshot.getChildren())      //child node
                {

                    final Peer thisPeer = childShot.getValue(Peer.class);           //peer object  from db
                    if(thisPeer.UID.equals(peer.UID))
                    {

                        DatabaseReference childRef = childShot.getRef();           //child node ref[peer]
                        childRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {       //deletion
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful())
                                {

                                    Bundle bundle = new Bundle();
                                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID,"Peer: "+thisPeer.UID);
                                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,"Peer: "+thisPeer.EmailAddress);

                                    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,bundle);   //firebase log

                                }
                                else
                                    {

                                        Bundle bundle = new Bundle();
                                        bundle.putString(FirebaseAnalytics.Param.ITEM_ID,"Peer: "+thisPeer.UID);
                                        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,"Peer: "+thisPeer.EmailAddress);
                                        bundle.putString(FirebaseAnalytics.Param.CONTENT,"Peer: "+ task.getResult());

                                        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,bundle);   //firebase log
                                    }

                            }
                        });
                        break;

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}

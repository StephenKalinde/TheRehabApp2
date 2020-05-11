package com.example.therehabapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PeerRequests extends AppCompatActivity {

    private ListView requestListView;
    private Toolbar mToolbar;

    private DatabaseReference dbRef;
    private FirebaseAuth auth;
    private String uid;

    private RequestAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peer_requests);

        requestListView =(ListView) findViewById(R.id.request_list_view);
        mToolbar =(Toolbar) findViewById(R.id.request_toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Requests");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();
        uid = auth.getUid();
        dbRef= FirebaseDatabase.getInstance().getReference("Requests/"+uid);

        ArrayList<Peer> peerRequests =GetRequests();
        adapter = new RequestAdapter(PeerRequests.this,peerRequests);
        requestListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private ArrayList<Peer> GetRequests()
    {

        final ArrayList<Peer> requests = new ArrayList<>();   //empty

        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Peer peer = dataSnapshot.getValue(Peer.class);    //peer node
                Log.d("DebuggMe: ",peer.UID);
                requests.add(peer);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        return requests;

    }


}

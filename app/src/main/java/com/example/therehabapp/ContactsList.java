package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.therehabapp.Messaging.MessagingProtocols;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ContactsList extends AppCompatActivity{

    protected Toolbar mToolbar;
    private SwipeRefreshLayout myRefreshLayout;

    private ArrayList<Peer> myArrayList;
    private ArrayList<User> myUserPeerArrayList;
    private ArrayList<User> allUsersList;
    private ArrayList<User> allPeersList;

    private DatabaseReference dbRef;
    private FirebaseAuth mAuth;
    private DatabaseReference mRefUsers;

    private ListView myListView;

    private UserList adapter;

    private static  String LOG = "Refresh";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_list_view);

        myArrayList= new ArrayList<>();
        myUserPeerArrayList = new ArrayList<>();

        mAuth= FirebaseAuth.getInstance();
        String uid = mAuth.getUid();

        dbRef= FirebaseDatabase.getInstance().getReference("Peers/"+uid);
        mRefUsers= FirebaseDatabase.getInstance().getReference("Users");

        mToolbar =(Toolbar) findViewById(R.id.contacts_list_toolbar);
        myListView = (ListView) findViewById(R.id.my_list_view);
        myRefreshLayout =(SwipeRefreshLayout) findViewById(R.id.refresh_layout);

        myRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Log.i(LOG_TAG, "refresh called");
                //onStart();

                UserList adapter = new UserList(ContactsList.this, allPeersList);
                myListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                myRefreshLayout.setRefreshing(false);
            }
        });

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Peers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String userEmail= allPeersList.get(position).EmailAddress;
                String userName= allPeersList.get(position).Name;
                String peerUid = allPeersList.get(position).UID;

                String myUid = mAuth.getUid();

                String inboxId= QueryFolder(myUid,peerUid);

                Intent intent = new Intent(ContactsList.this, NewMessage.class);

                intent.putExtra("userEmail", userEmail);
                intent.putExtra("userName", userName);
                intent.putExtra("inboxid",inboxId);

                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId()){

            case android.R.id.home:
                onBackPressed();
                return true;

        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onStart() {
        super.onStart();

                allUsersList= GetAllUsers();
                allPeersList = GetAllPeers();

                adapter = new UserList(ContactsList.this, allPeersList);
                myListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

    }

    private ArrayList<User> GetAllPeers()
    {

        final ArrayList<Peer> peerPeerList = new ArrayList<>();
        final ArrayList<User> peerUserList= new ArrayList<>();
        final ArrayList<User> allUsers= GetAllUsers();

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot user: dataSnapshot.getChildren())
                {

                    Peer userPeer = user.getValue(Peer.class);
                    peerPeerList.add(userPeer);

                }

                for(Peer user: peerPeerList)
                {
                    for(int i =0; i< allUsers.size(); i++){

                        if(user.EmailAddress.equals(allUsers.get(i).EmailAddress))
                        {
                            peerUserList.add(allUsers.get(i));
                        }

                    }
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return peerUserList;

    }


    private ArrayList<User> GetAllUsers()
    {

        final ArrayList<User> allUsers= new ArrayList<>();

        mRefUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot user : dataSnapshot.getChildren())
                {

                    User myUser= user.getValue(User.class);
                    allUsers.add(myUser);

                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        allUsersList=allUsers;
        return allUsers;

    }

    private String InboxID="";

    public String QueryFolder(final String uid1,final String uid2)
    {

        /**database refs for the inbox ids to each user **/

        DatabaseReference uid1Ref =FirebaseDatabase.getInstance().getReference("InboxIDs/"+uid1);

        final String combo1 =uid1+uid2;
        final String combo2 = uid2+uid1;

        uid1Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot idSnapshot: dataSnapshot.getChildren())
                {

                    String inboxId = idSnapshot.getValue(String.class);

                    // if the id in db is equal to uid1+uid2

                    if(inboxId.equals(combo1))
                    {

                        InboxID = combo1; // inbox uid

                    }

                    //if the is in db is equal to uid2+uid1
                    if(inboxId.equals(combo2))
                    {

                        InboxID= combo2;  //inbox uid

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return InboxID;

    }





}

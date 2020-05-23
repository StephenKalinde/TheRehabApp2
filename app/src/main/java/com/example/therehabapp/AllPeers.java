package com.example.therehabapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class AllPeers extends AppCompatActivity {

    private Toolbar mToolBar;
    private EditText searchBox;
    private Button searchBtn;
    private ListView peersListView;

    private ArrayList<User> peerFound;
    private ArrayList<User> allUsersList;
    private ArrayList<User> allMyPeersList;

    private PeersList adapter;

    private DatabaseReference mRefPeers;
    private DatabaseReference mRefUsers;
    private FirebaseAuth mAuth;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //auth and db refs
        mAuth= FirebaseAuth.getInstance();
        uid= mAuth.getUid();
        mRefPeers = FirebaseDatabase.getInstance().getReference("/Peers/"+uid);
        mRefUsers = FirebaseDatabase.getInstance().getReference("/Users/");

        //init peers list and fill
        allMyPeersList = new ArrayList<>();
        GetAllPeers();

        //set views
        setContentView(R.layout.all_peers);

        mToolBar =(Toolbar) findViewById(R.id.all_peers_toolbar);
        searchBox=(EditText) findViewById(R.id.peers_search_box);
        searchBtn=(Button) findViewById(R.id.peers_search_btn);
        peersListView=(ListView) findViewById(R.id.peers_list_view);

        searchBox.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Peers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //onclick events

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressDialog progressDialogBox= new ProgressDialog(AllPeers.this, R.style.MyDialogTheme);
                progressDialogBox.setTitle("Search");
                progressDialogBox.setMessage("Searching...");
                progressDialogBox.setCancelable(false);
                progressDialogBox.show();

                peerFound = SearchUserByEmail(searchBox.getText().toString());

                if(peerFound.size()>0)
                {

                    boolean isPeer=false;

                    for(int j=0 ; j< allMyPeersList.size(); j++)
                    {
                        if(allMyPeersList.get(j).EmailAddress.equals(peerFound.get(0).EmailAddress))
                        {
                            isPeer=true;
                            break;
                        }
                    }

                    if(isPeer==true)
                    {

                        adapter = new PeersList(AllPeers.this,peerFound,"Remove");
                        peersListView.setAdapter(adapter);

                    }

                    else{

                        adapter = new PeersList(AllPeers.this,peerFound,"Add");
                        peersListView.setAdapter(adapter);
                    }


                    progressDialogBox.cancel();
                    Toast.makeText(AllPeers.this, "Search Done", Toast.LENGTH_SHORT).show();

                }

                else{


                    progressDialogBox.cancel();
                    Toast.makeText(AllPeers.this, "User Not found.", Toast.LENGTH_LONG).show();

                }

            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        
    }

    @Override
    protected void onStart() {
        super.onStart();

        final ProgressDialog progressDialogBox = new ProgressDialog(AllPeers.this, R.style.MyDialogTheme);
        progressDialogBox.setTitle("Loading");
        progressDialogBox.setCancelable(false);
        progressDialogBox.show();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                PeersList adapter=new PeersList(AllPeers.this,allMyPeersList,"Remove");
                peersListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                progressDialogBox.cancel();

            }
        },3000);

    }

    private void GetAllPeers()
    {

        final ArrayList<User> allUsers= GetAllUsers();

        mRefPeers.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Peer peer = dataSnapshot.getValue(Peer.class);

                for(int i =0; i< allUsers.size(); i++){

                    if(peer.EmailAddress.equals(allUsers.get(i).EmailAddress))
                    {
                        allMyPeersList.add(allUsers.get(i));
                    }

                }

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

    }

    private ArrayList<User> GetAllUsers()
    {

        final ArrayList<User> allUsers= new ArrayList<>();

        mRefUsers.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                User myUser= dataSnapshot.getValue(User.class);
                allUsers.add(myUser);

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

        allUsersList=allUsers;
        return allUsers;

    }

    private ArrayList<User> SearchUserByEmail(String userEmail)
    {

        ArrayList<User> userFound= new ArrayList<>();
        ArrayList<User> allUsers= allUsersList;

        String searchString = userEmail.trim();


        for(int i= 0; i< allUsers.size(); i++)
        {

            if(searchString.equals(allUsers.get(i).EmailAddress))
            {

                userFound.add(allUsers.get(i));
                return userFound;

            }

        }

        return userFound;

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

}

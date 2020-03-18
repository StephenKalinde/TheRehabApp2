package com.example.therehabapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
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
        setContentView(R.layout.all_peers);

        mAuth= FirebaseAuth.getInstance();
        uid= mAuth.getUid();

        mRefPeers = FirebaseDatabase.getInstance().getReference("Peers/"+uid);
        mRefUsers = FirebaseDatabase.getInstance().getReference("Users");

        mToolBar =(Toolbar) findViewById(R.id.all_peers_toolbar);
        searchBox=(EditText) findViewById(R.id.peers_search_box);
        searchBtn=(Button) findViewById(R.id.peers_search_btn);
        peersListView=(ListView) findViewById(R.id.peers_list_view);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Peers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

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

        allMyPeersList= GetAllPeers();

        PeersList adapter=new PeersList(AllPeers.this,allMyPeersList,"Remove");
        peersListView.setAdapter(adapter);

    }

    private ArrayList<User> GetAllPeers()
    {

        final ArrayList<String> peerStringList= new ArrayList<>();
        final ArrayList<Peer> peerPeerList = new ArrayList<>();
        final ArrayList<User> peerUserList= new ArrayList<>();
        final ArrayList<User> allUsers= GetAllUsers();

        mRefPeers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot user: dataSnapshot.getChildren())
                {

                    //String userString = user.getValue(String.class);
                    //peerStringList.add(userString);

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

                /**
                for(String userEmail : peerStringList)
                {
                    for(int i= 0; i< allUsers.size(); i++)
                    {
                        if(userEmail.equals(allUsers.get(i).EmailAddress))
                        {
                            peerUserList.add(allUsers.get(i));
                        }
                    }
                }

                 **/
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

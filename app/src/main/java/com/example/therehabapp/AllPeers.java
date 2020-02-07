package com.example.therehabapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

    private ArrayList<User> allPeersUserList;
    private ArrayList<User> userFoundList;

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

        allPeersUserList = new ArrayList<>();
        userFoundList = new ArrayList<>();

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

                ArrayList<User> peerFound = SearchUserByEmail(searchBox.getText().toString());

                if(peerFound.size()>0)
                {

                    adapter = new PeersList(AllPeers.this,peerFound);
                    peersListView.setAdapter(adapter);

                    progressDialogBox.cancel();
                    Toast.makeText(AllPeers.this, "Search Done", Toast.LENGTH_SHORT).show();

                }

                else{


                    progressDialogBox.cancel();
                    Toast.makeText(AllPeers.this, "User Not found.", Toast.LENGTH_LONG).show();

                }

            }
        });

        /**peersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                User myUser=null;

                if(allPeersUserList.size()>0)
                {
                    myUser= allPeersUserList.get(position);
                }

                else{

                    myUser = peerFound2.get(position);
                }

                Intent intent = new Intent(AllPeers.this, PeerProfile.class);
                intent.putExtra("Username", myUser.Name).putExtra("UserEmail", myUser.EmailAddress);
                intent.putExtra("UserCity",myUser.City);

                startActivity(intent);
            }
        }); **/
    }

    @Override
    protected void onStart() {
        super.onStart();

        PeersList adapter=new PeersList(AllPeers.this,GetAllPeers());
        peersListView.setAdapter(adapter);

    }

    private ArrayList<User> GetAllPeers()
    {
        allPeersUserList.clear();
        final ArrayList<String>  allPeersEmailList = new ArrayList<>();

        mRefPeers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot userSnapshot: dataSnapshot.getChildren())
                {
                    String userEmail= userSnapshot.getValue(String.class);
                    allPeersEmailList.add(userEmail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRefUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot userSnapShot: dataSnapshot.getChildren())
                {
                    User user = userSnapShot.getValue(User.class);
                    for(String email: allPeersEmailList)
                    {

                        if(email.equals(user.EmailAddress)){
                            allPeersUserList.add(user);
                        }
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return allPeersUserList;
    }

    private ArrayList<User> SearchUserByEmail(String userSearch)
    {
         String searchString = userSearch.trim();

         ArrayList<User> allPeersList= allPeersUserList;
         userFoundList.clear();

         for(User myUser: allPeersList)
         {

             String userEmail= myUser.EmailAddress;

             if(userEmail.equals(searchString))
             {

                 userFoundList.add(myUser);

             }

         }

        return userFoundList;
    }
}

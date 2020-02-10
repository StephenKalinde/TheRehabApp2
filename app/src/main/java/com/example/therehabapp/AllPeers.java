package com.example.therehabapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
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
    private ArrayList<String> peerStringList;

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

        peerStringList= new ArrayList<>();


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

       /** peersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                User myUser=null;

                if(peerFound.size()>0)
                {
                    myUser = peerFound.get(position);
                }

                else
                {
                    myUser= allPeersUserList.get(position);
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

        peerStringList.clear();
        ArrayList<User> peerUserList= new ArrayList<>();
        ArrayList<User> allUsers= GetAllUsers();

        //dbreef to peers and add to array
        mRefPeers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot user: dataSnapshot.getChildren())
                {

                    String userString = user.getValue(String.class);
                    peerStringList.add(userString);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Toast.makeText(AllPeers.this, ""+peerStringList.size(),Toast.LENGTH_LONG).show();

        //convert user strings to user.class and place into arr

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

        return allUsers;

    }

    private ArrayList<User> SearchUserByEmail(String userEmail)
    {

        ArrayList<User> userFound= new ArrayList<>();
        ArrayList<User> allUsers= GetAllUsers();

        String searchString = userEmail.trim();

        for(User user: allUsers)
        {

            if(searchString.equals(user.EmailAddress))
            {

                userFound.add(user);
                break;

            }

        }

        return userFound;

    }


}

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
    private ArrayList<User> allUserList;
    private ArrayList<User> usersFoundList;
    private ArrayList<User> allUserList_1;
    private PeersList adapter;

    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_peers);

        mRef= FirebaseDatabase.getInstance().getReference("Users");

        mToolBar =(Toolbar) findViewById(R.id.all_peers_toolbar);
        searchBox=(EditText) findViewById(R.id.peers_search_box);
        searchBtn=(Button) findViewById(R.id.peers_search_btn);
        peersListView=(ListView) findViewById(R.id.peers_list_view);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Peers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        usersFoundList = new ArrayList<>();

        allUserList_1= new ArrayList<>();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressDialog progressDialogBox= new ProgressDialog(AllPeers.this, R.style.MyDialogTheme);
                progressDialogBox.setTitle("Search");
                progressDialogBox.setMessage("Searching...");
                progressDialogBox.setCancelable(false);
                progressDialogBox.show();

                allUserList=GetAllUsers();
                usersFoundList.clear();

                for(User user: allUserList)
                {
                    if(user.EmailAddress.equals(searchBox.getText().toString().trim()))
                    {

                        usersFoundList.add(user);

                    }

                }

                if(usersFoundList.size() == 0)
                {

                    progressDialogBox.cancel();
                    Toast.makeText(AllPeers.this, "User Does not Exit", Toast.LENGTH_LONG).show();

                }

                else{

                    progressDialogBox.cancel();

                    adapter = new PeersList(AllPeers.this,usersFoundList);
                    peersListView.setAdapter(adapter);
                    Toast.makeText(AllPeers.this, "Search Done", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        usersFoundList=GetAllUsers();
        PeersList adapter=new PeersList(AllPeers.this,usersFoundList/**,"Remove"**/);
        peersListView.setAdapter(adapter);
    }

    private ArrayList<User> GetAllUsers(){

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                allUserList_1.clear();

                for(DataSnapshot usersSnapShot: dataSnapshot.getChildren())
                {
                    User user= usersSnapShot.getValue(User.class);
                    allUserList_1.add(user);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return allUserList_1;
    }
}

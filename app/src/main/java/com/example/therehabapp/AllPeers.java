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
        allUserList = new ArrayList<>();

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressDialog progressDialogBox= new ProgressDialog(AllPeers.this, R.style.MyDialogTheme);
                progressDialogBox.setTitle("Search");
                progressDialogBox.setMessage("Searching...");
                progressDialogBox.setCancelable(false);
                progressDialogBox.show();
                //get all users
                //UsersHandler usersHandler= new UsersHandler();
                //allUserList= usersHandler.GetAllUsers();

                GetAllUsers();

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
                    Toast.makeText(AllPeers.this, "User Does not Exit", Toast.LENGTH_LONG);
                }

                else{

                    progressDialogBox.cancel();
                    PeersList adapter = new PeersList(AllPeers.this,usersFoundList);
                    peersListView.setAdapter(adapter);
                    //peersListView.getAdapter().notify();

                }

            }
        });

       /** peersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?>parent, View view, int position, long id) {

                String userEmail= allUserList.get(position).EmailAddress;
                String userName= allUserList.get(position).Name;

                Intent intent = new Intent(AllPeers.this, ProfileView.class);

                intent.putExtra("userEmail", userEmail);
                intent.putExtra("userName", userName);

                startActivity(intent);
            }
        }); **/


    }

    @Override
    protected void onStart() {
        super.onStart();

        PeersList adapter=new PeersList(AllPeers.this, usersFoundList);
        peersListView.setAdapter(adapter);
    }

    private void GetAllUsers(){

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                allUserList.clear();
                for(DataSnapshot usersSnapShot: dataSnapshot.getChildren())
                {
                    User user= usersSnapShot.getValue(User.class);
                    allUserList.add(user);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

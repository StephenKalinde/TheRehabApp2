package com.example.therehabapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PeerProfile extends AppCompatActivity {

    private TextView nameView;
    private Toolbar mToolBar;
    private FloatingActionButton newMessageBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peer_profile_view);

        Intent intent = getIntent();

        final String userName= intent.getStringExtra("UserName");
        final String userEmail = intent.getStringExtra("UserEmail");
        final String userId = intent.getStringExtra("UserId");


        nameView = (TextView) findViewById(R.id.user_name_view);
        mToolBar = (Toolbar) findViewById(R.id.peer_profile_toolbar);
        newMessageBtn= (FloatingActionButton) findViewById(R.id.new_message_btn);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameView.setText(userName);

        String uid = FirebaseAuth.getInstance().getUid();

        final String inboxId=QueryFolder(uid,userId);

        newMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PeerProfile.this,NewMessage.class);
                intent.putExtra("userName",userName);
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

    private String InboxID="";

    public String QueryFolder(final String uid1,final String uid2)
    {

        /**database refs for the inbox ids to each user **/

        DatabaseReference uid1Ref = FirebaseDatabase.getInstance().getReference("InboxIDs/"+uid1);

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

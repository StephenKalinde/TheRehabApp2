package com.example.therehabapp;

import android.os.Bundle;
import android.os.Debug;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ContactsList extends AppCompatActivity {

    protected Toolbar mToolbar;
    private ArrayList<User> myArrayList;
    private DatabaseReference dbRef;

    private ListView myListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_list_view);

        myArrayList= new ArrayList<>();

        dbRef= FirebaseDatabase.getInstance().getReference("Users");

        mToolbar =(Toolbar) findViewById(R.id.contacts_list_toolbar);
        myListView = (ListView) findViewById(R.id.my_list_view);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Peers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

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

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                myArrayList.clear();
                for(DataSnapshot userSnapshot: dataSnapshot.getChildren())
                {
                    User myUser= userSnapshot.getValue(User.class);
                    myArrayList.add(myUser);
                }

                UserList adapter = new UserList(ContactsList.this, myArrayList);
                myListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

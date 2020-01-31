package com.example.therehabapp;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseHandler {

    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    public ArrayList<User> allUsers;

    public FirebaseHandler(){

        mAuth= FirebaseAuth.getInstance();

    }

    public ArrayList<User> GetAllUsers()
    {

        allUsers= new ArrayList();

        db=FirebaseDatabase.getInstance();
        DatabaseReference myRef = db.getReference("Users");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allUsers.clear();

                for(DataSnapshot postSnapShot: dataSnapshot.getChildren())
                {
                    User myUser= postSnapShot.getValue(User.class);
                    allUsers.add(myUser);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return allUsers;

    }


}

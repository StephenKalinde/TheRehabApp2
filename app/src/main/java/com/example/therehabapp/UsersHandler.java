package com.example.therehabapp;

import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class UsersHandler {

    private DatabaseReference dbRef;
    private ArrayList<User> usersList;

    public UsersHandler(){

        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        usersList = new ArrayList<>();
    }

    public ArrayList<User> GetAllUsers()
    {

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                usersList.clear();
                for(DataSnapshot usersSnapShot: dataSnapshot.getChildren())
                {
                    User user= usersSnapShot.getValue(User.class);
                    usersList.add(user);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return usersList;

    }
}

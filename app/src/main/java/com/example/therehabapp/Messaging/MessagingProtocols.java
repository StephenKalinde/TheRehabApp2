package com.example.therehabapp.Messaging;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MessagingProtocols {

    private DatabaseReference inboxesRef;
    private DatabaseReference inboxIdsRef;

    public MessagingProtocols()
    {
        //inboxesRef = FirebaseDatabase.getInstance().getReference("Inboxes");
       // inboxIdsRef = FirebaseDatabase.getInstance().getReference("InboxIDs");
    }
    private String InboxID="";

    public String QueryFolder(final String uid1,final String uid2)
    {

        /**database refs for the inbox ids to each user **/

        DatabaseReference uid1Ref =FirebaseDatabase.getInstance().getReference("InboxIDs/"+uid1);

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

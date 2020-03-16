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
        inboxesRef = FirebaseDatabase.getInstance().getReference("Inboxes");
        inboxIdsRef = FirebaseDatabase.getInstance().getReference("InboxIDs");
    }

    private boolean trigger1 = false;
    private boolean trigger2 = false;
    private String InboxID;

    public String QueryFolder(final String uid1,final String uid2)
    {

        /**database refs for the inbox ids to each user **/

        DatabaseReference uid1Ref =inboxIdsRef.child("uid1");
        DatabaseReference uid2Ref =inboxIdsRef.child("uid2");

        uid1Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot idSnapshot: dataSnapshot.getChildren())
                {

                    String inboxId = idSnapshot.getValue(String.class);

                    // if the id in db is equal to uid1+uid2
                    if(inboxId.equals(uid1+ uid2))
                    {

                        trigger1 = true;
                        InboxID= uid1+uid2; // inbox uid

                    }

                    //if the is in db is equal to uid2+uid1
                    if(inboxId.equals(uid2+uid1))
                    {

                        trigger1 = true;
                        InboxID= uid2+uid1;  //inbox uid

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        uid2Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot idSnapshot: dataSnapshot.getChildren())
                {

                    String inboxId = idSnapshot.getValue(String.class);

                    //if inbox id is InboxID
                    if(inboxId.equals(InboxID))
                    {

                        trigger2 = true;

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if(trigger1==true  && trigger2 == true) {

            return InboxID;

        }

        return "not working...";

    }
}

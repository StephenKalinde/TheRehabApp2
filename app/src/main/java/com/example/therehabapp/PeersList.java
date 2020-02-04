package com.example.therehabapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PeersList  extends ArrayAdapter<User> {

    private Activity context;
    private List<User> userList;
    private DatabaseReference dbRef;
    private FirebaseAuth mAuth;
    private String uid;

    public PeersList(Activity context, List<User> userList)
    {
        super(context , R.layout.list_view_item, userList);
        this.context= context;
        this.userList = userList;
        mAuth= FirebaseAuth.getInstance();
        uid= mAuth.getUid();

        dbRef= FirebaseDatabase.getInstance().getReference("Peers/"+uid);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem= inflater.inflate(R.layout.peers_list_view_item,null,true);

        TextView userNameView =(TextView)  listViewItem.findViewById(R.id.user_name_view);
        TextView userEmailView = (TextView) listViewItem.findViewById(R.id.user_email_view);

        final Button addBtn= (Button) listViewItem.findViewById(R.id.add_btn);

        final User myUser= userList.get(position);

        userNameView.setText(myUser.Name);
        userEmailView.setText(myUser.EmailAddress);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i=0; i<1; i++) {
                    if (addBtn.getText().equals("Add")) {

                        dbRef.push().setValue(myUser.EmailAddress);
                        addBtn.setText("Remove");
                        break;

                    }

                    if (addBtn.getText().equals("Remove")) {
                        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for(DataSnapshot user: dataSnapshot.getChildren())
                                {
                                    String userEmail = user.getValue(String.class);

                                    if(myUser.EmailAddress.equals(userEmail))
                                    {
                                        user.getRef().removeValue();
                                        addBtn.setText("Add");

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;
                    }

                }
                /**if(addBtn.getText().equals("Remove"))
                {

                    ArrayList<String> allPeersEmails;
                    //get list of all peers for the respective
                    allPeersEmails = GetAllUsers();
                    //search for the respective user by user.EmailAddress
                    for (int i = 0; i < allPeersEmails.size(); i++)
                    {
                        if(allPeersEmails.get(i).equals(myUser.EmailAddress))
                        {

                            int index= i;
                            RemovePeer(index);

                        }
                    }

                    addBtn.setText("Add");

                    /**dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for(DataSnapshot user: dataSnapshot.getChildren())
                            {
                                String userEmail = user.getValue(String.class);

                                if(myUser.EmailAddress.equals(userEmail))
                                {
                                    user.getRef().removeValue();
                                    addBtn.setText("Add");

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    }); **/
                    //remove from db here
                  /**  dbRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot user: dataSnapshot.getChildren())
                            {
                                String userEmail = user.getValue(String.class);

                                if(myUser.EmailAddress.equals(userEmail))
                                {
                                    user.getRef().removeValue();

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                } **/
            }
        });

        return listViewItem;
    }

    private ArrayList<String> GetAllUsers()
    {
        final ArrayList<String> peersList= new ArrayList<>();

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for( DataSnapshot peerSnapshot: dataSnapshot.getChildren())
                {

                    String peer= peerSnapshot.getValue(String.class);
                    peersList.add(peer);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return peersList;
    }

    private void RemovePeer(final int index)
    {

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int count = 0;
                for(DataSnapshot peerSnapshot: dataSnapshot.getChildren())
                {
                    if(index == count)
                    {
                        peerSnapshot.getRef().removeValue();
                    }

                    count++;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

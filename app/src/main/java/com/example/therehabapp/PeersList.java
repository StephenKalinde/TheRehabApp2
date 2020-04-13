package com.example.therehabapp;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.List;

public class PeersList  extends ArrayAdapter<User> {

    private Activity context;
    private List<User> userList;
    private DatabaseReference dbRef;
    private FirebaseAuth mAuth;
    private String uid;
    private String myEmailAddress;
    private String BtnText;

    public PeersList(Activity context, List<User> userList,String BtnText)
    {
        super(context , R.layout.list_view_item, userList);
        this.context = context;
        this.userList = userList;
        this.BtnText = BtnText;
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();
        myEmailAddress= mAuth.getCurrentUser().getEmail();



        dbRef= FirebaseDatabase.getInstance().getReference("Peers/"+uid);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem= inflater.inflate(R.layout.peers_list_view_item,null,true);

        TextView userNameView =(TextView)  listViewItem.findViewById(R.id.user_name_view);
        TextView userEmailView = (TextView) listViewItem.findViewById(R.id.user_email_view);
        RelativeLayout myLayout= (RelativeLayout) listViewItem.findViewById(R.id.layout_view);

        final Button addBtn= (Button) listViewItem.findViewById(R.id.add_btn);
        addBtn.setText(BtnText);

        final User myUser= userList.get(position);

        userNameView.setText(myUser.Name);
        userEmailView.setText(myUser.EmailAddress);

        myLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail= userList.get(position).EmailAddress;
                String userName= userList.get(position).Name;
                String userID=  userList.get(position).UID;

                Intent intent = new Intent (getContext(), PeerProfile.class);
                intent.putExtra("UserName",userName);
                intent.putExtra("UserEmail",userEmail);
                intent.putExtra("UserId",userID);
                getContext().startActivity(intent);

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i=0; i<1; i++) {

                    if (addBtn.getText().equals("Add")) {

                        String userEmailAddress= myUser.EmailAddress;
                        String inboxId= myEmailAddress+userEmailAddress;

                        Peer myPeer = new Peer(userEmailAddress,inboxId);

                        dbRef.push().setValue(myPeer);

                        Peer thisPeer = new Peer(myEmailAddress,inboxId); //my reference to them as a peer

                        DatabaseReference dbRefAlt= FirebaseDatabase.getInstance().getReference("Peers/"+myUser.UID);

                        dbRefAlt.push().setValue(thisPeer);

                        //create folder for inbox
                        String myUid =FirebaseAuth.getInstance().getUid();
                        String userUid = myUser.UID;

                        String inboxID= myUid+userUid;

                        //DatabaseReference dbInbox = FirebaseDatabase.getInstance().getReference("Inboxes/"+inboxID);

                        DatabaseReference myDbInboxId = FirebaseDatabase.getInstance().getReference("InboxIDs/"+myUid);
                        DatabaseReference userDbInboxId =FirebaseDatabase.getInstance().getReference("InboxIDs/"+userUid);

                        myDbInboxId.push().setValue(inboxID);
                        userDbInboxId.push().setValue(inboxID);

                        addBtn.setText("Remove");
                        break;

                    }

                    if (addBtn.getText().equals("Remove")) {
                        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for(DataSnapshot user: dataSnapshot.getChildren())
                                {
                                    Peer myPeer = user.getValue(Peer.class);

                                    if(myUser.EmailAddress.equals(myPeer.EmailAddress))
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

                        DatabaseReference dbRefAlt= FirebaseDatabase.getInstance().getReference("Peers/"+myUser.UID);

                        dbRefAlt.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for(DataSnapshot user : dataSnapshot.getChildren())
                                {
                                    Peer myPeer = user.getValue(Peer.class);
                                    if(myEmailAddress.equals(myPeer.EmailAddress))
                                    {
                                        user.getRef().removeValue();
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

            }
        });

        return listViewItem;

    }

}

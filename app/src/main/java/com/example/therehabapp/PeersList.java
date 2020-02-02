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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PeersList  extends ArrayAdapter<User> {

    private Activity context;
    private List<User> userList;
    private DatabaseReference dbRef;
    private FirebaseAuth mAuth;

    public PeersList(Activity context, List<User> userList)
    {
        super(context , R.layout.list_view_item, userList);
        this.context= context;
        this.userList = userList;
        mAuth= FirebaseAuth.getInstance();
        String uid= mAuth.getUid();

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

                dbRef.push().setValue(myUser.EmailAddress);
                addBtn.setText("Added");
            }
        });

        return listViewItem;
    }
}

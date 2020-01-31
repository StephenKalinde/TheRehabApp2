package com.example.therehabapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class UserList extends ArrayAdapter<User> {

    private Activity context;
    private List<User> userList;

    public UserList(Activity context, List<User> userList)
    {
        super(context , R.layout.list_view_item, userList);
        this.context= context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem= inflater.inflate(R.layout.list_view_item,null,true);

        TextView userNameView =(TextView)  listViewItem.findViewById(R.id.user_name_view);
        TextView userEmailView = (TextView) listViewItem.findViewById(R.id.user_email_view);

        User myUser= userList.get(position);

        userNameView.setText(myUser.Name);
        userEmailView.setText(myUser.EmailAddress);

        return listViewItem;

    }
}

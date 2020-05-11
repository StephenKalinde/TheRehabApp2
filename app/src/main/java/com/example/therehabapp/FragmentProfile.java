package com.example.therehabapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.Serializable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentProfile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<String> inboxIdsList;
    private ArrayList<TopMessage> topMessagesList;

    private OnFragmentInteractionListener mListener;

    public FragmentProfile(){}

    public FragmentProfile(ArrayList<String> inboxIdsList) {

        this.inboxIdsList = inboxIdsList;
        topMessagesList = GetTopMessages(inboxIdsList);

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProfile newInstance(String param1, String param2) {
        FragmentProfile fragment = new FragmentProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private Button messagesBtn;
    private Button peersBtn;
    private Button requestsBtn;
    private Button journalBtn;
    private TextView nameView;
    private String userName;

    private ArrayList<String> inboxIDs;
    private ArrayList<String> inboxIDs2;
    private ArrayList<TopMessage> topMessages;

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference inboxIdsRef;
    private String uid;
    private String myEmailAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_fragment_profile, container, false);

        inboxIDs = new ArrayList<>();
        inboxIDs = new ArrayList<>();
        topMessages = new ArrayList<>();

        auth = FirebaseAuth.getInstance();
        uid = auth.getUid();
        myEmailAddress = auth.getCurrentUser().getEmail();

        firebaseDatabase = FirebaseDatabase.getInstance();
        inboxIdsRef = firebaseDatabase.getReference("InboxIDs/"+uid);

        messagesBtn = (Button) myView.findViewById(R.id.messages_btn);
        peersBtn= (Button) myView.findViewById(R.id.peers_btn);
        requestsBtn = (Button) myView.findViewById(R.id.requests_btn);
        journalBtn = (Button) myView.findViewById(R.id.my_journal_btn);
        nameView = (TextView) myView.findViewById(R.id.user_name_view);

        DatabaseReference userNameRef = FirebaseDatabase.getInstance().getReference("Users/"+uid+"/Name");

        userNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userName = dataSnapshot.getValue(String.class);

                nameView.setText(userName);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //ArrayList<String> inboxIdsList= GetInboxIds();

        messagesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),Messages.class);
                intent.putExtra("key",topMessagesList);
                startActivity(intent);

            }
        });

        peersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), AllPeers.class);
                startActivity(intent);
            }
        });

        requestsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),PeerRequests.class);
                startActivity(intent);

            }
        });

        journalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),Journal.class);
                startActivity(intent);
            }
        });

        return myView;

    }


    /**
     * Get TopMessages
     */
    private ArrayList<TopMessage> GetTopMessages(ArrayList<String> idsList)
    {
        final ArrayList<TopMessage> topMessagesArr = new ArrayList<>();

        for(int i =0 ; i<idsList.size();i++)
        {

            String id = idsList.get(i);
            DatabaseReference topMsgRef = FirebaseDatabase.getInstance().getReference("TopMessages/"+id);
            topMsgRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    TopMessage topMessage = dataSnapshot.getValue(TopMessage.class);
                    topMessagesArr.add(topMessage);

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        return topMessagesArr;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

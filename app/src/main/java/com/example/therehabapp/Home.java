package com.example.therehabapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.health.ServiceHealthStats;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.DateSorter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.therehabapp.Functions.DateSplit;
import com.example.therehabapp.Functions.ScheduleCalculations;
import com.example.therehabapp.Functions.ScheduleLog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.Console;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Home extends AppCompatActivity implements FragmentHome.OnFragmentInteractionListener ,FragmentAbout.OnFragmentInteractionListener, FragmentDisorders.OnFragmentInteractionListener,FragmentHappenings.OnFragmentInteractionListener, FragmentProfile.OnFragmentInteractionListener {

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch(menuItem.getItemId())
            {

                case R.id.nav_home:
                    FragmentHome homeFragment= new FragmentHome();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragments_container,homeFragment,"FragmentName");
                    fragmentTransaction.commit();
                    break;

                case R.id.nav_about:
                    FragmentAbout fragmentAbout = new FragmentAbout();
                    FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(R.id.fragments_container,fragmentAbout,"FragmentName");
                    fragmentTransaction2.commit();
                    break;

                case R.id.nav_disorders:
                    FragmentDisorders fragmentDisorders= new FragmentDisorders(myLayout);
                    FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.fragments_container,fragmentDisorders,"FragmentName");
                    fragmentTransaction3.commit();
                    break;

                case R.id.nav_profile:
                    FragmentProfile profileFragment= new FragmentProfile();
                    FragmentTransaction fragmentTransaction4 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction4.replace(R.id.fragments_container,profileFragment,"FragmentName");
                    fragmentTransaction4.commit();
                    break;

                case R.id.nav_happenings:
                    FragmentHappenings happeningsFragment= new FragmentHappenings();
                    FragmentTransaction fragmentTransaction5 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction5.replace(R.id.fragments_container,happeningsFragment,"FragmentName");
                    fragmentTransaction5.commit();
                    break;

            }

            return true;
        }
    };

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToogle;
    private Toolbar mToolBar;
    private NavigationView navigationView;

    private int myLayout= R.layout.fragment_fragment_disorders;

    private FirebaseDatabase firebaseDb;
    private DatabaseReference dbRef;
    private DatabaseReference inboxesRef;
    private FirebaseAuth auth;
    private String uid;

    private ArrayList<String> inboxIdsList;

    private ScheduleLog log;

    DateSplit[] myScheduleArray;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);

        log = new ScheduleLog();
        myScheduleArray = new DateSplit[12];

        inboxIdsList = new ArrayList<>();

        firebaseDb = FirebaseDatabase.getInstance();

        auth= FirebaseAuth.getInstance();
        uid = auth.getUid();
        dbRef = firebaseDb.getReference("Diagnoses/" + uid);


        //subscribe to topic {uid}
        FirebaseMessaging.getInstance().subscribeToTopic(uid).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {

                    Toast.makeText(Home.this, "Welcome To The Rehab",Toast.LENGTH_SHORT).show();

                }

                else{

                    Toast.makeText(Home.this, "",Toast.LENGTH_LONG).show();

                }

            }
        });

        BottomNavigationView bottomNav= (BottomNavigationView) findViewById(R.id.bottom_nav_bar);
        bottomNav.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_SELECTED);
        bottomNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        FragmentHome homeFragment= new FragmentHome();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragments_container,homeFragment,"FragmentName");
        fragmentTransaction.commit();

        mToolBar=(Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        mToogle= new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView=(NavigationView) findViewById(R.id.side_nav);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId())
                {

                    case R.id.faq:

                        Toast.makeText(Home.this,"Option Not Available At The Moment",Toast.LENGTH_LONG).show();
                        break;

                    case R.id.sign_out:

                        FirebaseMessaging.getInstance().unsubscribeFromTopic(uid);
                        auth.signOut();
                        Intent intent = new Intent(Home.this,SignUp.class);
                        intent.putExtra("Error","none");
                        startActivity(intent);
                        finishAffinity();
                        break;

                }

                return false;

            }
        });

    }

    @Override
    protected void onStart() {

        super.onStart();

        GetInboxIds();

        GetSchedule();

    }

    private void SeekLayout(final int logWeek)
    {

        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String diagnosis = dataSnapshot.getValue(String.class);
                SetLayout(diagnosis,logWeek);

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

    private void SetLayout(String diagnosis,int logWeek)
    {

        /** NB:set case 0: statements for all diagnoses **/

        if(diagnosis.equals("Depression"))
        {

            switch (logWeek)
            {

                case 1:
                    myLayout = R.layout.fragment_disorders_depression_1;
                    break;

                case 2:
                    myLayout = R.layout.fragment_disorders_depression_2;
                    break;

                case 3:
                    myLayout = R.layout.fragment_disorders_depression_3;
                    break;

                case 4:
                    myLayout = R.layout.fragment_disorders_depression_4;
                    break;

                case 5:
                    myLayout = R.layout.fragment_disorders_depression_5;
                    break;

                case 6:
                    myLayout = R.layout.fragment_disorders_depression_6;
                    break;

                case 7:
                    myLayout = R.layout.fragment_disorders_depression_7;
                    break;

                case 8:
                    myLayout = R.layout.fragment_disorders_depression_8;
                    break;

                case 9:
                    myLayout = R.layout.fragment_disorders_depression_9;
                    break;

                case 10:
                    myLayout = R.layout.fragment_disorders_depression_10;
                    break;

                case 11:
                    myLayout = R.layout.fragment_disorders_depression_11;
                    break;

                case 12:
                    myLayout = R.layout.fragment_disorders_depression_12;
                    break;

            }

        }

        if(diagnosis.equals("Eating Disorder"))
        {

            switch (logWeek)
            {
                case 1:
                    myLayout = R.layout.fragment_disorders_eating_1;
                    break;

                case 2:
                    myLayout = R.layout.fragment_disorders_eating_2;
                    break;

                case 3:
                    myLayout = R.layout.fragment_disorders_eating_3;
                    break;

                case 4:
                    myLayout = R.layout.fragment_disorders_eating_4;
                    break;

                case 5:
                    myLayout = R.layout.fragment_disorders_eating_5;
                    break;

                case 6:
                    myLayout = R.layout.fragment_disorders_eating_6;
                    break;

                case 7:
                    myLayout = R.layout.fragment_disorders_eating_7;
                    break;

                case 8:
                    myLayout = R.layout.fragment_disorders_eating_8;
                    break;

                case 9:
                    myLayout = R.layout.fragment_disorders_eating_9;
                    break;

                case 10:
                    myLayout = R.layout.fragment_disorders_eating_10;
                    break;

                case 11:
                    myLayout = R.layout.fragment_disorders_eating_11;
                    break;

                case 12:
                    myLayout = R.layout.fragment_disorders_eating_12;
                    break;
            }

        }

        if(diagnosis.equals("Addiction"))
        {

            switch (logWeek)
            {
                case 1:
                    myLayout = R.layout.fragment_disorders_addiction_1;
                    break;

                case 2:
                    myLayout = R.layout.fragment_disorders_addiction_2;
                    break;

                case 3:
                    myLayout = R.layout.fragment_disorders_addiction_3;
                    break;

                case 4:
                    myLayout = R.layout.fragment_disorders_addiction_4;
                    break;

                case 5:
                    myLayout = R.layout.fragment_disorders_addiction_5;
                    break;

                case 6:
                    myLayout = R.layout.fragment_disorders_addiction_6;
                    break;

                case 7:
                    myLayout = R.layout.fragment_disorders_addiction_7;
                    break;

                case 8:
                    myLayout = R.layout.fragment_disorders_addiction_8;
                    break;

                case 9:
                    myLayout = R.layout.fragment_disorders_addiction_9;
                    break;

                case 10:
                    myLayout = R.layout.fragment_disorders_addiction_10;
                    break;

                case 11:
                    myLayout = R.layout.fragment_disorders_addiction_11;
                    break;

                case 12:
                    myLayout = R.layout.fragment_disorders_addiction_12;
                    break;
            }

        }

        if(diagnosis.equals("Anxiety"))
        {

            switch (logWeek)
            {
                case 1:
                    myLayout = R.layout.fragment_fragment_disorders;
                    break;

                case 2:
                    myLayout = R.layout.fragment_disorders_anxiety_2;
                    break;

                case 3:
                    myLayout = R.layout.fragment_disorders_anxiety_3;
                    break;

                case 4:
                    myLayout = R.layout.fragment_disorders_anxiety_4;
                    break;

                case 5:
                    myLayout = R.layout.fragment_disorders_anxiety_5;
                    break;

                case 6:
                    myLayout = R.layout.fragment_disorders_anxiety_6;
                    break;

                case 7:
                    myLayout = R.layout.fragment_disorders_anxiety_7;
                    break;

                case 8:
                    myLayout = R.layout.fragment_disorders_anxiety_8;
                    break;

                case 9:
                    myLayout = R.layout.fragment_disorders_anxiety_9;
                    break;

                case 10:
                    myLayout = R.layout.fragment_disorders_anxiety_10;
                    break;

                case 11:
                    myLayout = R.layout.fragment_disorders_anxiety_11;
                    break;

                case 12:
                    myLayout = R.layout.fragment_disorders_anxiety_12;
                    break;
            }

        }

    }

    private void GetInboxIds()
    {

        DatabaseReference inboxIdsRef = FirebaseDatabase.getInstance().getReference("InboxIDs/"+uid);
        inboxIdsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot inboxIdShot: dataSnapshot.getChildren())
                {

                    String id = inboxIdShot.getValue(String.class);
                    inboxIdsList.add(id);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void GetSchedule()
    {

        String myUID = FirebaseAuth.getInstance().getUid();
        DatabaseReference logsRef = FirebaseDatabase.getInstance().getReference("ScheduleLogs/"+ myUID);

        logsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot shot: dataSnapshot.getChildren())
                {

                    ScheduleLog log = shot.getValue(ScheduleLog.class);

                    //get current date
                    long millis = System.currentTimeMillis();
                    java.sql.Date date= new java.sql.Date(millis);
                    String currentDateString= date.toString();
                    DateSplit dateSplit = new DateSplit(currentDateString);

                    //set layout
                    int logWeek = FindSchedule(log,dateSplit);
                    SeekLayout(logWeek);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private int FindSchedule(ScheduleLog log,DateSplit dateSplit)
    {

        int week =0;     //current log week init

        //order log
        DateSplit[] orderedLog = new DateSplit[12];
        orderedLog[0]=log.week1;
        orderedLog[1]=log.week2;
        orderedLog[2]=log.week3;
        orderedLog[3]=log.week4;
        orderedLog[4]=log.week5;
        orderedLog[5]=log.week6;
        orderedLog[6]=log.week7;
        orderedLog[7]=log.week8;
        orderedLog[8]=log.week9;
        orderedLog[9]=log.week10;
        orderedLog[10]=log.week11;
        orderedLog[11]=log.week12;

        //compare current date to log.week{i} && log.week{i+1}
        for(int i = 0; i < 12; i++)
        {

            if( i <= 10) {   //avoid index array out of bounds

                ScheduleCalculations calculations = new ScheduleCalculations();
                boolean isBefore = calculations.CompareDates(dateSplit, orderedLog[i + 1]);   //current date is before logweek i+1
                boolean isAfter = calculations.CompareDates(orderedLog[i], dateSplit);      //current date is after logweek i

                if (isBefore == true && isAfter == true) {

                    week= i+1;
                    break;

                }

            }

            else {

                week =0;

            }

        }

        //return int of current schedule

        return week;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(mToogle.onOptionsItemSelected(item)){

            return true;

        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}

package com.example.therehabapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.health.ServiceHealthStats;
import android.util.Log;
import android.view.MenuItem;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;
import java.util.ArrayList;
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
    private DatabaseReference logsRef;
    private FirebaseAuth auth;
    private String uid;

    private DateSplit currentWeekStart;
    private ScheduleLog log;

    DateSplit[] myScheduleArray;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);

        log = new ScheduleLog();
        myScheduleArray = new DateSplit[12];

        firebaseDb = FirebaseDatabase.getInstance();

        auth= FirebaseAuth.getInstance();
        uid = auth.getUid();
        dbRef = firebaseDb.getReference("Diagnoses/" + uid);
        logsRef = firebaseDb.getReference("ScheduleLogs/"+ uid);

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
                        //startActivity(new Intent(Home.this, Inbox.class));
                        break;

                    case R.id.gallery:
                        //startActivity(new Intent(Home.this, Gallery.class));
                        break;

                    case R.id.journal:
                        startActivity(new Intent(Home.this, Journal.class));
                        break;

                }

                return false;

            }
        });

    }

    @Override
    protected void onStart() {

        super.onStart();

        //currentWeekStart = new DateSplit();
        SeekLayout();

    }

    private void SeekLayout()
    {

        long millis = System.currentTimeMillis();
        java.sql.Date date= new java.sql.Date(millis);
        String dateString = date.toString();
        DateSplit currentDate = new DateSplit(dateString);

        //DateSplit[] scheduleLog = GetSchedule();

        ScheduleCalculations calc = new ScheduleCalculations();

        /**
        //compare current date to each dateSplit
        for(int i=0; i<scheduleLog.length;i++)
        {

            if(calc.CompareDates(currentDate,myScheduleArray[i+1])==true && calc.CompareDates(myScheduleArray[i],currentDate)==true) //if current date is between 2 weeks
            {

                currentWeekStart = myScheduleArray[i];
                break;

            }

        } **/

        //SetLayout("Addiction",currentWeekStart);

        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String diagnosis = dataSnapshot.getValue(String.class);
                SetLayout(diagnosis);
                //SetLayout(diagnosis,currentWeekStart);

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

    private void SetLayout(String diagnosis)
    {

       // int week = weekStartDate.Week;

        if(diagnosis.equals("Depression"))
        {
            myLayout = R.layout.fragment_disorders_depression_1;
            //switch statements
            /**switch (week)
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

            } **/

        }

        if(diagnosis.equals("Eating Disorder"))
        {

            myLayout = R.layout.fragment_disorders_eating_1;

            /**switch (week)
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
            } **/


        }

        if(diagnosis.equals("Addiction"))
        {

            myLayout = R.layout.fragment_disorders_addiction_1;
            /**switch (week)
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
            } **/


        }

       /** if(diagnosis.equals("Anxiety"))
        {

            switch (week)
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


        } **/

    }

    /**
     * returns the returns DateSplit[] from db
     */

    public DateSplit[] GetSchedule()
    {

        logsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot logSnapshot: dataSnapshot.getChildren())
                {

                    ScheduleLog log = logSnapshot.getValue(ScheduleLog.class) ;

                    myScheduleArray[0] =log.week1;
                    myScheduleArray[1] =log.week2;
                    myScheduleArray[2] =log.week3;
                    myScheduleArray[3] =log.week4;
                    myScheduleArray[4] =log.week5;
                    myScheduleArray[5] =log.week6;
                    myScheduleArray[6] =log.week7;
                    myScheduleArray[7] =log.week8;
                    myScheduleArray[8] =log.week9;
                    myScheduleArray[9] =log.week10;
                    myScheduleArray[10] =log.week11;
                    myScheduleArray[11] =log.week12;


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return myScheduleArray;

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

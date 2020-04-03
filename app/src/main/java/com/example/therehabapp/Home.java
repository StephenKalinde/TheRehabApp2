package com.example.therehabapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);

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
                    case R.id.inbox:
                        startActivity(new Intent(Home.this, Inbox.class));
                        break;

                    case R.id.journal:
                        startActivity(new Intent(Home.this, Journal.class));
                        break;

                    case R.id.gallery:
                        startActivity(new Intent(Home.this, Gallery.class));
                        break;
                }

                return false;
            }
        });

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

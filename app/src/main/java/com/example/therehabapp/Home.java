package com.example.therehabapp;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class Home extends AppCompatActivity {

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
                    FragmentDisorders fragmentDisorders= new FragmentDisorders();
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

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_view);

        BottomNavigationView bottomNav= (BottomNavigationView) findViewById(R.id.bottom_nav_bar);
        bottomNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);


        FragmentHome homeFragment= new FragmentHome();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragments_container,homeFragment,"FragmentName");
        fragmentTransaction.commit();

    }
}

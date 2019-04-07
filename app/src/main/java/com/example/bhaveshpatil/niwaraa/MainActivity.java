package com.example.bhaveshpatil.niwaraa;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.bhaveshpatil.niwaraa.activity.PostPropertyUser;
import com.example.bhaveshpatil.niwaraa.fragments.HomePage;
import com.example.bhaveshpatil.niwaraa.fragments.ProfileFragment;



public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar.setTitle("Niwaraa");
        loadFragment(new HomePage());
        }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    toolbar.setTitle("Niwaraa");
                    fragment = new HomePage();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_chat:
                    toolbar.setTitle("Chat");
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_addProperty:
                    toolbar.setTitle("Add Property");
                    startActivity(new Intent(getApplicationContext(), PostPropertyUser.class));

                   // fragment = new ProfileFragment();
                   // loadFragment(fragment);
                    return true;
                case R.id.nav_notification:
                    toolbar.setTitle("Notifications");
                  //  fragment = new Notifications();
                   // loadFragment(fragment);
                    return true;
                case R.id.nav_menu:
                    toolbar.setTitle("Menu");
                   // fragment = new ProfileFragment();
                  //  loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}


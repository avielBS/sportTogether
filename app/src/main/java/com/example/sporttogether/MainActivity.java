package com.example.sporttogether;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private FirebaseDatabase firebaseDatabase;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout = findViewById(R.id.navigation_drawer);
        navigationView = findViewById(R.id.navigation_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawe_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){

                    case R.id.add_workout_menu:
                        Intent addWorkoutIntent = new Intent(MainActivity.this, AddWorkoutActiviy.class);
                        addWorkoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(addWorkoutIntent);
                        break;

                    case R.id.search_workouts_menu:
                        Intent workoutsIntent = new Intent(MainActivity.this, WorkoutsActivity.class);
                        workoutsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(workoutsIntent);
                        break;
                    case R.id.history_menu:
                        Intent historyIntent = new Intent(MainActivity.this, HistoryActivity.class);
                        historyIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(historyIntent);
                        break;
                    case R.id.settings:
                        Intent SettingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                        SettingsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(SettingsIntent);
                        break;

                    case R.id.login:
                        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(loginIntent);
                        break;
                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        break;


                }

                return true;
            }

        });

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }
            }
        };
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else
                    drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
            super.onBackPressed();
    }

}
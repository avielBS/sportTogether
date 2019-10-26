package com.example.sporttogether;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.sporttogether.Data.UserRecord;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firestore.v1.FirestoreGrpc;

public class MainActivity extends AppCompatActivity {

    private Button loginBtn;
    private Button workoutsButton;
    private Button historyWorkoutsButton;
    private Button logoutButton;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private FirebaseDatabase firebaseDatabase;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        loginBtn = findViewById(R.id.login_btn);
//        workoutsButton = findViewById(R.id.workouts_btn);
//        historyWorkoutsButton = findViewById(R.id.workout_history_btn);
//        logoutButton = findViewById(R.id.logout_btn);
//
//        historyWorkoutsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent historyIntent = new Intent(MainActivity.this, HistoryActivity.class);
//                historyIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(historyIntent);
//            }
//        });
//
//
//        workoutsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent workoutsIntent = new Intent(MainActivity.this, WorkoutsActivity.class);
//                workoutsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(workoutsIntent);
//            }
//        });
//
//        loginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
//                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(loginIntent);
//            }
//        });
//
//        logoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//            }
//        });

//        firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference dbRef = firebaseDatabase.getReference();

        drawerLayout = findViewById(R.id.navigation_drawer);
        navigationView = findViewById(R.id.navigation_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawe_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
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
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.drawer_menu,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        if(item.getItemId() == R.id.login){
//            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
//            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(loginIntent);
//        }
//        if(item.getItemId() == R.id.add_workout){
//            Intent addWorkoutIntent = new Intent(MainActivity.this, AddWorkoutActiviy.class);
//            addWorkoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(addWorkoutIntent);
//        }
//
//        return super.onOptionsItemSelected(item);
//    }



}
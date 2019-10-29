package com.example.sporttogether;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SingleWorkoutActivity extends AppCompatActivity {

    private String workoutId;
    private DatabaseReference databaseReference;
    


    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView cityTextView;
    private TextView typeTextView;
    private TextView dateAndTime;
    private TextView creatorTextView;

    private Button removeButton;
    private Button openOnGoogleMaps;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_workout);

        workoutId = getIntent().getExtras().getString(Util.WORKOUT_ID);

        dateAndTime = findViewById(R.id.single_workout_date_and_time);
        titleTextView = findViewById(R.id.single_workout_title);
        descriptionTextView = findViewById(R.id.single_workout_descriptions);
        cityTextView = findViewById(R.id.single_workout_city);
        typeTextView = findViewById(R.id.single_workout_type);
        creatorTextView = findViewById(R.id.single_workout_creator);

        databaseReference = FirebaseDatabase.getInstance().getReference().child(Util.WORKOUTS);

        removeButton = findViewById(R.id.remove_workout_button);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(v.getContext())
                        .setTitle("Remove workout").setMessage("Delete this workout ?")
                        .setIcon(android.R.drawable.ic_delete)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child(workoutId).removeValue();
                        Toast.makeText(getApplicationContext(),"workout deleted",Toast.LENGTH_LONG).show();
                        finish();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
            }
        });
        openOnGoogleMaps = findViewById(R.id.open_on_google_maps);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        databaseReference.child(workoutId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    titleTextView.setText(dataSnapshot.child(Util.TITLE).getValue().toString());
                    descriptionTextView.setText(dataSnapshot.child(Util.DESCRIPTION).getValue().toString());
                    cityTextView.setText(dataSnapshot.child(Util.CITY).getValue().toString());
                    typeTextView.setText(dataSnapshot.child(Util.TYPE).getValue().toString());
                    dateAndTime.setText(dataSnapshot.child(Util.DATE).getValue().toString() + " " + dataSnapshot.child(Util.HOUR).getValue().toString());
                    creatorTextView.setText(dataSnapshot.child(Util.USERNAME).getValue().toString());

                    double lat, lng;
                    lat = new Double(dataSnapshot.child(Util.LAT).getValue().toString());
                    lng = new Double(dataSnapshot.child(Util.LONG).getValue().toString());

                    if (lat == -1 && lng == -1)
                        openOnGoogleMaps.setVisibility(View.INVISIBLE);
                    else
                        openOnGoogleMaps.setVisibility(View.VISIBLE);

                    if (firebaseUser.getUid().equals(dataSnapshot.child(Util.USERID).getValue().toString())) {
                        removeButton.setVisibility(View.VISIBLE);
                    } else {
                        removeButton.setVisibility(View.INVISIBLE);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}

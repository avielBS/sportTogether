package com.example.sporttogether;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AddWorkoutActiviy extends AppCompatActivity {

    private EditText workoutTitle;
    private EditText workoutDescription;
    private EditText workoutCity;
    private Button addWorkoutBtn;
    private ProgressBar progressBar;

    private Spinner spinnerType;
    private String type;

    private DatabaseReference databaseReference;
    private DatabaseReference userDatabaseReference;
    private  DatabaseReference joinedDatabaseReference;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout_activiy);

        this.spinnerType = findViewById(R.id.spinner_type);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        ArrayAdapter<CharSequence> stringArrayAdapter = ArrayAdapter.createFromResource(this,R.array.types,android.R.layout.simple_spinner_item);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(stringArrayAdapter);

        workoutTitle = findViewById(R.id.workout_title);
        workoutDescription = findViewById(R.id.workout_descriptions);
        workoutCity = findViewById(R.id.city_editTxt);
        addWorkoutBtn = findViewById(R.id.add_workout_btn);
        progressBar = findViewById(R.id.progress_bar);
        
        addWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWorkoutToFirebase();
            }
        });

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                type = adapterView.getItemAtPosition(0).toString();
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference().child(Util.WORKOUTS);
        userDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Util.USERS).child(firebaseUser.getUid());

    }

    private void addWorkoutToFirebase() {
        progressBar.setVisibility(View.VISIBLE);
        final String title = this.workoutTitle.getText().toString();
        final String description = this.workoutDescription.getText().toString();
        final String city = this.workoutCity.getText().toString();

        if(!title.isEmpty() && !city.isEmpty()){
            //
            final DatabaseReference newWorkout = databaseReference.push();

            userDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    newWorkout.child(Util.TITLE).setValue(title);
                    newWorkout.child(Util.DESCRIPTION).setValue(description);
                    newWorkout.child(Util.CITY).setValue(city);
                    newWorkout.child(Util.TYPE).setValue(type);
                    newWorkout.child(Util.USERID).setValue(firebaseUser.getUid());
                    newWorkout.child(Util.USERNAME).setValue(dataSnapshot.child(Util.NAME).getValue())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getApplicationContext(),"Your workout add successfully",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(AddWorkoutActiviy.this,MainActivity.class));
                                    }else{
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getApplicationContext(),"upload failed",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        }
        else{
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(this,"Title or Description are empty",Toast.LENGTH_LONG).show();
        }

    }
}

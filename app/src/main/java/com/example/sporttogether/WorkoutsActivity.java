package com.example.sporttogether;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.sporttogether.Data.WorkoutRecord;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class WorkoutsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerOptions< WorkoutRecord> firebaseOptions;
    private WorkoutAdapter workoutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);

        recyclerView = findViewById(R.id.workout_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference().child(Util.WORKOUTS);

        firebaseOptions = new FirebaseRecyclerOptions.Builder<WorkoutRecord>()
                .setQuery(databaseReference,WorkoutRecord.class).build();

        workoutAdapter = new WorkoutAdapter(firebaseOptions);
        workoutAdapter.startListening();

        recyclerView.setAdapter(workoutAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        workoutAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        workoutAdapter.stopListening();
    }



}

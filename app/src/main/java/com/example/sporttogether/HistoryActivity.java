package com.example.sporttogether;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sporttogether.Data.WorkoutRecord;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private DatabaseReference joinedWorkouts;
    private FirebaseRecyclerOptions<WorkoutRecord> firebaseOptions;
    private WorkoutAdapter workoutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.history_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference().child(Util.WORKOUTS);

        firebaseOptions = new FirebaseRecyclerOptions.Builder<WorkoutRecord>()
                        .setQuery(databaseReference.orderByChild(FirebaseAuth.getInstance().getUid().toString())
                                        .equalTo(FirebaseAuth.getInstance().getUid().toString())
                                ,WorkoutRecord.class).build();


        workoutAdapter = new WorkoutAdapter(firebaseOptions);
        workoutAdapter.startListening();

        recyclerView.setAdapter(workoutAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(workoutAdapter!=null)
            workoutAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(workoutAdapter!=null)
            workoutAdapter.stopListening();
    }
}

package com.example.sporttogether;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sporttogether.Data.WorkoutRecord;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class WorkoutsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerOptions< WorkoutRecord> firebaseOptions;
    private WorkoutAdapter workoutAdapter;
    private EditText searchCity;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);

        searchCity = findViewById(R.id.search_city_editText);
        searchButton = findViewById(R.id.search_city_btn);

        recyclerView = findViewById(R.id.workout_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference().child(Util.WORKOUTS);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseOptions = new FirebaseRecyclerOptions.Builder<WorkoutRecord>()
                        .setQuery(databaseReference.orderByChild(Util.PUBLIC_CITY).equalTo(Util.PUBLIC+"_"+searchCity.getText().toString())

                                ,WorkoutRecord.class).build();

                workoutAdapter = new WorkoutAdapter(firebaseOptions);
                workoutAdapter.startListening();

                recyclerView.setAdapter(workoutAdapter);
            }
        });

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

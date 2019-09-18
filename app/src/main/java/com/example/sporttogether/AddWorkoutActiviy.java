package com.example.sporttogether;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class AddWorkoutActiviy extends AppCompatActivity {

    private Spinner spinnerType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout_activiy);

        this.spinnerType = findViewById(R.id.spinner_type);

        ArrayAdapter<CharSequence> stringArrayAdapter = ArrayAdapter.createFromResource(this,R.array.types,android.R.layout.simple_spinner_item);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(stringArrayAdapter);

    }
}

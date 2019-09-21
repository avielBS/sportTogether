package com.example.sporttogether;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sporttogether.TimeAndDate.MyDatePicker;
import com.example.sporttogether.TimeAndDate.MyTimePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddWorkoutActiviy extends AppCompatActivity implements  DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    private EditText workoutTitle;
    private EditText workoutDescription;
    private EditText workoutCity;
    private Button addWorkoutBtn;
    private ProgressBar progressBar;

    private Button dateButton;
    private TextView dateTxt;
    private Button hourButton;
    private TextView hourTxt;

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

        dateButton = findViewById(R.id.date_button);
        dateTxt = findViewById(R.id.workout_date_text);

        hourButton = findViewById(R.id.workout_hour_button);
        hourTxt = findViewById(R.id.workout_hour_text);

        hourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timeFragment = new MyTimePicker();
                timeFragment.show(getSupportFragmentManager(),"Workout time");
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dateFregmant = new MyDatePicker();
                dateFregmant.show(getSupportFragmentManager(),"Workout Date");
            }
        });

        
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar current = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        if(calendar.getTimeInMillis() >= current.getTimeInMillis()){
            String workoutDate = DateFormat.getDateInstance().format(calendar.getTime());
            dateTxt.setText(workoutDate);
        }
        else{
            Toast.makeText(this,"You can't change the past",Toast.LENGTH_LONG).show();
        }


    }

    private void addWorkoutToFirebase() {
        progressBar.setVisibility(View.VISIBLE);
        final String title = this.workoutTitle.getText().toString();
        final String description = this.workoutDescription.getText().toString();
        final String city = this.workoutCity.getText().toString();
        final String date = this.dateTxt.getText().toString();
        final String hour = this.hourTxt.getText().toString();

        if(!title.isEmpty() && !city.isEmpty() && !hourTxt.getText().toString().isEmpty() &&! dateTxt.getText().toString().isEmpty()) {
            //
            final DatabaseReference newWorkout = databaseReference.push();

            userDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    newWorkout.child(Util.TITLE).setValue(title);
                    newWorkout.child(Util.DESCRIPTION).setValue(description);
                    newWorkout.child(Util.CITY).setValue(city);
                    newWorkout.child(Util.TYPE).setValue(type);

                    newWorkout.child(Util.HOUR).setValue(hour);
                    newWorkout.child(Util.DATE).setValue(date);

                    newWorkout.child(Util.PUBLIC_CITY).setValue(type+"_"+city);
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
            Toast.makeText(this,"Title,Description,time,date can't be empty",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();

        if(hourOfDay >= calendar.get(Calendar.HOUR_OF_DAY) && minute >= calendar.get(Calendar.MINUTE) )
             hourTxt.setText(hourOfDay+":"+minute);
        else
            Toast.makeText(this,"set workout to future time",Toast.LENGTH_LONG).show();

    }
}

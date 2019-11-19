package com.example.sporttogether;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sporttogether.Data.WorkoutRecord;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class WorkoutAdapter extends FirebaseRecyclerAdapter<WorkoutRecord, WorkoutAdapter.WorkoutViewHolder> {

    private DatabaseReference joinedDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Util.JOIN_TO_WORKOUT);
    private DatabaseReference workoursDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Util.WORKOUTS);
    private DatabaseReference userDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Util.USERS)
            .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
    private boolean flag=false;


    public WorkoutAdapter(@NonNull FirebaseRecyclerOptions<WorkoutRecord> options) {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull final WorkoutViewHolder holder, final int position, @NonNull final WorkoutRecord model) {


            holder.getTitle().setText(model.getTitle());
            holder.getCity().setText(model.getCity());
            holder.getDescription().setText(model.getDescription());
            holder.getType().setText(model.getType());
            holder.getDateAndTime().setText(model.getDate()+" "+model.getHour());

            holder.checkJoinButton(getRef(position).getKey(), userDatabaseReference.getKey());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent singleWorkoutIntent = new Intent(v.getContext(),SingleWorkoutActivity.class);
                    singleWorkoutIntent.putExtra(Util.WORKOUT_ID,getRef(position).getKey());
                    v.getContext().startActivity(singleWorkoutIntent);
                }
            });

            holder.getJoinWorkoutButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    holder.setJoined(!holder.isJoined());

                    flag = true;

                    workoursDatabaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (flag) {

                                if (dataSnapshot.child(getRef(position).getKey()).hasChild(userDatabaseReference.getKey())  ) {
//                                    joinedDatabaseReference.child(getRef(position).getKey()).child(userDatabaseReference.getKey()).removeValue();
                                    workoursDatabaseReference.child(getRef(position).getKey()).child(userDatabaseReference.getKey().toString()).removeValue();
                                    workoursDatabaseReference.child(getRef(position).getKey()).child(Util.PARTICIPANTS).setValue(model.getParticipants() - 1);


                                    flag = false;
                                } else {
 //                                   joinedDatabaseReference.child(getRef(position).getKey()).child(userDatabaseReference.getKey()).setValue("1");
                                    Calendar current = Calendar.getInstance();

                                        if(current.getTimeInMillis() < model.getDateInMillis()){
                                            Log.d(" relvate workout",current.getTimeInMillis() - model.getDateInMillis()+"");
                                            workoursDatabaseReference.child(getRef(position).getKey()).child(userDatabaseReference.getKey().toString()).setValue(userDatabaseReference.getKey());
                                            workoursDatabaseReference.child(getRef(position).getKey()).child(Util.PARTICIPANTS).setValue(model.getParticipants() + 1);
                                        }
                                        else{
                                            Toast.makeText(holder.itemView.getContext(),"Workout time pass",Toast.LENGTH_LONG).show();
                                        }

                                    flag = false;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            });

        Log.d("title",getItem(position).getTitle()+""+model.getTitle());
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_workout,parent,false);
        return new WorkoutViewHolder(view);
    }

    class WorkoutViewHolder extends  RecyclerView.ViewHolder{

        private TextView title,description,type,city;
        private Button joinWorkoutButton;
        private boolean joined;
        private long participate;
        private TextView dateAndTime;

        private FirebaseAuth auth;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.single_workout_title);
            city = itemView.findViewById(R.id.single_workout_city);
            description = itemView.findViewById(R.id.single_workout_descriptions);
            type = itemView.findViewById(R.id.single_workout_type);
            joinWorkoutButton = itemView.findViewById(R.id.join_workout_btn);
            dateAndTime = itemView.findViewById(R.id.single_workout_date_and_time);
            joinWorkoutButton.setBackgroundColor(Color.GREEN);
            joined = false;
            participate = 0;
            auth = FirebaseAuth.getInstance();

            joinedDatabaseReference.keepSynced(true);
        }

        public void checkJoinButton(final String postKey, final String currentUser){


            workoursDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(postKey).hasChild(currentUser)) {

                        getJoinWorkoutButton().setBackgroundColor(Color.RED);
                        getJoinWorkoutButton().setText(Util.LEAVE);

                    }
                    else{
                        getJoinWorkoutButton().setBackgroundColor(Color.GREEN);
                        getJoinWorkoutButton().setText(Util.JOIN);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        public TextView getDateAndTime() {
            return dateAndTime;
        }

        public void setDateAndTime(TextView dateAndTime) {
            this.dateAndTime = dateAndTime;
        }

        public long getParticipate() {
            return participate;
        }

        public void setParticipate(long participate) {
            this.participate = participate;
        }

        public boolean isJoined() {
            return joined;
        }

        public void setJoined(boolean joined) {
            this.joined = joined;
        }

        public Button getJoinWorkoutButton() {
            return joinWorkoutButton;
        }

        public void setJoinWorkoutButton(Button joinWorkoutButton) {
            this.joinWorkoutButton = joinWorkoutButton;
        }

        public TextView getTitle() {
            return title;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }

        public TextView getDescription() {
            return description;
        }

        public void setDescription(TextView description) {
            this.description = description;
        }

        public TextView getType() {
            return type;
        }

        public void setType(TextView type) {
            this.type = type;
        }

        public TextView getCity() {
            return city;
        }

        public void setCity(TextView city) {
            this.city = city;
        }
    }

}

package com.example.sporttogether;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

public class WorkoutAdapter extends FirebaseRecyclerAdapter<WorkoutRecord, WorkoutAdapter.WorkoutViewHolder> {

    DatabaseReference joinedDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Util.JOINED);
    DatabaseReference userDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Util.USERS)
            .child(FirebaseAuth.getInstance().getCurrentUser().getUid());


    public WorkoutAdapter(@NonNull FirebaseRecyclerOptions<WorkoutRecord> options) {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull final WorkoutViewHolder holder, final int position, @NonNull WorkoutRecord model) {



        holder.getTitle().setText(model.getTitle());
        holder.getCity().setText(model.getCity());
        holder.getDescription().setText(model.getDescription());
        holder.getType().setText(model.getType());
        holder.getJoinWorkoutButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.setJoined( ! holder.isJoined() );

                joinedDatabaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(getRef(position).getKey()).hasChild(userDatabaseReference.getKey() )){

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                if(holder.isJoined()){
                    holder.getJoinWorkoutButton().setBackgroundColor(Color.RED);
                    holder.getJoinWorkoutButton().setText(Util.LEAVE);
                }else{
                    holder.getJoinWorkoutButton().setBackgroundColor(Color.BLUE);
                    holder.getJoinWorkoutButton().setText(Util.JOINED);
                }

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

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.single_workout_title);
            city = itemView.findViewById(R.id.single_workout_city);
            description = itemView.findViewById(R.id.single_workout_descriptions);
            type = itemView.findViewById(R.id.single_workout_type);
            joinWorkoutButton = itemView.findViewById(R.id.join_workout_btn);
            joinWorkoutButton.setBackgroundColor(Color.BLUE);
            joined = false;
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

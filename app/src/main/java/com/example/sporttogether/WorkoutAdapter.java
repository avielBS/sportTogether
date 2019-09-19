package com.example.sporttogether;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sporttogether.Data.WorkoutRecord;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class WorkoutAdapter extends FirebaseRecyclerAdapter<WorkoutRecord, WorkoutAdapter.WorkoutViewHolder> {


    public WorkoutAdapter(@NonNull FirebaseRecyclerOptions<WorkoutRecord> options) {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position, @NonNull WorkoutRecord model) {

        getItem(position).getTitle();

        holder.getTitle().setText(model.getTitle());
        holder.getCity().setText(model.getCity());
        holder.getDescription().setText(model.getDescription());
        holder.getType().setText(model.getType());

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

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.single_workout_title);
            city = itemView.findViewById(R.id.single_workout_city);
            description = itemView.findViewById(R.id.single_workout_descriptions);
            type = itemView.findViewById(R.id.single_workout_type);
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

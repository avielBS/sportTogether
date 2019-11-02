package com.example.sporttogether;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sporttogether.Data.WorkoutRecord;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivity extends AppCompatActivity {

    private EditText newPassword;
    private EditText confirmPassword;
    private Button changePasswordButton;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(Util.USERS);

        newPassword = findViewById(R.id.change_password_editText);
        confirmPassword = findViewById(R.id.confirm_password_editText);
        changePasswordButton = findViewById(R.id.change_password_button);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newPassword.getText().toString().equals(confirmPassword.getText().toString()) && !newPassword.getText().toString().isEmpty()) {
                    final FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        user.updatePassword(newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplication(), "password changed", Toast.LENGTH_LONG).show();
                                    databaseReference.child(user.getUid().toString()).child(Util.PASSWORD).setValue(newPassword.getText().toString());

                                } else {
                                    Toast.makeText(getApplication(), "password dont changed ! ", Toast.LENGTH_LONG).show();
                                    Log.d("task", task.getException().getMessage());
                                }
                            }
                        });
                    }
                }
            }
        });


    }
}

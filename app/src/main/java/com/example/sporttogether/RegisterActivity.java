package com.example.sporttogether;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameEditTxt;
    private EditText emailEditTxt;
    private EditText passwordEditTxt;
    private Button registerButton;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.firebaseAuth = FirebaseAuth.getInstance();
        this.databaseReference = FirebaseDatabase.getInstance().getReference().child(Util.USERS);

        nameEditTxt = findViewById(R.id.name_edit_txt);
        emailEditTxt = findViewById(R.id.email_edit_txt);
        passwordEditTxt = findViewById(R.id.password_edit_txt);
        progressBar = findViewById(R.id.progress_bar);
        registerButton = findViewById(R.id.register_button);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

    }

    private void register() {

        final String nameStr = this.nameEditTxt.getText().toString();
        final String emailStr = this.emailEditTxt.getText().toString();
        final String passwordStr = this.passwordEditTxt.getText().toString();

        if(!nameStr.isEmpty() && !emailStr.isEmpty() && !passwordStr.isEmpty()){

                this.firebaseAuth.createUserWithEmailAndPassword(emailStr,passwordStr)
                        .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.VISIBLE);
                    Log.d("TASK",task.isSuccessful()+"");

                    if( task.isSuccessful() ){
                        String userId = firebaseAuth.getCurrentUser().getUid();

                        DatabaseReference currentUser = databaseReference.child(userId);
                        currentUser.child(Util.NAME).setValue(nameStr);
                        currentUser.child(Util.EMAIL).setValue(emailStr);
                        currentUser.child(Util.PASSWORD).setValue(passwordStr);

                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Cant register",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
        else{
            Toast.makeText(this,"Complete all fields",Toast.LENGTH_LONG).show();
        }
    }


}

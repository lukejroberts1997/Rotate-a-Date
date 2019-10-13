package com.example.finalprojectv1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class signup extends AppCompatActivity implements View.OnClickListener {
    EditText user, name2;
    public Button btngo;
    EditText email, Password;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_signup);
        email = (EditText) findViewById(R.id.email);
        name2 = findViewById(R.id.username);
        Password = (EditText) findViewById(R.id.Password);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.btngo).setOnClickListener(this);


    }

    private void registerUser() {

        String youremail = email.getText().toString().trim();
        String password = Password.getText().toString().trim();


        if (youremail.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(youremail).matches()) {
            email.setError("Please enter a valid Email Address");
        }

        if (password.isEmpty()) {
            Password.setError("Password is required");
            Password.requestFocus();
            return;
        }
        if (password.length() < 6) {
            Password.setError("Password must be longer than 6 characters.");
            Password.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(youremail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(signup.this, profile.class));
                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

   @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btngo:
                registerUser();
                break;


        }
    }


}


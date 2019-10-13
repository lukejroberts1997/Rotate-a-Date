package com.example.finalprojectv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Settings extends AppCompatActivity {
    Button startbutton,logout, Profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
        init2();
        init3();
    }
    public void init() {
        startbutton = (Button) findViewById(R.id.startbutton);
        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signclick = new Intent(Settings.this, Sessionwindow.class);
                startActivity(signclick);
            }

        });
    }
    public void init2() {
        Profile = (Button) findViewById(R.id.Profile);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signclick = new Intent(Settings.this, profile.class);
                startActivity(signclick);
            }

        });
    }
    public void init3(){
        logout= (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(Settings.this,MainActivity.class));

            }
        });
    }
}

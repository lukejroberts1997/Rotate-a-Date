package com.example.finalprojectv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public Button btnsignin;
    public Button btnsignup;
    public Button btnguest;
    public void init(){
        btnsignin= (Button)findViewById(R.id.btnsignin);
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signclick = new Intent(MainActivity.this, login2.class);
                startActivity(signclick);
            }
        });
    }

    public void init2(){
        btnsignup= (Button)findViewById(R.id.btnsignup);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupclick = new Intent(MainActivity.this, signup.class);
                startActivity(signupclick);
            }
        });
    }
    public void init3(){
        btnguest= (Button)findViewById(R.id.btnguest);
        btnguest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent guestclick = new Intent(MainActivity.this, Guest.class);
                startActivity(guestclick);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        init2();
        init3();
    }
}

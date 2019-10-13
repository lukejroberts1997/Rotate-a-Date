package com.example.finalprojectv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;


public class profile extends AppCompatActivity {
    public Button btngay,btnstraight,btnlesbian;

    public void init(){
        btngay= (Button)findViewById(R.id.btngay);
        btngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signclick = new Intent(profile.this, Boysession.class);
                startActivity(signclick);
            }
        });
    }
    public void init2(){
        btnlesbian= (Button)findViewById(R.id.btnlesbian);
        btnlesbian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signclick = new Intent(profile.this, girlsession.class);
                startActivity(signclick);
            }
        });
    }
    public void init3(){
        btnstraight= (Button)findViewById(R.id.btnstraight);
        btnstraight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signclick = new Intent(profile.this, Sessionwindow.class);
                startActivity(signclick);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
        init3();
        init2();
    }
}

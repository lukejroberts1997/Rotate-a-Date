package com.example.finalprojectv1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Session extends AppCompatActivity {
/*public Button btnstop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        init();

        Button btncamera = (Button) findViewById(R.id.btncamera);
        ImageView camera = (ImageView)findViewById(R.id.camera);

        btncamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });
    }
    public void init() {
        btnstop = (Button) findViewById(R.id.btnstop);
        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signclick = new Intent(Session.this, Settings.class);
                startActivity(signclick);
            }

        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        //ImageView.setImageBitmap(bitmap);
    }*/
}

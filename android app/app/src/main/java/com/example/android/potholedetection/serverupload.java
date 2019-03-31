package com.example.android.potholedetection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class serverupload extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serverupload);

        Button b1 = (Button) findViewById(R.id.no);

        b1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(serverupload.this,MainActivity.class));
            }
        });


        Button b2 = (Button) findViewById(R.id.yes);

        b2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(serverupload.this,issue.class));
            }
        });

    }
}

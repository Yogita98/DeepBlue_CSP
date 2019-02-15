package com.example.android.potholedetection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class choice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        Button b1 = (Button) findViewById(R.id.inappcamera);

        b1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(choice.this,Reportpothole.class));
            }
        });

        Button b2 = (Button) findViewById(R.id.gallery);

        b2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(choice.this,disclaimer.class));
            }
        });

    }
}

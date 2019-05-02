package com.example.android.potholedetection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class issue extends AppCompatActivity {

    int shoesize, severity;
    float widthincm,depthincm;
    String currentLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);

        Bundle bundle = getIntent().getExtras();
        shoesize = bundle.getInt("Shoesize");
        severity = bundle.getInt("Severity");
        widthincm = bundle.getFloat("Width");
        depthincm = bundle.getFloat("Depth");
        currentLocation = bundle.getString("Location");


        Button b1 = (Button) findViewById(R.id.details);
        b1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent in = new Intent(issue.this, showdimensions.class);
                Bundle bundle = new Bundle();
                bundle.putFloat("Width",widthincm);
                bundle.putFloat("Depth",depthincm);
                bundle.putInt("Shoesize",shoesize);
                bundle.putInt("Severity", severity);
                bundle.putString("Location", currentLocation);
                in.putExtras(bundle);
                startActivity(in);
            }
        });

    }
}

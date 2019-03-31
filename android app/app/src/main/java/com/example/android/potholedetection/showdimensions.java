package com.example.android.potholedetection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class showdimensions extends AppCompatActivity {
    int shoesize,severity;
    float widthincm,depthincm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdimensions);
        String severity1 = "Not a Pothole";
        String location1 = "Hashu Advani Memorial Complex";

        Bundle bundle = getIntent().getExtras();
        shoesize = bundle.getInt("Shoesize");
        severity = bundle.getInt("Severity");
        widthincm = bundle.getFloat("Width");
        depthincm = bundle.getFloat("Depth");
        if(severity == 1)
            severity1 = "Severity of Pothole: Low ";
        if(severity == 2)
            severity1 = "Severity of Pothole: Medium ";
        if(severity == 3)
            severity1 = "Severity of Pothole: High ";

        String width1 = "Width in cm: " + String.valueOf(widthincm);
        String depth1 = "Depth in cm: " + String.valueOf(depthincm);
        //String severity1 = "Severity of Pothole: " + String.valueOf(severity);
        String location2 = "Location: " + location1;
        TextView width = (TextView)findViewById(R.id.show_width);
        TextView depth = (TextView)findViewById(R.id.show_depth);
        TextView severity = (TextView)findViewById(R.id.show_severity);
        TextView location = (TextView)findViewById(R.id.show_location);

        width.setText(width1);
        depth.setText(depth1);
        severity.setText(severity1);
        location.setText(location2);

        Button b1 = (Button) findViewById(R.id.mainscreen);
        b1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent in = new Intent(showdimensions.this, MainActivity.class);
                startActivity(in);
            }
        });
    }
}
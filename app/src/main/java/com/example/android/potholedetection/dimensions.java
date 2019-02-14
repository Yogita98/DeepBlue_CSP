package com.example.android.potholedetection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


public class dimensions extends AppCompatActivity {
    ArrayList<String> arraytopX1 = new ArrayList<>(10);
    ArrayList<String> arraytopY1 = new ArrayList<>(10);
    ArrayList<String> arraysideX1 = new ArrayList<>(10);
    ArrayList<String> arraysideY1 = new ArrayList<>(10);

    float refcm, refpxl, width1pxl, width2pxl,depth2pxl;
    // Reference object length in cm = refcm
    // Reference object length in pixel = refpxl
    // Pothole width in pixel from topview image = width1pxl
    // Pothole width in pixel from sideview image = width2pxl
    // Pothole depth in pixel from sideview image = depth2pxl
    // Pothole depth in pixel in topview image = depth1pxl(To be found)
    // Pothole depth in cm = depthcm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimensions);

        Button b1 = (Button) findViewById(R.id.btn_toUpload);



        b1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(dimensions.this,serverupload.class));

            }
        });

        Bundle bundle = getIntent().getExtras();
        arraytopX1 = (ArrayList<String>) bundle.getStringArrayList("arraytX");
        arraytopY1 = (ArrayList<String>) bundle.getStringArrayList("arraytY");
        arraysideX1 = (ArrayList<String>) bundle.getStringArrayList("arraysX");
        arraysideY1 = (ArrayList<String>) bundle.getStringArrayList("arraysY");

        Log.d("Array in topview X is:",arraytopX1.toString());
        Log.d("Array in topview Y is:",arraytopY1.toString());
        Log.d("Array in sideview X is:",arraysideX1.toString());
        Log.d("Array in sideview Y is:",arraysideY1.toString());

        double ref1,ref2,ref3,ref4;
        double depth1, depth2, depth3, depth4;

//        ref1 = (double)Double.parseDouble(arraytopX1.get(0).toString());
//        ref2 = (double)Double.parseDouble(arraytopX1.get(1));
//        ref3 = (double)Double.parseDouble(arraytopY1.get(0));
//        ref4 = (double)Double.parseDouble(arraytopY1.get(1));



    }

    public float detectWidth(float arefcm, float arefpxl, float awidth1pxl)
    {
        float widthcm;
        widthcm = (arefcm*awidth1pxl)/arefpxl;
        return widthcm;
    }

    public float detectDepth(float brefcm, float brefpxl, float bwidth1pxl, float bwidth2pxl, float bdepth2pxl)
    {
        float depth1pxl;
        float depthcm;
        depth1pxl = (bwidth1pxl*bdepth2pxl)/bwidth2pxl;
        depthcm = (depth1pxl*brefcm)/brefpxl;
        return depthcm;
    }

    //Find Width in cm
    float widthincm = detectWidth(refcm,refpxl,width1pxl);


    //Find Depth in cm
    float depthincm = detectDepth(refcm, refpxl, width1pxl, width2pxl, depth2pxl);


}

package com.example.android.potholedetection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class dimensions extends AppCompatActivity {
    public int Xdirtop;
    public int Xdirside;
    float[] arraytopX;
    float[] arraytopY;
    float[] arraysideX;
    float[] arraysideY;
    String currentLocation;

    float refcm, refpxl, width1pxl, width2pxl, depth2pxl, widthincm, depthincm;
    int shoesize;
    int severity = 0;


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
        setContentView(R.layout.activity_serverupload);
        //refcm = (float) 6.4;  //Length of toothpick
        arraytopX = new float[10];
        arraytopY = new float[10];
        arraysideX = new float[10];
        arraysideY = new float[10];

        Button b2 = (Button) findViewById(R.id.no);
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(dimensions.this, MainActivity.class));

            }
        });


        Bundle bundle = getIntent().getExtras();
        arraytopX = bundle.getFloatArray("arraytX");
        arraytopY = bundle.getFloatArray("arraytY");
        arraysideX = bundle.getFloatArray("arraysX");
        arraysideY = bundle.getFloatArray("arraysY");
        shoesize = bundle.getInt("Shoe_size");
        currentLocation = bundle.getString("Location");


//        Log.d("arraytopX[0]is",String.valueOf(arraytopX[0]));
//        Log.d("arraytopX[1]is",String.valueOf(arraytopX[1]));
//        Log.d("arraysideX[0]is",String.valueOf(arraysideX[0]));
//        Log.d("arraysideX[1]is",String.valueOf(arraysideX[1]));
//
//        Log.d("arraytopY[0]is",String.valueOf(arraytopY[0]));
//        Log.d("arraytopY[1]is",String.valueOf(arraytopY[1]));
//        Log.d("arraysideY[0]is",String.valueOf(arraysideY[0]));
//        Log.d("arraysideY[1]is",String.valueOf(arraysideY[1]));

//        Log.d("Array in topview X is:",arraytopX.toString());
//        Log.d("Array in topview Y is:",arraytopY.toString());
//        Log.d("Array in sideview X is:",arraysideX.toString());
//        Log.d("Array in sideview Y is:",arraysideY.toString());

        int severity = 0;

        switch(shoesize){
            case 37: refcm = (float)23.3;
            case 38: refcm = (float)24.0;
            case 39: refcm = (float)24.7;
            case 40: refcm = (float)25.3;
            case 41: refcm = (float)26.0;
            case 42: refcm = (float)26.7;
            case 43: refcm = (float)27.3;
            case 44: refcm = (float)28.0;
            case 45: refcm = (float)28.7;
            case 46: refcm = (float)29.3;
            case 0: refcm = (float)6.4;


        }


        float reft1, reft2, refs1, refs2;
        reft1 = arraytopX[0];
        reft2 = arraytopX[1];
        refs1 = arraysideX[0];
        refs2 = arraysideX[1];


        //Xdirtop = 0 means vertically aligned and we take Y values
        //Xdirside = 0 means vertically aligned and we take Y values

        if (reft2 - reft1 >= 50 || reft1 - reft2 >= 50) {
            Xdirtop = 1;
        }

        if (refs2 - refs1 >= 50 || refs1 - refs2 >= 50) {
            Xdirside = 1;
        }
//        Log.d("Xdirtop is:",String.valueOf(Xdirtop));
//        Log.d("Xdirside is:",String.valueOf(Xdirside));

        if (Xdirtop == 1) {
            //Take values from X array
            refpxl = Math.abs(arraytopX[1] - arraytopX[0]);
            Log.d("Reference pixel:", String.valueOf(refpxl));
            width1pxl = Math.abs(arraytopX[3] - arraytopX[2]);
            Log.d("Width1 pixel:", String.valueOf(width1pxl));
        }
        if (Xdirtop == 0) {
            //Take values from Y array
            refpxl = Math.abs(arraytopY[1] - arraytopY[0]);
            Log.d("Reference pixel:", String.valueOf(refpxl));
            width1pxl = arraytopY[3] - arraytopY[2];
            Log.d("Width1 pixel:", String.valueOf(width1pxl));
        }

        if (Xdirside == 1) {
            //Take values from X array
            width2pxl = arraysideX[1] - arraysideX[0];
            Log.d("Width2 pixel:", String.valueOf(width2pxl));
            depth2pxl = arraysideX[3] - arraysideX[2];
            Log.d("depth2 pixel:", String.valueOf(depth2pxl));
        }
        if (Xdirside == 0) {
            //Take values from Y array
            width2pxl = arraysideY[1] - arraysideY[0];
            Log.d("Width2 pixel:", String.valueOf(width2pxl));
            depth2pxl = arraysideY[3] - arraysideY[2];
            Log.d("Depth2 pixel:", String.valueOf(depth2pxl));

        }

        //Find Width in cm
        widthincm = detectWidth(refcm, refpxl, width1pxl);
        Log.d("Width in cm: ", String.valueOf(widthincm));


        //Find Depth in cm
        depthincm = detectDepth(refcm, refpxl, width1pxl, width2pxl, depth2pxl);
        Log.d("Depth in cm: ", String.valueOf(depthincm));

        //Severity = 0 // Not a pothole
        //Severity = 1 // Low level pothole
        //Severity = 2 // Medium level pothole
        //Severity = 3 // High level pothole
        if (depthincm >= 5.0 && depthincm <= 15.0) {
            severity = 2;

        } else if (depthincm >= 15.0 && depthincm <= 25.0) {
            severity = 3;
        } else {
            severity = 1;
        }
        Button b1 = (Button) findViewById(R.id.yes);
        final int finalSeverity = severity;
        b1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent in = new Intent(dimensions.this, issue.class);
                Bundle bundle = new Bundle();
                bundle.putFloat("Width",widthincm);
                bundle.putFloat("Depth",depthincm);
                bundle.putInt("Shoesize",shoesize);
                bundle.putInt("Severity", finalSeverity);
                bundle.putString("Location", currentLocation);

                in.putExtras(bundle);
                startActivity(in);
                //startActivity(new Intent(dimensions.this, issue.class));
            }
        });


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
        Log.d("Depth1 pixel:", String.valueOf(depth1pxl));

        depthcm = (depth1pxl*brefcm)/brefpxl;
        return depthcm;
    }

}

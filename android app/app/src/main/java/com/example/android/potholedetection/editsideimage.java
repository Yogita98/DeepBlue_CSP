package com.example.android.potholedetection;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class editsideimage extends AppCompatActivity implements PaintViewInterface{

    PaintView1 mPaintView;
    Button mBtnPick;
    int mWidth;
    int mHeight;
    Menu menu;
    public ArrayList<Float> arrayX1 = new ArrayList<>(10);
    public ArrayList<Float> arrayY1 = new ArrayList<>(10);
    float arrayt1X[];
    float arrayt1Y[];
    int shoesize;

//    public ArrayList<Float> arrayt1X = new ArrayList<>(10);
//    public ArrayList<Float> arrayt1Y = new ArrayList<>(10);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editsideimage);
        Button b1 = (Button) findViewById(R.id.btn_next);
        // arrayt1X = new float[10];
        //arrayt1Y = new float[10];

        Bundle bundle = getIntent().getExtras();

        arrayt1X = bundle.getFloatArray("arraytX");
        arrayt1Y = bundle.getFloatArray("arraytY");
        shoesize = bundle.getInt("Shoe_size");
        Log.d("Array topX[0] in side ", String.valueOf(arrayt1X[0]));
        Log.d("Array topY[0] in side ", String.valueOf(arrayt1Y[0]));

        final float arrayXf[];
        arrayXf = new float[10];
        final float arrayYf[];
        arrayYf = new float[10];


        b1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){


                int i = 0, j = 0;
                for (Float f : arrayX1) {
                    arrayXf[i++] = (f != null ? f : Float.NaN);
                }
                for (Float f : arrayY1) {
                    arrayYf[j++] = (f != null ? f : Float.NaN);
                }
//                Log.d("Array sideX is: ",arrayX1.toString());
//                Log.d("Array sideY is: ",arrayY1.toString());
//                Log.d("Array topX[0] is: ",String.valueOf(arrayXf[0]));
//                Log.d("Array topY[0] is: ",String.valueOf(arrayYf[0]));

                Intent in = new Intent(editsideimage.this, CurrentLocation.class);
                Bundle bundle = new Bundle();
                bundle.putFloatArray("arraytX", arrayt1X);
                bundle.putFloatArray("arraytY", arrayt1Y);
                bundle.putFloatArray("arraysX", arrayXf);
                bundle.putFloatArray("arraysY", arrayYf);
                bundle.putInt("Shoe_size",shoesize);
                in.putExtras(bundle);
                startActivity(in);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                editsideimage.this.finish();
            }
        });


        mWidth = mHeight = 0;
        mPaintView = (PaintView1)findViewById(R.id.paint_view);
        mBtnPick = (Button) findViewById(R.id.btn_pick);

        mBtnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                Intent customChooserIntent = Intent.createChooser(i, "Pick an image");
                startActivityForResult(customChooserIntent, 10);
            }
        });
        if(savedInstanceState!=null){
            mWidth = savedInstanceState.getInt("width");
            mHeight = savedInstanceState.getInt("height");
            Bitmap bitmap1 = savedInstanceState.getParcelable("bitmap");
            if(bitmap1!=null){
                mPaintView.addBitmap(bitmap1);
            }
        }
        mPaintView.setViewListener(this);
    }
    @Override
    public void onActionFinished(float movedX, float movedY) {
        /*Print it wherever you want here */
        arrayX1.add(movedX);
        arrayY1.add(movedY);

        //Log.d("ArrayX Side is:",arrayX.toString());
        //Log.d("ArrayY Side is:",arrayY.toString());
    }


    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if(height>reqHeight || width>reqWidth)
        {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            inSampleSize = heightRatio<widthRatio ? heightRatio : widthRatio;


        }

        return inSampleSize;
    }
    private Bitmap getBitmapFromUri(Uri data){
        Bitmap bitmap1 = null;
        InputStream is=null;
        try {
            is = getContentResolver().openInputStream(data);
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, options);
            options.inSampleSize = calculateInSampleSize(options, mWidth, mHeight);
            options.inJustDecodeBounds = false;
            is = getContentResolver().openInputStream(data);
            bitmap1 = BitmapFactory.decodeStream(is,null,options);
            if(bitmap1==null){
                Toast.makeText(getBaseContext(), "Image is not Loaded",Toast.LENGTH_SHORT).show();
                return null;
            }
            is.close();

        }catch (IOException e) {

            e.printStackTrace();

        }catch(NullPointerException e){
            e.printStackTrace();
        }
        return bitmap1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 10 && resultCode == RESULT_OK && null != intent) {
            Uri data = intent.getData();
            Bitmap bitmap1 = getBitmapFromUri(data);
            if(bitmap1!=null){
                mPaintView.addBitmap(bitmap1);
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mWidth = mPaintView.getWidth();
        mHeight = mPaintView.getHeight();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("width", mWidth);
        outState.putInt("height", mHeight);
        if(mPaintView.getBitmap()!=null){
            outState.putParcelable("bitmap", mPaintView.getBitmap());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        mPaintView.pause(false);
        mPaintView.invalidate();
        super.onResume();
    }

    @Override

    protected void onPause() {
        mPaintView.pause(true);
        super.onPause();
    }
}




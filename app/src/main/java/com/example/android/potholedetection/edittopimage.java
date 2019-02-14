package com.example.android.potholedetection;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class edittopimage extends AppCompatActivity implements PaintViewInterface{

    PaintView mPaintView;
    Button mBtnPick;
    TextView textView;
    int mWidth;
    int mHeight;
    Menu menu;
    Bitmap bitmap;
    //private float mX, mY, mOldX, mOldY;
    public ArrayList<Float> arrayX = new ArrayList<>(10);
    public ArrayList<Float> arrayY = new ArrayList<>(10);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittopimage);
        Button b1 = (Button) findViewById(R.id.btn_next);



        b1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent in = new Intent(edittopimage.this, editsideimage.class);
                in.putExtra("arraytX",arrayX);
                in.putExtra("arraytY",arrayY);
                startActivity(in);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //startActivity(new Intent(edittopimage.this,editsideimage.class));
                edittopimage.this.finish();
            }
        });

        mWidth = mHeight = 0;
        mPaintView = (PaintView)findViewById(R.id.paint_view);
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
            Bitmap bitmap = savedInstanceState.getParcelable("bitmap");
            if(bitmap!=null){
                mPaintView.addBitmap(bitmap);

            }
        }

        mPaintView.setViewListener(this);
    }
    @Override
    public void onActionFinished(float movedX, float movedY) {
        /*Print it wherever you want here */
        arrayX.add(movedX);
        arrayY.add(movedY);

        //Log.d("ArrayX Top is:",arrayX.toString());
        //Log.d("ArrayY Top is:",arrayY.toString());
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
        Bitmap bitmap = null;
        InputStream is=null;
        try {
            is = getContentResolver().openInputStream(data);
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, options);
            options.inSampleSize = calculateInSampleSize(options, mWidth, mHeight);
            options.inJustDecodeBounds = false;
            is = getContentResolver().openInputStream(data);
            bitmap = BitmapFactory.decodeStream(is,null,options);
            if(bitmap==null){
                Toast.makeText(getBaseContext(), "Image is not Loaded",Toast.LENGTH_SHORT).show();
                return null;
            }
            is.close();

        }catch (IOException e) {

            e.printStackTrace();

        }catch(NullPointerException e){
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 10 && resultCode == RESULT_OK && null != intent) {
            Uri data = intent.getData();
            Bitmap bitmap = getBitmapFromUri(data);
            if(bitmap!=null){
                mPaintView.addBitmap(bitmap);

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

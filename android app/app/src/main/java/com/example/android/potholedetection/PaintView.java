package com.example.android.potholedetection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class PaintView extends View implements View.OnTouchListener{

    Paint mPaint;
    Bitmap mBitmap;
    Matrix mMatrix;
    RectF mSrcRectF;
    RectF mDestRectF;
    boolean mPause;
    public ArrayList<Float> arrX = new ArrayList<>(10);
    public ArrayList<Float> arrY = new ArrayList<>(10);
    private final int DEFAULT_DOT_SIZE = 8;
    private final int MAX_DOT_SIZE = 100;
    private final int DEFAULT_COLOR = Color.GREEN;
    private int mDotSize;
    private int mPenColor;
    private Path mPath;
    private Paint mPaint1;
    private float mX,mY,mOldX,mOldY;
    private static ArrayList<Path> mPaths;
    private static ArrayList<Paint> mPaints;

    public PaintView(Context context){
        super(context);
        this.init();
    }


    public PaintView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        //arrX.add((float) 0);
        //arrY.add((float) 0);
        mPaint = new Paint();
        mMatrix = new Matrix();
        mSrcRectF = new RectF();
        mDestRectF = new RectF();
        mPause = false;
        this.init();
    }

    private void init(){
        this.mDotSize = DEFAULT_DOT_SIZE;
        this.mPenColor = DEFAULT_COLOR;
        this.mPaths = new ArrayList<Path>();
        this.mPaints = new ArrayList<Paint>();
        this.mX = this.mY = this.mOldX = this.mOldY = (float)0.0;
        this.setOnTouchListener(this);
    }

    private void addPath(boolean fill)
    {
        mPath = new Path();
        mPaths.add(mPath);
        mPaint1 = new Paint();
        mPaints.add(mPaint1);
        mPaint1.setColor(mPenColor);
        mPaint1.setStyle(Paint.Style.STROKE);
        mPaint1.setStrokeWidth(mDotSize);
    }

    public void setmPenColor(int penColor) {
        this.mPenColor = penColor;
    }

    public void addBitmap(Bitmap bitmap){
        mBitmap = bitmap;
    }

    public Bitmap getBitmap(){
        return mBitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        if(!mPause){
            if(mBitmap!=null){

                // Setting size of Source Rect
                mSrcRectF.set(0, 0,mBitmap.getWidth(),mBitmap.getHeight());

                // Setting size of Destination Rect
                mDestRectF.set(0, 0, getWidth(), getHeight());

                // Scaling the bitmap to fit the PaintView
                mMatrix.setRectToRect( mSrcRectF , mDestRectF, Matrix.ScaleToFit.CENTER);

                // Drawing the bitmap in the canvas
                canvas.drawBitmap(mBitmap, mMatrix, mPaint);
            }

            for(int i=0;i<mPaths.size();++i)
            {
                canvas.drawPath(mPaths.get(i),mPaints.get(i));
            }

            // Redraw the canvas
            invalidate();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {


        mX = motionEvent.getX();

        mY = motionEvent.getY();
        arrX.add(mX);
        arrY.add(mY);
        //Log.d("Touched PaintView:", "("+mX+","+mY+")");
        //Log.d("ArrayList for X:",arrX.toString());
        //Log.d("ArrayList for Y:",arrY.toString());

        switch(motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                this.addPath(true);
                this.mPath.addCircle(mX,mY,mDotSize/3, Path.Direction.CW);
                this.addPath(true);
                this.mPath.moveTo(mX,mY);
                break;

            case MotionEvent.ACTION_MOVE:
                this.addPath(true);
                this.mPath.addCircle(mX,mY,mDotSize/3, Path.Direction.CW);
                break;

            case MotionEvent.ACTION_UP:
                this.addPath(true);
                if(mOldX == mX && mOldY == mY)
                    this.mPath.addCircle(mX,mY,mDotSize/3, Path.Direction.CW);
                mInterface.onActionFinished(mX, mY);

                break;
        }
        this.invalidate();
        mOldX = mX;
        mOldY = mY;
        return true;
    }

    // Pause or resume onDraw method
    public void pause(boolean pause){
        mPause = pause;
    }

    private PaintViewInterface mInterface;


    public void setViewListener(edittopimage interface1) {
        mInterface = interface1;
    }
}
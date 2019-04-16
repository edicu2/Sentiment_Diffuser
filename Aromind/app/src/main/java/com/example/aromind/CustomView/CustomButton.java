package com.example.aromind.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.aromind.R;

public class CustomButton extends View{
    //circle and text colors
    private int circleCol, labelCol;
    //label text
    private String circleText;
    //paint for drawing custom view
    private Paint circlePaint;
    private boolean check;
    public static int[] circleCols;
    public static int[] tempcircleCols;
    private int alpha = 255;


    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        //paint object for drawing in onDraw
        circlePaint = new Paint();
        //get the attributes specified in attrs.xml using the name we included
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CustomButton, 0, 0);
        try {
            //get the text and colors specified using the names in attrs.xml
            circleText = a.getString(R.styleable.CustomButton_circleLabel);
            circleCol = a.getInteger(R.styleable.CustomButton_circleColor, 0);//0 is default
            labelCol = a.getInteger(R.styleable.CustomButton_labelColor, 0);
        } finally {
            a.recycle();
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        //get half of the width and height as we are working with a circle
        int viewWidthHalf = this.getMeasuredWidth()/2;
        int viewHeightHalf = this.getMeasuredHeight()/2;
        //get the radius as half of the width or height, whichever is smaller
//subtract ten so that it has some space around it
        int radius = 0;
        if(viewWidthHalf>viewHeightHalf)
            radius=viewHeightHalf-10;
        else
            radius=viewWidthHalf-10;
        circlePaint.setStyle(Style.FILL);
        circlePaint.setAntiAlias(true);
        //set the paint color using the circle color specified
        Log.i("mqtt_Color", String.valueOf(circleCols));
        circlePaint.setAlpha(alpha);
        circlePaint.setColor(Color.argb(255,255,255,255));
        if(circleCols != null) {
            circlePaint.setShader(new SweepGradient(170, 170, circleCols, null));
        }else{
            circlePaint.setShader(null);
            circlePaint.setColor(circleCol);
        }
        /*
        if(circleCols != null) {
            circlePaint.setShader(new SweepGradient(170, 170, circleCols, null));
            circleCols = null;
        }else {
            circlePaint.setShader(null);
            circlePaint.setColor(circleCol);
        }

*/
        canvas.drawCircle(viewWidthHalf, viewHeightHalf, radius, circlePaint);
        circlePaint.setShader(null);

    }
    public int getCircleColor(){
        return circleCol;
    }

    public int getLabelColor(){
        return labelCol;
    }

    public String getLabelText(){
        return circleText;
    }

    public void setCircleColor(int newColor){
        //update the instance variable
        circleCol=newColor;
        //redraw the view
        invalidate();
        requestLayout();
    }
    public void setLabelColor(int newColor){
        //update the instance variable
        labelCol=newColor;
        //redraw the view
        invalidate();
        requestLayout();
    }
    public void setLabelText(String newLabel){
        //update the instance variable
        circleText=newLabel;
        //redraw the view
        invalidate();
        requestLayout();
    }

    public void setCircleColors(int colors[]){
        this.circleCols=colors;
        this.tempcircleCols = colors;
        //redraw the view
        invalidate();
        requestLayout();
    }
}

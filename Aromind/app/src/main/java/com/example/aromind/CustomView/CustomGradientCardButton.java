package com.example.aromind.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import com.example.aromind.Activity.Gradient.ColorItem;
import com.example.aromind.R;

public class CustomGradientCardButton extends View{
    //circle and text colors
    private int  labelCol;
    private String circleCol;
    private int[] circleCols;
    //label text
    private String circleText;
    //paint for drawing custom view
    private Paint circlePaint;
    private int alpha = 255;

    public CustomGradientCardButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        //paint object for drawing in onDraw
        circlePaint = new Paint();
        //get the attributes specified in attrs.xml using the name we included
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CustomGradientCardButton, 0, 0);
        try {
            //get the text and colors specified using the names in attrs.xml
            circleText = a.getString(R.styleable.CustomGradientCardButton_circleLabel);
            circleCol = a.getString(R.styleable.CustomGradientCardButton_circleColor);//0 is default
            labelCol = a.getInteger(R.styleable.CustomGradientCardButton_labelColor, 0);
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
        //circlePaint.setColor(circleCol);
        circlePaint.setAlpha(alpha);
        circlePaint.setShader(new SweepGradient(500, 500, circleCols,null));
        canvas.drawCircle(viewWidthHalf, viewHeightHalf, radius, circlePaint);
        //set the text color using the color specified
        //set text properties
        //draw the text using the string attribute and chosen properties
        //canvas.drawText(circleText, viewWidthHalf, viewHeightHalf, circlePaint);


    }
    public  Shader getCircleColor(){
        //return new RadialGradient(80, 80, 70,Integer.parseInt(circleCol.split("_")[0]),Integer.parseInt(circleCol.split("_")[1]), Shader.TileMode.CLAMP);
        //return new SweepGradient(30 , 30, Color.WHITE, Color.BLACK, Shader.TileMode.CLAMP);
        //return new SweepGradient(80, 80, Color.BLUE, Color.WHITE, Shader.TileMode.CLAMP);
        //return (new LinearGradient(0, 0, 100, 0, Color.BLUE, Color.WHITE, Shader.TileMode.CLAMP));
        return new RadialGradient(90, 90, 80,Color.WHITE, Color.BLUE, Shader.TileMode.CLAMP);
    }

    public int getLabelColor(){
        return labelCol;
    }

    public String getLabelText(){
        return circleText;
    }

    public void setCircleColor(String newColor){
        //update the instance variable
        circleCol=newColor;
        //redraw the view
        invalidate();
        requestLayout();
    }
    public void setAlpha(int alpha){
        this.alpha = alpha;
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
        //redraw the view
        invalidate();
        requestLayout();
    }

}

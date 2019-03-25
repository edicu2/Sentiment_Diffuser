package com.example.aromind.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.aromind.CustomView.CustomButton;
import com.example.aromind.Model.Mqtt;
import com.example.aromind.R;

public class GradientAddActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private Bitmap bitmap;
    private CustomButton colorCheck;
    private ToggleButton colorPower;
    private Button btn_ok, btn_cancel;
    private ImageView color;
    private int r,g,b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams= new WindowManager.LayoutParams();
//팝업 외부 뿌연 효과
        layoutParams.flags= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//뿌연 효과 정도
        layoutParams.dimAmount= 0.7f;
//적용
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.activity_gradient_add);


        color = (ImageView)findViewById(R.id.color);
        color.setDrawingCacheEnabled(true);
        color.buildDrawingCache(true);
        color.setOnTouchListener(this);
        colorCheck = (CustomButton)findViewById(R.id.color_check);
        colorPower = (ToggleButton)findViewById(R.id.colorPower);
        colorPower.setChecked(false);
        btn_ok = (Button)findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_ok){
            if (!(r == 0 && g == 0 && b == 0)) {
                Intent intent = new Intent();
                intent.putExtra("r", r);
                intent.putExtra("g", g);
                intent.putExtra("b", b);
                setResult(RESULT_OK, intent);
            }
            finish();
        }else if(v.getId() == R.id.btn_cancel){
            finish();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
            bitmap = color.getDrawingCache();
            int px = bitmap.getPixel((int)event.getX(),(int)event.getY());
            if (!(Color.red(px) == 0 && Color.green(px) == 0 && Color.blue(px) == 0)) {
                r = Color.red(px);
                g = Color.green(px);
                b = Color.blue(px);
                colorPower.setChecked(true);
                colorCheck.setCircleColor(Color.rgb(r, g, b));
            }
        }
        return false;
    }
}


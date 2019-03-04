package com.example.diffuser.Activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;


import com.example.diffuser.Interface.MainInterface;
import com.example.diffuser.Model.MqttConnect;
import com.example.diffuser.Presenter.MainPresenter;
import com.example.diffuser.R;

import static com.example.diffuser.Data.Setting.b;
import static com.example.diffuser.Data.Setting.g;
import static com.example.diffuser.Data.Setting.bright;
import static com.example.diffuser.Data.Setting.r;

public class MainActivity extends AppCompatActivity implements MainInterface.View, View.OnClickListener, CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener, View.OnTouchListener {

    private MainPresenter presenter;

    //colorPicker
    private ImageView imageView;
    private Bitmap bitmap;

    //temp
    private TextView temp ;

    //brightness
    private SeekBar brightness;

    //aroma
    private ToggleButton aromaButton1, aromaButton2, aromaButton3;
    private Button menuButton1, menuButton2, menuButton3;

    //MQTT
    private ToggleButton powerButton, colorButton;
    String payload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(MainActivity.this, getApplicationContext(),this);
        presenter.presenterView();
        // 실시간 온도 체크
        presenter.mqttSubCallback(temp);
    }
    @Override
    public void setView() {

        temp = (TextView)findViewById(R.id.temp);
        //ColorPicker
        imageView=(ImageView)findViewById(R.id.imageView);
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache(true);
        imageView.setOnTouchListener(this);

        brightness = (SeekBar) findViewById(R.id.brightness);
        brightness.setOnSeekBarChangeListener(this);

        //aromaButton and Listener
        aromaButton1=(ToggleButton)findViewById(R.id.aromaButton1);
        aromaButton2=(ToggleButton)findViewById(R.id.aromaButton2);
        aromaButton3=(ToggleButton)findViewById(R.id.aromaButton3);
        aromaButton1.setOnCheckedChangeListener(this);
        aromaButton2.setOnCheckedChangeListener(this);
        aromaButton3.setOnCheckedChangeListener(this);

        //colorButton and Listener
        colorButton=(ToggleButton)findViewById(R.id.colorButton);
        colorButton.setBackgroundColor(255);
        colorButton.setChecked(false);
        colorButton.setOnCheckedChangeListener(this);

        //powerButton and Listener
        powerButton=(ToggleButton)findViewById(R.id.powerButton);
        powerButton.setChecked(true);
        powerButton.setOnCheckedChangeListener(this);
        menuButton1 = (Button)findViewById(R.id.menuButton1);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(CompoundButton v, boolean isChecked) {
        if (isChecked == true){
            if(v.getResources().getResourceEntryName(v.getId()).equals("colorButton")) {
                brightness.setProgress(125);
                powerButton.setChecked(true);
            }
            payload = v.getResources().getResourceEntryName(v.getId())+"_ON";
        }else {
            if(v.getResources().getResourceEntryName(v.getId()).equals("colorButton")) {
                brightness.setProgress(0);
            }
            else if(v.getResources().getResourceEntryName(v.getId()).equals("powerButton")) {
                brightness.setProgress(0);
                colorButton.setChecked(false);
                aromaButton1.setChecked(false);
                aromaButton2.setChecked(false);
                aromaButton3.setChecked(false);
            }
            payload = v.getResources().getResourceEntryName(v.getId())+"_OFF";
        }
        Log.i("button", payload);
        MqttConnect.clientPub(MainActivity.this,payload);
    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(progress == 0)
            colorButton.setChecked(false);
        else {
            powerButton.setChecked(true);
            colorButton.setChecked(true);
            brightness.setProgress(progress);
        }
        presenter.setBright(bright);
        payload = "colorPicker_"+r+","+g+","+b+","+(float)bright/255;
        Log.i("colorNum", payload);
        MqttConnect.clientPub(MainActivity.this,payload);
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
            if(colorButton.isChecked() == false) {
                colorButton.setChecked(true);
                powerButton.setChecked(true);
            }
            bitmap = imageView.getDrawingCache();
            int pixel = bitmap.getPixel((int) event.getX(), (int) event.getY());
            presenter.setColor(pixel,bright);
            if (r == 0 && g == 0 && b == 0) {
                colorButton.setBackgroundColor(Color.rgb(255, 255, 255));
                payload = "colorPicker_255,255,255," + (float) bright / 255;
            } else {
                colorButton.setBackgroundColor(Color.argb(255, r, g, b));
                payload = "colorPicker_" + String.valueOf(r) + "," + String.valueOf(g) + "," + String.valueOf(b) + "," + (float) bright / 255;
            }
            Log.i("colorNum", payload);
            MqttConnect.clientPub(MainActivity.this,payload);
        }
        return false;
    }
}

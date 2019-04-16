package com.example.aromind.Activity.MenuAlarm_Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.aromind.R;

public class Popup_alarmInterval extends AppCompatActivity implements View.OnClickListener {

    private RadioGroup interval_Group, repeat_Group;
//    private RadioButton interval_5m, interval_10m, interval_15m, interval_30m;
//    private RadioButton repeat_no, repeat_2, repeat_3;
    private Button submit, cancle;
    private RadioButton interval_btn, repeat_btn;
    private int interval, repeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_alarm_interval);

        interval_Group = findViewById(R.id.interval_Group);
        repeat_Group = findViewById(R.id.repeat_Group);

        interval_Group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.interval_5m:
                        interval = 5;
                        break;
                    case R.id.interval_10m:
                        interval = 10;
                        break;
                    case R.id.interval_15m:
                        interval = 15;
                        break;
                    case R.id.interval_30m:
                        interval = 30;
                        break;
                    default:
                        interval = 0;
                }
            }
        });

        repeat_Group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.repeat_no:
                        repeat = 0;
                        break;
                    case R.id.repeat_2:
                        repeat = 2;
                    case R.id.repeat_3:
                        repeat = 3;
                        break;
                    default:
                        repeat = 0;
                }
            }
        });

//        interval_5m = findViewById(R.id.interval_5m);
//        interval_10m = findViewById(R.id.interval_10m);
//        interval_15m = findViewById(R.id.interval_15m);
//        interval_30m = findViewById(R.id.interval_30m);
//
//        repeat_no = findViewById(R.id.repeat_no);
//        repeat_2 = findViewById(R.id.repeat_2);
//        repeat_3 = findViewById(R.id.repeat_3);

        //button
        submit = findViewById(R.id.submit);
        cancle = findViewById(R.id.cancle);

        submit.setOnClickListener(this);
        cancle.setOnClickListener(this);

        setResult(Setting_Alarm.RESULT_NG);
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.submit){
            Toast.makeText(this, String.valueOf(interval)+String.valueOf(repeat), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("interval", interval);
            intent.putExtra("repeat", repeat);
            setResult(Setting_Alarm.RESULT_OK, intent);
            finish();

        }else if(v.getId() == R.id.cancle);
            finish();

    }
}

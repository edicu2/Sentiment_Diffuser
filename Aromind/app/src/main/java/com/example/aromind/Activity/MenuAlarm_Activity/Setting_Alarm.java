package com.example.aromind.Activity.MenuAlarm_Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.aromind.Activity.MainActivity_fragment.MenuAlarm;
import com.example.aromind.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.widget.Toast.LENGTH_SHORT;

public class Setting_Alarm extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    private int colorPrimaryfont, colorPrimaryDark;

    public static final int REQ_NUM = 1;
    public static final int RESULT_OK = 11;
    public static final int RESULT_NG = 12;

    //days
    private ToggleButton[] days_btn = new ToggleButton[7];

    private ArrayList<Integer> week_setting = new ArrayList<Integer>();
    private boolean[] week = new boolean[8];
    private String[] weeks_setting = {"","일","월","화","수","목","금","토"};
    private ArrayList<String> weeks_array = new ArrayList<String>();
    private String set_days ="";

    //timepicker
    private TimePicker timePicker;
    private String am_pm, time;
    private int hour, min;
    int temphour;
    int tempmin;

    //listView
    private ListView listView;
    Setting_Alarm_Adapter adapter;

    //cancle/submit
    private Button cancle, submit;

    //setting alarm
    private int interval, repeat;
    private String alarm_name;
    private TextView timeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting__alarm);

        colorPrimaryfont = (this).getResources().getColor(R.color.colorPrimaryfont);
        colorPrimaryDark = (this).getResources().getColor(R.color.colorPrimaryDark);

        submit = findViewById(R.id.submit);
        cancle =  findViewById(R.id.cancle);

        submit.setOnClickListener(this);
        cancle.setOnClickListener(this);

        days_btn[0] = findViewById(R.id.sun);
        days_btn[1] = findViewById(R.id.mon);
        days_btn[2] = findViewById(R.id.tue);
        days_btn[3] = findViewById(R.id.wed);
        days_btn[4] = findViewById(R.id.thr);
        days_btn[5] = findViewById(R.id.fri);
        days_btn[6] = findViewById(R.id.str);



        for (int i=0; i<days_btn.length; i++){
            days_btn[i].setOnCheckedChangeListener(this);
        }

        timeView = findViewById(R.id.time);

        //listMenu
        listView = findViewById(R.id.menu_list);
        adapter = new Setting_Alarm_Adapter();

        listView.setAdapter(adapter);

        if (alarm_name != null || alarm_name==""){
            adapter.addItem("알람 이름", "");
        }else{
            adapter.addItem("알람 이름", alarm_name);
        }

        adapter.addItem("카드 설정", "");
        adapter.addItem("분사 설정", "");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    Intent intent = new Intent(Setting_Alarm.this, Popup_alarmName.class);
                    startActivityForResult(intent, REQ_NUM);
                }else if (position == 1){
                    Toast.makeText(Setting_Alarm.this, "Sorry Bro", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(Setting_Alarm.this, Popup_alarmSound.class);
//                    startActivityForResult(intent, REQ_NUM);
                }else if(position == 2){
                    Intent intent = new Intent(Setting_Alarm.this, Popup_alarmInterval.class);
                    startActivityForResult(intent, REQ_NUM);
                }
            }

        });

        //timepicker
        timePicker = findViewById(R.id.time_picker);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                hour = hourOfDay;
                min = minute;

                temphour = hourOfDay;
                tempmin = minute;

                if(hourOfDay >= 12){
                    temphour = hourOfDay - 12;
                    am_pm = "pm";
                    if (temphour == 0){
                        temphour = 12;
                    }
                }else {
                    am_pm = "am";
                }

                if (tempmin < 10){
                    time = String.valueOf(temphour)+" : 0"+String.valueOf(tempmin);
                    Toast.makeText(Setting_Alarm.this, time, Toast.LENGTH_SHORT).show();
                }else {
                    time = String.valueOf(temphour) + " : " + String.valueOf(tempmin);
                }
            }
        });

        setResult(MenuAlarm.RESULT_NG);
    }

    @Override
    public void onCheckedChanged(CompoundButton v, boolean isChecked) {
        if (isChecked){
            v.setBackgroundResource(R.drawable.toggle_circle_btn_on);
            v.setTextColor(colorPrimaryDark);
        }else{
            v.setBackgroundResource(R.drawable.toggle_circle_btn_off);
            v.setTextColor(colorPrimaryfont);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQ_NUM){
            if(resultCode == RESULT_OK){
                if(data != null) {
                    //data를 받아왔을때
                    String alarm_name = data.getStringExtra("alarm_name");
                    int interval = data.getIntExtra("interval", 0);
                    int repeat = data.getIntExtra("repeat", 0);

                    //alarm_name 받아오기
                    if (alarm_name != null) {
                        this.alarm_name = alarm_name;
                    } else {
                        this.alarm_name = "";
                    }//end if (alarm_name != null)

                    //interval 받아오기
                    if (interval != RESULT_NG) {
                        this.interval = interval;
                    } else {
                        this.interval = 0;
                    }//end if (interval != RESULT_NG)

                    //repeat 받아오기
                    if (repeat !=0) {
                        Toast.makeText(this, String.valueOf(repeat), Toast.LENGTH_SHORT).show();
                        this.repeat = repeat;
                    } else {
                        this.repeat=0;
                    }//end if (repeat !=0)
                } else
                    Toast.makeText(this, "null data", LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Result NG", LENGTH_SHORT).show();
        }
    }

    private void setting_days(){
        boolean[] week = {false, days_btn[0].isChecked(), days_btn[1].isChecked(), days_btn[2].isChecked(), days_btn[3].isChecked(),
                           days_btn[4].isChecked(), days_btn[5].isChecked(), days_btn[6].isChecked()};
        this.week = week;
//        Log.i("Timess", String.valueOf(this.week[1])+"  "+String.valueOf(this.week[2])+"  "+String.valueOf(this.week[3])+"  "+
//                String.valueOf(this.week[4])+"  "+String.valueOf(this.week[5])+"  "+String.valueOf(this.week[6])+"  "+String.valueOf(this.week[7]));

        for (int i=0; i<week.length; i++){
            if (week[i] == true){
                if (week[1]==true&&week[2]==true&&week[3]==true&&week[4]==true&&week[5]==true&&week[6]==true&&week[7]==true){
                    set_days = "매일";
                }else if(week[2]==true&&week[3]==true&&week[4]==true&&week[5]==true&&week[6]==true&&week[1]==false&&week[7]==false){
                    set_days = "평일";
                }else if(week[1]==true&&week[7]==true&&week[2]==false&&week[3]==false&&week[4]==false&&week[5]==false&&week[6]==false){
                    set_days = "주말";
                }else {
                    set_days += weeks_setting[i]+" ";
                }
            }
        }
        Log.i("Timess", set_days);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit){
            setting_days();
            Intent data = new Intent();
            data.putExtra("alarm_name", alarm_name);
            data.putExtra("am_pm", am_pm);
            data.putExtra("time", time);
            data.putExtra("set_days", set_days);
            data.putExtra("hour", hour);
            data.putExtra("min", min);
            data.putExtra("week", week);
            setResult(MenuAlarm.RESULT_OK, data);
            finish();

        }else if(v.getId() == R.id.cancle) {
            finish();
        }
    }
}

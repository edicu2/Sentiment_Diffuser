package com.example.aromind.Activity.MainActivity_fragment;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.aromind.Activity.MenuAlarm_Activity.Setting_Alarm;
import com.example.aromind.Activity.MenuRemote_RecyclerView.RecyclerViewAdapter;
import com.example.aromind.MenuAlarm_RecyclerView.HistoryAdapter;
import com.example.aromind.MenuAlarm_RecyclerView.HistoryBean;
import com.example.aromind.R;

import java.util.ArrayList;

import static android.content.Context.ALARM_SERVICE;
import static android.widget.Toast.LENGTH_SHORT;

public class MenuAlarm extends Fragment{

    private Button add_alarm;
    private RecyclerView list;
    private HistoryAdapter adapter;
    private ArrayList<HistoryBean> data = new ArrayList<HistoryBean>();
    private HistoryBean bean;
    private Context context;
    private Switch on_off;

    public static final int REQ_NUM = 1;
    public static final int RESULT_OK = 11;
    public static final int RESULT_NG = 12;

    private String am_pm, time, alarm_name, set_days;
    private int hour, min, repeat, interval;
    private boolean[] week = new boolean[8];
    private AlarmManager alarmManager;
    private String card_title;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_alarm, container, false);

        add_alarm = (Button) view.findViewById(R.id.add_alarm);
        add_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Setting_Alarm.class);
                startActivityForResult(intent, REQ_NUM);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        list = view.findViewById(R.id.alarm_list);
        list.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQ_NUM){
            if(resultCode == RESULT_OK){
                if(data != null) {

                    //data를 받아왔을때
                    String alarm_name = data.getStringExtra("alarm_name");
                    String am_pm = data.getStringExtra("am_pm");
                    String time = data.getStringExtra("time");
                    String set_days = data.getStringExtra("set_days");
                    int hour = data.getIntExtra("hour", 0);
                    int min = data.getIntExtra("min", 0);
                    boolean[] week = data.getBooleanArrayExtra("week");
                    String card_title = data.getStringExtra("card_title");
                    int interval = data.getIntExtra("interval", 0);
                    int repeat = data.getIntExtra("repeat", 0);

                    Log.i("Timess", String.valueOf(week[1])+"  "+String.valueOf(week[2])+"  "+String.valueOf(week[3])+"  "+
                            String.valueOf(week[4])+"  "+String.valueOf(week[5])+"  "+String.valueOf(week[6])+"  "+String.valueOf(week[7]));

                    Log.i("Timess", String.valueOf(hour)+":"+String.valueOf(min));

                    if (alarm_name != null) {
                        //Toast.makeText(this, result, LENGTH_SHORT).show();
                        this.alarm_name = alarm_name;
                    } else {
                        Toast.makeText(getActivity(), "null alarm_name", LENGTH_SHORT).show();
                    }
                    if (am_pm != null) {
                        //Toast.makeText(this, result, LENGTH_SHORT).show();
                        this.am_pm = am_pm;
                    } else {
                        Toast.makeText(getActivity(), "null am_pm", LENGTH_SHORT).show();
                    }
                    if (time != null) {
                        //Toast.makeText(this, result, LENGTH_SHORT).show();
                        this.time = time;
                    } else {
                        Toast.makeText(getActivity(), "null time", LENGTH_SHORT).show();
                    }
                    if (set_days != null) {
                        //Toast.makeText(this, result, LENGTH_SHORT).show();
                        this.set_days = set_days;
                    } else {
                        Toast.makeText(getActivity(), "null set_days", LENGTH_SHORT).show();
                    }
                    if (hour != 0){
                        this.hour = hour;
                    } else{
                        Toast.makeText(getActivity(), "null hour", LENGTH_SHORT).show();
                    }
                    if (min != 0){
                        this.min = min;
                    } else{
                        Toast.makeText(getActivity(), "null min", LENGTH_SHORT).show();
                    }
                    if (week != null){
                        this.week = week;
                    } else{
                        Toast.makeText(getActivity(), "null week", LENGTH_SHORT).show();
                    }if (card_title != null){
                        this.card_title = card_title;
                    }else {
                        Toast.makeText(getActivity(), "null card_title", LENGTH_SHORT).show();
                    }if (interval != 0){
                        this.interval = interval;
                    }else {
                        Toast.makeText(getActivity(), "null interval", LENGTH_SHORT).show();
                    }if (repeat != 0){
                        this.repeat = repeat;
                    }else {
                        Toast.makeText(getActivity(), "null repeat", LENGTH_SHORT).show();
                    }

                    settingList();

                }else
                    Toast.makeText(getActivity(), "null data", LENGTH_SHORT).show();
            } else
                Toast.makeText(getActivity(), "Result NG", LENGTH_SHORT).show();
        }
    }

    private void settingList(){
        bean = new HistoryBean();
        bean.setSet_am_pm(am_pm);
        bean.setSet_Tiem(time);
        bean.setSet_day(set_days);
        bean.setSet_alarm_name(alarm_name);
        bean.setSet_hour(hour);
        bean.setSet_min(min);
        bean.setSet_week(week);
        bean.setCard_title(card_title);
        bean.setInterval(interval);
        bean.setRepeat(repeat);
        data.add(bean);
    //일단 이부분에서 오류가 나는데 어뎁터에 무슨일이 생긴건가
        adapter = new HistoryAdapter(getActivity(), data);
        list.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//        adapter.getItemCount();
    }

}
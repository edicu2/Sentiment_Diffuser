package com.example.aromind.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.aromind.Activity.MainActivity_fragment.BottomNavigationHelper;
import com.example.aromind.Activity.MainActivity_fragment.MenuAlarm;
import com.example.aromind.Activity.MainActivity_fragment.MenuEmotion;
import com.example.aromind.Activity.MainActivity_fragment.MenuMypage;
import com.example.aromind.Activity.MainActivity_fragment.MenuRemote;
import com.example.aromind.Activity.MainActivity_fragment.MenuSetting;
import com.example.aromind.Model.Mqtt;
import com.example.aromind.R;

public class MainActivity extends AppCompatActivity {

    private TextView title;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private MenuMypage p_mypage = new MenuMypage();
    private MenuRemote p_remote = new MenuRemote();
    private MenuAlarm p_alarm = new MenuAlarm();
    private MenuEmotion p_emotion = new MenuEmotion();
    private MenuSetting p_setting = new MenuSetting();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Mqtt Connect
        //Mqtt mqttConnect = new Mqtt(this);

        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        title = (TextView)findViewById(R.id.title);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        // 첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, p_remote).commitAllowingStateLoss();
        bottomNavigationView.setSelectedItemId(R.id.remote);
        title.setText("Remote");

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.remote: {
                        transaction.replace(R.id.frame_layout, p_remote).commitAllowingStateLoss();
                        title.setText("Remote");
                        break;
                    }
                    case R.id.alarm: {
                        transaction.replace(R.id.frame_layout, p_alarm).commitAllowingStateLoss();
                        title.setText("Alarm");
                        break;
                    }
                    case R.id.emotion: {
                        transaction.replace(R.id.frame_layout, p_emotion).commitAllowingStateLoss();
                        title.setText("Emotion");
                        break;
                    }
                    case R.id.setting: {
                        transaction.replace(R.id.frame_layout, p_setting).commitAllowingStateLoss();
                        title.setText("Setting");
                        break;
                    }
                    case R.id.mypage: {
                        transaction.replace(R.id.frame_layout, p_mypage).commitAllowingStateLoss();
                        title.setText("My Page");
                        break;
                    }
                }
                return true;
            }
        });

    }
}

package com.example.aromind.Activity.MenuAlarm_Activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aromind.R;

public class Popup_alarmName extends AppCompatActivity implements View.OnClickListener {

    private EditText alarm_name;
    private Button submit, cancle;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_alarm_name);

        alarm_name = findViewById(R.id.alarm_name);

        submit = findViewById(R.id.submit);
        cancle = findViewById(R.id.cancle);

        submit.setOnClickListener(this);
        cancle.setOnClickListener(this);

        setResult(Setting_Alarm.RESULT_NG);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit){
            name = alarm_name.getText().toString();
            //Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
            Intent data = new Intent();
            data.putExtra("alarm_name", name);
            setResult(Setting_Alarm.RESULT_OK, data);
            finish();

        }else if (v.getId() == R.id.cancle){
            finish();

        }
    }

}

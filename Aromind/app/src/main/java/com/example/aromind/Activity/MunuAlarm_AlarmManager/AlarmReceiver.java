package com.example.aromind.Activity.MunuAlarm_AlarmManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("chk1", "리시버 도착");

        boolean one_time = intent.getBooleanExtra("one_time", false);
        String card_title = intent.getStringExtra("card_title");
        Log.i("chk1", String.valueOf(one_time));

        if (one_time){

            Intent service_Intent = new Intent(context, AlarmService.class);
            service_Intent.putExtra("card_title", card_title);
            context.startService(service_Intent);
            Log.i("chk1", "서비스 전송");

        }else {
            boolean[] week = intent.getBooleanArrayExtra("week");
//            Log.i("chk1", String.valueOf(week[1])+" "+String.valueOf(week[2])+" "+String.valueOf(week[3])+" "+
//                    String.valueOf(week[4])+" "+String.valueOf(week[5])+" "+String.valueOf(week[6])+" "+String.valueOf(week[7]));

            Calendar calendar = Calendar.getInstance();
            Log.i("chk1", String.valueOf(Calendar.DAY_OF_WEEK));

            if (!week[calendar.get(Calendar.DAY_OF_WEEK)]){
                return;

            }else {
                Intent service_Intent = new Intent(context, AlarmService.class);
                service_Intent.putExtra("card_title", card_title);
                context.startService(service_Intent);
                Log.i("chk1", "요일 서비스 전송");
            }
        }


    }
}

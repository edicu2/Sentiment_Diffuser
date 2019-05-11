package com.example.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManagerCompat notificationCompat;



        String message = intent.getStringExtra("message");
//        String neutural = intent.getStringExtra("message");
//        String negative = intent.getStringExtra("message");
//        if (positive != null){
//            Toast.makeText(context, positive, Toast.LENGTH_SHORT).show();
//        }else if (neutural != null){
//            Toast.makeText(context, neutural, Toast.LENGTH_SHORT).show();
//        }else {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            //알람 선택한후 끔
        NotificationManagerCompat.from(context).cancel(1);

    }
}

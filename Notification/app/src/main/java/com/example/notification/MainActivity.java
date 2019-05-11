package com.example.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import static com.example.notification.App.CHANNEL_1_ID;
import static com.example.notification.App.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManagerCompat;
    private EditText editText1, editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManagerCompat = NotificationManagerCompat.from(this);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
    }


    public void sendOnChanel1(View view){
        String title = editText1.getText().toString();
        String message = editText2.getText().toString();

        Intent activtyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, activtyIntent, 0);

        Intent positiveIntent = new Intent(this, NotificationReceiver.class);
        positiveIntent.setAction("action.NOTIFICATION");
        positiveIntent.putExtra("message", "positive");
        PendingIntent actionIntent = PendingIntent.getBroadcast(this, 0, positiveIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Intent neutralIntent = new Intent(this, NotificationReceiver.class);
        neutralIntent.setAction("AA");
        neutralIntent.putExtra("message", "neutral");
        PendingIntent actionIntent2 = PendingIntent.getBroadcast(this, 0, neutralIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.i("아로마다2", "2");

        Intent negativeIntent = new Intent(this,  NotificationReceiver.class);
        negativeIntent.setAction("BB");
        negativeIntent.putExtra("message", "negative");
        PendingIntent actionIntent3 = PendingIntent.getBroadcast(this, 0, negativeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.i("아로마다3", "3");

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_one)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.MAGENTA)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setContentTitle("Aromind")
                .setContentText("What your Setiment this Time")
                .addAction(R.mipmap.ic_launcher, "     Positive", actionIntent)
                .addAction(R.mipmap.ic_launcher, "Neutral", actionIntent2)
                .addAction(R.mipmap.ic_launcher, "Nagative",actionIntent3)
                .build();

        notificationManagerCompat.notify(1, notification);

    }

    public void sendOnChannel2(View view){
        String title = editText1.getText().toString();
        String message = editText2.getText().toString();

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_two)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManagerCompat.notify(2, notification);
    }
}

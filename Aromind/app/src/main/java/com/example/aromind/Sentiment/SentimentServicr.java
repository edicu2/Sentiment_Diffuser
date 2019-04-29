package com.example.aromind.Sentiment;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Telephony;
import android.util.Log;

import com.example.aromind.Model.NaturalLanguage;
import com.example.aromind.facebook.Facebook;
import com.example.aromind.facebook.afslkdfj;


import java.util.ArrayList;

public class SentimentServicr extends Service {

    String text;
    Context context = this;

    public SentimentServicr() {
    }

    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Sms sms = new Sms(context);
        ArrayList<String> arrayList = sms.getArrayList();

        if (arrayList == null || arrayList.size() <1){
            Log.i("AAAMW", "No Data");
        }else {
            Log.i("AAAMW", String.valueOf(arrayList));
        }

        String message = intent.getStringExtra("message");
        Log.i("AAAAWA", message);
        arrayList.add(message);
        for (int i=0; i<arrayList.size(); i++){
            Log.i("TTT3", String.valueOf(arrayList.get(i)));
        }
        NaturalLanguage nl = new NaturalLanguage(context, arrayList);

        return START_NOT_STICKY;
    }
}
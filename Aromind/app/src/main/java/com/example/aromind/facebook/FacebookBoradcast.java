package com.example.aromind.facebook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.aromind.Model.NaturalLanguage;
import com.example.aromind.Sentiment.SentimentServicr;
import com.example.aromind.Sentiment.Sms;

import java.util.ArrayList;

public class FacebookBoradcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Sms sms = new Sms(context);
        ArrayList<String> arrayList = sms.getArrayList();

//        afslkdfj afslkdfj = new afslkdfj();
//        String fbMessage = afslkdfj.getMessage();
//        if (fbMessage != null){
//            Log.i("fbMessage", fbMessage);
//            arrayList.add(fbMessage);
//        }

        String message = intent.getStringExtra("message");
        if (message != null) {
            Log.i("BroMessage", message);
            arrayList.add(message);
        }

//        Intent data = new Intent(context, SentimentServicr.class);
//        data.putExtra("message", arrayList);
//        context.startService(data);
    }
}

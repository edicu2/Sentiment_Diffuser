package com.example.aromind.Face;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.aromind.Sentiment.SentimentServicr;
import com.example.aromind.facebook.afslkdfj;

public class MyBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // intent ..

        String imageUrl = intent.getStringExtra("imageUrl");
        Log.i("imageUrl" , imageUrl);
        Face f = new Face(imageUrl);

        afslkdfj afslkdfj= new afslkdfj(context);


    }
}
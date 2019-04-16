package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // intent ..
        String imageUrl = intent.getStringExtra("imageUrl");
        Log.i("imageUrl" , imageUrl);
        Face f = new Face(imageUrl);

    }
}
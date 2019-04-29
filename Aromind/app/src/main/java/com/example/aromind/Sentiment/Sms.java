package com.example.aromind.Sentiment;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Sms {

    ArrayList<String> arrayList = new ArrayList<String>();

    // JSON sned Server
    JSONObject jsonObject = new JSONObject();
    JSONArray jsonArray = new JSONArray();


    public Sms(Context context){
        getSms(context);
    }

    private void getSms(Context context){
        Log.i("TTTTY", "startgetSms");
        //Time now
        long now = System.currentTimeMillis();
        //user want to Time  내가 원하는 시간*3600*1000
        //2*3600*1000
        long userTime = now-7200000;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        String getTime = sdf.format(userTime);


        // Create Sent box URI
        Uri sentURI = Uri.parse("content://sms/sent");

        // List required columns
        String[] reqCols = new String[] { "_id", "address", "body", "date"};

        // Get Content Resolver object, which will deal with Content Provider
        ContentResolver cr = context.getContentResolver();
        // Fetch Sent SMS Message from Built-in Content Provider
        //첫번째: URI 두번째: 무엇을 가져올것이냐 세번째: 무엇을 검색하냐 네번째: 검색할 값 다섯번째: oderBy
        Cursor c = cr.query(sentURI, reqCols, "date>?", new String[]{String.valueOf(userTime)}, null);
        int bodyidx = c.getColumnIndex("body");

        while (c.moveToNext()){
            String body = c.getString(bodyidx);
            Log.i("TTTT",body);
            arrayList.add(body);
        }
    }

    public ArrayList<String> getArrayList(){
        return arrayList;
    }
}

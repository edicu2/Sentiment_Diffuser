package com.example.aromind.Sentiment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.util.Log;

import com.example.aromind.Model.Custom_gradient_DBHelper;
import com.example.aromind.Model.Custom_power_DBHelper;
import com.example.aromind.Model.Mqtt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

import androidx.annotation.Nullable;

public class Sentiment_HistoryHelper extends SQLiteOpenHelper {

    private String tb_name;
    private Double score;
    private ConnectHttpServer connectHttpServer;
    public double avg;
    private int totalColumn;
    private int totalCnt;
    public SharedPreferences preferences;
    private Context context;

    //mqtt/ customcard
    private String card_title;
    private Custom_power_DBHelper custom_powerDB;
    private Custom_gradient_DBHelper custom_gradient_DB;
    private Activity activity = Mqtt.getActivity();

    public Sentiment_HistoryHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        tb_name = name;
        this.context = context;
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS " + name +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "score DOUBLE," +
                " date BIGINT);");
        Log.i("넘어", "ok");
        //getSentiment();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insert(Double score, long date) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("score", score);
        values.put("date", date);
        db.insert(tb_name, null, values);
        db.close();
        Log.i("넘어", String.valueOf(values.get("score")) + " / " + String.valueOf(values.get("date")));
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("score", values.get("score"));
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getSentiment();
    }

    //특정 id를 갖는 데이터를 json으로 출력
    public JSONArray getSentiment() {
        double total = 0;
        long now = System.currentTimeMillis();
        //user want to Time  내가 원하는 시간*3600*1000
        //2*3600*1000
//        long userTime = now - 3600000;
        long userTime = now - 360000;
        Log.i("야야양", String.valueOf(userTime));
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT score FROM " + tb_name + " WHERE date > ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userTime)});
        int bodyidx = cursor.getColumnIndex("score");
        totalCnt = cursor.getCount();
//        long ss = cursor.getColumnIndex("date");
        while (cursor.moveToNext()) {
            Double body = cursor.getDouble(bodyidx);
            Log.i("TTTT", String.valueOf(body));
//            Log.i("PPPPP", String.valueOf(ss) + " / " +String.valueOf(userTime) + " / " + String.valueOf(now));
        }
        JSONArray jsonArray = new JSONArray();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) ;
                {
                    try {
                        rowObject.put(cursor.getColumnName(i), cursor.getDouble(i));
                        total = total + cursor.getDouble(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            jsonArray.put(rowObject);
            cursor.moveToNext();
        }

        cursor.close();
        Log.i("나왔", jsonArray.toString());
        Log.i("토탈", String.valueOf(total));
        Log.i("토탈zkdnsxm", String.valueOf(totalCnt));
        avg = total / totalCnt;
        Log.i("토탈avg", String.valueOf(avg));

        connectHttpServer = new ConnectHttpServer();
        connectHttpServer.connect(avg);

//        Check_Sentiment_Thread check_sentiment_thread = new Check_Sentiment_Thread(context, avg);
//        Thread t = new Thread(check_sentiment_thread);
//        t.setDaemon(true);
//        t.start();

        return jsonArray;
    }


    public Double getAvg(){

        double total = 0;
        long now = System.currentTimeMillis();
        long userTime = now - 360000;
        Log.i("야야양", String.valueOf(userTime));
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT score FROM " + tb_name + " WHERE date > ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userTime)});
        int bodyidx = cursor.getColumnIndex("score");
        totalCnt = cursor.getCount();
        while (cursor.moveToNext()) {
            Double body = cursor.getDouble(bodyidx);
            Log.i("TTTT", String.valueOf(body));
        }
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            totalColumn = cursor.getColumnCount();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null){
                    total = total + cursor.getDouble(i);
                }
            }
            cursor.moveToNext();
        }

        cursor.close();
        Log.i("토탈", String.valueOf(total));
        Log.i("토탈zkdnsxm", String.valueOf(totalCnt));
        avg = total / totalCnt;
        Log.i("토탈avg", String.valueOf(avg));

        return avg;
    }


}
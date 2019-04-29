package com.example.aromind.Activity.MunuAlarm_AlarmManager;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.aromind.CustomView.CustomButton;
import com.example.aromind.Model.Custom_gradient_DBHelper;
import com.example.aromind.Model.Custom_power_DBHelper;
import com.example.aromind.Model.Mqtt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;

public class AlarmService extends Service {

    Context context = this;

    //향/무드라이트 가져오는 부분
    private Custom_power_DBHelper custom_powerDB;
    private Custom_gradient_DBHelper custom_gradient_DB;
    private Activity activity = Mqtt.getActivity();
    private ToggleButton btn_positive, btn_neutral, btn_negative;
    private ToggleButton colorPower;
    private CustomButton colorCheck;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String card_title = intent.getStringExtra("card_title");
        Log.i("후후후후후1", card_title);

        custom_powerDB = new Custom_power_DBHelper(context, "custom_power", null, 1);
        custom_gradient_DB = new Custom_gradient_DBHelper(context,"custom_color", null, 1);
        JSONObject power_object = (JSONObject)custom_powerDB.getData(card_title);
        JSONArray json_db_color = custom_gradient_DB.getData((card_title));
        Log.i("후후후후후2", power_object.toString());
        Log.i("후후후후후3", json_db_color.toString());
        Log.i("후후후후후4", card_title);
        Log.i("chk1", "서비스 도착");
        Mqtt.clientPub(activity, "totalPower_ON");
        try {
                if (Integer.parseInt((String) power_object.get("positive"))!=  0) {

                    Mqtt.clientPub(activity, "aroma1_" + (String) power_object.get("positive"));
                }else{
                 Log.i("NOMAN", "NOMAN");
                }
                if (Integer.parseInt((String) power_object.get("neutral")) != 0) {

                    Mqtt.clientPub(activity, "aroma2_" + (String) power_object.get("neutral"));
                }else{
                    Log.i("NOMAN", "NOMAN");
                }
                if (Integer.parseInt((String) power_object.get("negative")) != 0) {

                    Mqtt.clientPub(activity, "aroma3_" + (String) power_object.get("negative"));
                }else{
                    Log.i("NOMAN", "NOMAN");
                }
                int[] color_array = null;
                Mqtt.clientPub(activity, "colorPicker_0,0,0,1.0");
                String gradient_payload = null;
                if ((int)json_db_color.length() != 0) {
                    for (int j = 0; j < json_db_color.length(); j++) {
                        JSONObject color_object = (JSONObject) json_db_color.get(j);
                        int int_c= Integer.parseInt((String)color_object.get("color"));
                        int r = Color.red(int_c);
                        int g = Color.green(int_c);
                        int b = Color.blue(int_c);
                        if (j == 0) {
                            color_array = new int[json_db_color.length() + 1];
                            gradient_payload = "gradient_";
                            gradient_payload += r+"."+g+"."+b;
                        }else{
                            gradient_payload +=","+r+"."+g+"."+b;
                        }
                        color_array[j] = Integer.parseInt((String) color_object.get("color"));
                    }
                    JSONObject last = (JSONObject) json_db_color.get(0);
                    int r = Color.red(Integer.parseInt((String)last.get("color")));
                    int g = Color.green(Integer.parseInt((String)last.get("color")));
                    int b = Color.blue(Integer.parseInt((String)last.get("color")));
                    gradient_payload +=","+r+"."+g+"."+b;
                    color_array[json_db_color.length()] = Integer.parseInt((String) last.get("color"));

                    Mqtt.clientPub(activity,gradient_payload);
                    //mqtt color gradient 전송하기
                }else{
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "알람이 드딛어 성공하셨습니다", Toast.LENGTH_SHORT).show();



        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

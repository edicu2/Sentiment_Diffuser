package com.example.aromind.Activity.MenuAlarm_Activity;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;

import com.example.aromind.Model.Mqtt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Alarm_Thread implements Runnable{

    private JSONObject power_object;
    private JSONArray json_db_color;
    private int interval;
    private int repeat;
    private int sleepTime;
    private Activity activity = Mqtt.getActivity();

    public Alarm_Thread(JSONObject power_object, JSONArray json_db_color, int interval, int repeat){
        this.power_object = power_object;
        this.json_db_color = json_db_color;
        this.interval =interval;
        this.repeat =repeat;

        if (interval != 0){
            if (interval == 5){
                sleepTime = 1000*5;
            }else if(interval == 10){
                sleepTime = (1000*60)*10;
            }else if (interval == 15){
                sleepTime = (1000*60)*15;
            }else if (interval == 30){
                sleepTime = (1000*60)*30;
            }
        }
    }



    @Override
    public void run() {

        if (repeat == 0) {
            Log.i("인터벌과리핏true", String.valueOf(interval)+" / "+String.valueOf(repeat));
            startMqtt();

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            Mqtt.clientPub(activity, "totalPower_OFF");
        }else {
            Log.i("인터벌과리핏false1", String.valueOf(interval)+" / "+String.valueOf(repeat));
            for (int i=1; i<=repeat; i++){
                Log.i("인터벌과리핏false2", String.valueOf(interval)+" / "+String.valueOf(repeat));
                startMqtt();
                Log.i("인터벌과리핏false", String.valueOf(i)+"바퀴");
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
                Mqtt.clientPub(activity, "totalPower_OFF");

                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        }


    }

    private void startMqtt(){
        Log.i("인터벌과리핏startMqtt1", "매서드실행완료");
        //햫 피우기
        Mqtt.clientPub(activity, "totalPower_ON");
        try {
            if (Integer.parseInt((String) power_object.get("positive"))!=  0) {
                Log.i("인터벌과리핏startMqtt2", String.valueOf(power_object.get("positive")));
                Mqtt.clientPub(activity, "aroma1_" + (String) power_object.get("positive"));
            }else{
                Log.i("NOMAN", "NOMAN");
            }
            if (Integer.parseInt((String) power_object.get("neutral")) != 0) {

                Mqtt.clientPub(activity, "aroma2_" + (String) power_object.get("neutral"));
                Log.i("인터벌과리핏startMqtt2", String.valueOf(power_object.get("neutral")));
            }else{
                Log.i("NOMAN", "NOMAN");
            }
            if (Integer.parseInt((String) power_object.get("negative")) != 0) {

                Mqtt.clientPub(activity, "aroma3_" + (String) power_object.get("negative"));
                Log.i("인터벌과리핏startMqtt2", String.valueOf(power_object.get("negative")));
            }else{
                Log.i("NOMAN", "NOMAN");
            }

            //무드등 피우기
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
    }
}

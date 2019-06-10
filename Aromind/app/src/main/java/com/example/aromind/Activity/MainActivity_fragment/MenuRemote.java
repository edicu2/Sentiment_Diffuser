package com.example.aromind.Activity.MainActivity_fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.example.aromind.Activity.MainActivity;
import com.example.aromind.Activity.MenuRemote_RecyclerView.ListDecoration;
import com.example.aromind.Activity.MenuRemote_RecyclerView.RecyclerViewAdapter;
import com.example.aromind.CustomView.CustomButton;
import com.example.aromind.Model.Custom_gradient_DBHelper;
import com.example.aromind.Model.Custom_power_DBHelper;
import com.example.aromind.Model.Http_getCustomCard;
import com.example.aromind.Model.Mqtt;
import com.example.aromind.R;
import com.github.mikephil.charting.data.PieEntry;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;



public class MenuRemote extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener, View.OnTouchListener {
    View view;

    //color Pick
    private int r=255,g=255,b=255,bright=0;
    private ImageView color;
    private ToggleButton colorPower;
    private Bitmap bitmap;
    private SeekBar brightness;
    private CustomButton colorCheck;
    private ColorMatrix matrix;
    private ColorMatrixColorFilter filter;

    // aroma power
    private ToggleButton aromaBtn1, aromaBtn2, aromaBtn3;

    //total Power
    private ToggleButton totalPower;

    //Custom
    private static RecyclerView listview;
    private static RecyclerViewAdapter adapter;

    // Mqtt String
    private String payload;

    // DB
    private Custom_power_DBHelper custom_powerDB;
    private Custom_gradient_DBHelper custom_gradient_DB;
    private ArrayList<int[]> itemList2 ;
    private ArrayList<ArrayList> itemList ;
    private ArrayList<String> title ;
    private static SharedPreferences pref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menu_remote, container, false);


        // Total Power
        totalPower = (ToggleButton)view.findViewById(R.id.totalPower);
        totalPower.setOnCheckedChangeListener(this);

        // Color Pick Part - color, color_power, brightness
        colorPower = (ToggleButton)view.findViewById(R.id.colorPower);
        colorPower.setOnCheckedChangeListener(this);
        color = (ImageView)view.findViewById(R.id.color);
        color.setDrawingCacheEnabled(true);
        color.buildDrawingCache(true);
        color.setOnTouchListener(this);
        brightness = (SeekBar)view.findViewById(R.id.brightness);
        brightness.setOnSeekBarChangeListener(this);
        colorCheck = (CustomButton)view.findViewById(R.id.color_check);
        colorCheck.setCircleColor(Color.argb(bright,r, g, b));
        colorCheck.setOnClickListener(this);

        // Aroma power Btn
        aromaBtn1 = (ToggleButton)view.findViewById(R.id.aroma1);
        aromaBtn1.setOnCheckedChangeListener(this);
        aromaBtn2 = (ToggleButton)view.findViewById(R.id.aroma2);
        aromaBtn2.setOnCheckedChangeListener(this);
        aromaBtn3 = (ToggleButton)view.findViewById(R.id.aroma3);
        aromaBtn3.setOnCheckedChangeListener(this);

        // first set
        matrix = new ColorMatrix();
        matrix.setSaturation(0);
        filter = new ColorMatrixColorFilter(matrix);
        color.setColorFilter(filter);

        // DB
        custom_powerDB = new Custom_power_DBHelper(getContext(), "custom_power", null, 1);
        custom_gradient_DB = new Custom_gradient_DBHelper(getContext(),"custom_color", null, 1);
        pref = getContext().getSharedPreferences("Sentiment", 0);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
    public void onResume() {
        super.onResume();

        if(CustomButton.tempcircleCols != null) {
            colorCheck.setCircleColors(CustomButton.tempcircleCols);
            String p = "gradient_";
            for(int i=0 ; i<CustomButton.tempcircleCols.length-1 ; i++){
                int r = Color.red(CustomButton.tempcircleCols[i]);
                int g = Color.green(CustomButton.tempcircleCols[i]);
                int b = Color.blue(CustomButton.tempcircleCols[i]);
                if( i == 0 ){
                    p +=r+"."+g+"."+b;
                }else {
                    p +=","+r+"."+g+"."+b;
                }
            }
            Mqtt.clientPub(getActivity(),p);


        }


        listview = (RecyclerView)view.findViewById(R.id.main_listview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        listview.setLayoutManager(layoutManager);
        itemList = new ArrayList<ArrayList>();
        itemList2 = new ArrayList<>();
        title = new ArrayList<>();
//
//        ArrayList<PieEntry> pie;
//        int custom_power_DB_size = (int)custom_powerDB.getDataSize();
//        JSONArray json_db = custom_powerDB.getRecentData(custom_power_DB_size);
//
//        int[] colors = null;
//
//        for(int i=0 ; i < custom_power_DB_size  ; i++){
//            try {
//                JSONObject power_object = (JSONObject) json_db.get(i);
//                pie = new ArrayList<PieEntry>();
//                pie.add(new PieEntry(Integer.parseInt((String) power_object.get("positive")),"aroma1"));
//                pie.add(new PieEntry(Integer.parseInt((String) power_object.get("neutral")),"aroma2"));
//                pie.add(new PieEntry(Integer.parseInt((String) power_object.get("negative")),"aroma3"));
//                itemList.add(pie);
//                title.add((String) power_object.get("custom_name"));
//                JSONArray json_db_color = custom_gradient_DB.getData((String) power_object.get("custom_name"));
//                Log.i("custom_color_size", String.valueOf(json_db_color.length()));
//                if(json_db_color.length() != 0) {
//                    for (int j = 0; j < json_db_color.length(); j++) {
//                        JSONObject color_object = (JSONObject)json_db_color.get(j);
//                        Log.i("colors", (String) color_object.get("color"));
//                        if (j == 0) {
//                            colors = new int[json_db_color.length()+1];
//                        }
//                        colors[j] =Integer.parseInt((String)color_object.get("color"));
//                        Log.i("color_teki", String.valueOf(colors[j]));
//                    }
//                    JSONObject last = (JSONObject)json_db_color.get(0);
//                    colors[json_db_color.length()] = Integer.parseInt((String)last.get("color"));
//                    itemList2.add(colors);
//                }else{
//                    itemList2.add(new int[]{Color.WHITE,Color.BLACK,Color.WHITE});
//                }
//                Log.i("colors", String.valueOf(itemList2));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        pie = new ArrayList<PieEntry>();
//        itemList.add(pie);
//        itemList2.add(new int[]{Color.WHITE, R.color.aroma1, R.color.aroma2, R.color.aroma3, Color.WHITE});
//        title.add("New Custom Add");
//
//
//        adapter = new RecyclerViewAdapter(getContext(),getActivity(), aromaBtn1 ,aromaBtn2,aromaBtn3,color, brightness, colorPower, colorCheck,totalPower,itemList, itemList2, title);
//        listview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listToggleSetFalse();
//            }
//        });
//        listview.setAdapter(adapter);
//        listview.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                listToggleSetFalse();
//            }
//        });
        showListView();
        mqttSubCallback();
    }
    public static void listToggleSetFalse(){
        Log.i("count", String.valueOf(listview.getChildCount()));
        for(int i=0; i<listview.getChildCount() ; i++){
            ToggleButton tg = listview.getChildAt(i).findViewById(R.id.focus);
            TextView ct = listview.getChildAt(i).findViewById(R.id.custom_title);
            Log.i("remote",pref.getString("remote_set",""));
            if(pref.getString("remote_set","").equals(ct.getText())) {
                Log.i("remot", "ok");
                tg.setChecked(true);
            }else{
                Log.i("remot", "no");
                tg.setChecked(false);
            }

        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(totalPower.isChecked() == false){
            setAllStop();
        }else{
            if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
                setColorOn();
                colorCheck.tempcircleCols=null;
                colorPower.setChecked(true);
                bitmap = color.getDrawingCache();
                int px = bitmap.getPixel((int)event.getX(),(int)event.getY());
                if (!(Color.red(px) == 0 && Color.green(px) == 0 && Color.blue(px) == 0)) {
                    r = Color.red(px);
                    g = Color.green(px);
                    b = Color.blue(px);
                    bright = brightness.getProgress();
                    colorPower.setChecked(true);
                    if(brightness.getProgress() == 0)
                        brightness.setProgress(20);
                    colorCheck.setCircleColor(Color.argb(bright,r, g, b));
                    payload = "colorPicker_"+r+","+g+","+b+","+(float)bright/255;

                    Log.i("mqtt",payload);
                    Mqtt.clientPub(getActivity(), payload);
                }
            }
        }
        return false;
    }


    @Override
    public void onCheckedChanged(CompoundButton v, boolean isChecked) {
        Log.i("mqtt",getView().getResources().getResourceEntryName(v.getId()));
        // total 버튼이 눌렸는지 아닌지 확인하고 ON, OFF인지 확인
        // total 버튼이 아닐 때 ->
        if(v.getId() == R.id.totalPower){
            if(isChecked == true){
                aromaBtn1.setClickable(true);
                aromaBtn2.setClickable(true);
                aromaBtn3.setClickable(true);
                colorPower.setClickable(true);
                payload = v.getResources().getResourceEntryName(v.getId()) + "_ON";
            }else{
                aromaBtn1.setChecked(false);
                aromaBtn2.setChecked(false);
                aromaBtn3.setChecked(false);
                colorPower.setChecked(false);
                aromaBtn1.setClickable(false);
                aromaBtn2.setClickable(false);
                aromaBtn3.setClickable(false);
                colorPower.setClickable(false);
                setColorOff();
                CustomButton.tempcircleCols=null;
                brightness.setProgress(0);
                payload = v.getResources().getResourceEntryName(v.getId())+"_OFF";
            }
        }else if (v.getId() == R.id.colorPower) {
            if (isChecked == true) {
                setColorOn();
                brightness.setProgress(125);
            } else {
                setColorOff();
                brightness.setProgress(0);
            }

        }else{
            if (isChecked == true) {
                payload = v.getResources().getResourceEntryName(v.getId())+"_100";
            } else {
                payload = v.getResources().getResourceEntryName(v.getId())+"_0";
            }
        }
        Log.i("mqtt", payload);
        Mqtt.clientPub(getActivity(), payload);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(totalPower.isChecked() == false){
            setAllStop();
        }else {
            if (progress == 0) {
                colorPower.setChecked(false);
                setColorOff();
            }
            else {
                colorPower.setChecked(true);
                setColorOn();
            }
            bright = progress;
            payload = "colorPicker_"+r+","+g+","+b+","+(float)bright/255;
            colorCheck.setCircleColor(Color.argb(bright,r, g, b));
            Log.i("mqtt",payload);
            Mqtt.clientPub(getActivity(), payload);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        bright = seekBar.getProgress();
        payload = "colorPicker_"+r+","+g+","+b+","+(float)bright/255;
        colorCheck.setCircleColor(Color.argb(bright,r, g, b));
        Log.i("mqtt",payload);
        Mqtt.clientPub(getActivity(), payload);
    }

    public void setAllStop(){
        aromaBtn1.setChecked(false);
        aromaBtn2.setChecked(false);
        aromaBtn3.setChecked(false);
        colorPower.setChecked(false);
        brightness.setProgress(0);
        setColorOff();
    }

    public void setColorOn(){
        matrix.setSaturation(1);
        filter = new ColorMatrixColorFilter(matrix);
        color.setColorFilter(filter);
        colorCheck.circleCols=null;
    }

    public void setColorOff(){
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        filter = new ColorMatrixColorFilter(matrix);
        color.setColorFilter(filter);
        colorCheck.circleCols = null;
        colorCheck.setCircleColor(Color.argb(0,0, 0, 0));
    }

    public void mqttSubCallback(){
        
        Mqtt.client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String msg = new String(message.getPayload());
                Log.i("메세지바꿔봐", msg);
                Log.i("메세지바꿔봐", msg.substring(0,1));
                if (msg.substring(0,1).equals("%")){
                    Log.i("메세지바꿔봐", msg.substring(1));
                    int id = Integer.parseInt(msg.substring(1));
                    Log.i("메세지바꿔봐", String.valueOf(id));
//                    Http_getCustomCard http_getCustomCard = new Http_getCustomCard(id, getContext(), adapter);

                    try {
                        final URL url = new URL(com.example.aromind.Data.URL.GETCUSTOMCARD_SERVER+"/"+String.valueOf(id));

                        new AsyncTask<URL, Void, String>() {
                            protected void onPreExecute() {                 //작업 실행전 인터페이스 진행률 표시줄을 표시하는데 사용
                                super.onPreExecute();
                            }
                            @Override
                            protected String doInBackground(URL... urls) {
                                String result = new String();
                                if (urls == null || urls.length<1){
                                    Log.i("cunn", "No Http URLS");
                                    return null;
                                }
                                try {
                                    HttpURLConnection connection = (HttpURLConnection) urls[0].openConnection();
                                    Log.i("cunn2", urls[0].toString());

                                    connection.setRequestMethod("GET");//post방식
//                        connection.setDoOutput(true);//쓰기모드 POST강제실행
                                    connection.setDoInput(true);//읽기모드
                                    connection.setUseCaches(false);
                                    connection.setDefaultUseCaches(false);
                                    Log.i("cunn2", "Complite Connection");

//                        OutputStream os = connection.getOutputStream();
//                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//                        writer.write("id="+String.valueOf(id));
//                        writer.flush();

                                    Log.i("cunn29", "Complite Connection");
                                    //읽기모드
                                    InputStream is = connection.getInputStream();
                                    StringBuilder builder = new StringBuilder();
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                                    String line;
                                    while ((line = reader.readLine()) != null) {
                                        builder.append(line + "\n");
                                    }
                                    result = builder.toString();
                                    Log.i("cunn3", result);
                                }catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return result;
                            }
                            @Override
                            protected void onPostExecute(String s) {
                                super.onPostExecute(s);
                                Log.i("카드 받아왔다", s);
                                String name, strColor;
                                int positive, neutral, negative, bright;
                                String[] color;
                                try {
                                    JSONArray jsonArray = new JSONArray(s);
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    Log.i("카드받아왔지롱", jsonObject.toString());
                                    name = jsonObject.optString("customcard_name");
                                    Log.i("카드받아왔지롱2", name);
                                    positive = jsonObject.optInt("positive_strength");
                                    Log.i("카드받아왔지롱3", String.valueOf(positive));
                                    neutral = jsonObject.optInt("normal_strength");
                                    Log.i("카드받아왔지롱3", String.valueOf(neutral));
                                    negative = jsonObject.optInt("nagative_strength");
                                    Log.i("카드받아왔지롱3", String.valueOf(positive));
                                    bright = Integer.parseInt(jsonObject.optString("bright"));
                                    Log.i("카드받아왔디롱4", String.valueOf(bright));
                                    strColor = jsonObject.optString("rgb");
                                    color = strColor.split(",");
                                    custom_powerDB.insert(name, positive, neutral, negative, bright);
                                    for (String rgb : color){
                                        Log.i("카드받아왔디롱5", rgb);
                                        //특수문자를 구별하기위해 \\.
                                        String[] colors = rgb.split("\\.");
                                        Log.i("카드받아왔지롱6", colors[0]);
                                        Log.i("카드받아왔지롱6", colors[1]);
                                        Log.i("카드받아왔지롱6", colors[2]);

                                        int rgbs = android.graphics.Color.rgb(Integer.parseInt(colors[0]),Integer.parseInt(colors[1]),Integer.parseInt(colors[2]));
                                        Log.i("색갈이 나오는중", String.valueOf(rgbs));
                                        custom_gradient_DB.insert(name, rgbs);

                                        itemList.clear();
                                        itemList2.clear();
                                        title.clear();
                                        showListView();

                                        adapter.notifyDataSetChanged();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.execute(url);
                    }catch (MalformedURLException e){
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });
    }

    private void showListView(){

        ArrayList<PieEntry> pie;
        int custom_power_DB_size = (int)custom_powerDB.getDataSize();
        JSONArray json_db = custom_powerDB.getRecentData(custom_power_DB_size);

        int[] colors = null;

        for(int i=0 ; i < custom_power_DB_size  ; i++){
            try {
                JSONObject power_object = (JSONObject) json_db.get(i);
                pie = new ArrayList<PieEntry>();
                pie.add(new PieEntry(Integer.parseInt((String) power_object.get("positive")),"aroma1"));
                pie.add(new PieEntry(Integer.parseInt((String) power_object.get("neutral")),"aroma2"));
                pie.add(new PieEntry(Integer.parseInt((String) power_object.get("negative")),"aroma3"));
                itemList.add(pie);
                title.add((String) power_object.get("custom_name"));
                JSONArray json_db_color = custom_gradient_DB.getData((String) power_object.get("custom_name"));
                Log.i("custom_color_size", String.valueOf(json_db_color.length()));
                if(json_db_color.length() != 0) {
                    for (int j = 0; j < json_db_color.length(); j++) {
                        JSONObject color_object = (JSONObject)json_db_color.get(j);
                        Log.i("colors", (String) color_object.get("color"));
                        if (j == 0) {
                            colors = new int[json_db_color.length()+1];
                        }
                        colors[j] =Integer.parseInt((String)color_object.get("color"));
                        Log.i("color_teki", String.valueOf(colors[j]));
                    }
                    JSONObject last = (JSONObject)json_db_color.get(0);
                    colors[json_db_color.length()] = Integer.parseInt((String)last.get("color"));
                    itemList2.add(colors);
                }else{
                    itemList2.add(new int[]{Color.WHITE,Color.BLACK,Color.WHITE});
                }
                Log.i("colors", String.valueOf(itemList2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        pie = new ArrayList<PieEntry>();
        itemList.add(pie);
        itemList2.add(new int[]{Color.WHITE, R.color.aroma1, R.color.aroma2, R.color.aroma3, Color.WHITE});
        title.add("New Custom Add");


        adapter = new RecyclerViewAdapter(getContext(),getActivity(), aromaBtn1 ,aromaBtn2,aromaBtn3,color, brightness, colorPower, colorCheck,totalPower,itemList, itemList2, title);
        listview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listToggleSetFalse();
            }
        });
        listview.setAdapter(adapter);
        listview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                listToggleSetFalse();
            }
        });
    }

}
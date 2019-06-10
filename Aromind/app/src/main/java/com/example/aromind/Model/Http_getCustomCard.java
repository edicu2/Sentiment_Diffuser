package com.example.aromind.Model;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.aromind.Activity.CustomAddActivity;
import com.example.aromind.Activity.MenuRemote_RecyclerView.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Http_getCustomCard {

    Custom_gradient_DBHelper custom_gradient_dbHelper;
    Custom_power_DBHelper custom_power_dbHelper;
    public Http_getCustomCard(final int id, Context context, final RecyclerViewAdapter adapter) {
        custom_power_dbHelper = new Custom_power_DBHelper(context, "custom_power", null, 1);
        custom_gradient_dbHelper = new Custom_gradient_DBHelper(context,"custom_color", null, 1);

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
                        custom_power_dbHelper.insert(name, positive, neutral, negative, bright);
                        for (String rgb : color){
                            Log.i("카드받아왔디롱5", rgb);
                            //특수문자를 구별하기위해 \\.
                            String[] colors = rgb.split("\\.");
                            Log.i("카드받아왔지롱6", colors[0]);
                            Log.i("카드받아왔지롱6", colors[1]);
                            Log.i("카드받아왔지롱6", colors[2]);

                            int rgbs = android.graphics.Color.rgb(Integer.parseInt(colors[0]),Integer.parseInt(colors[1]),Integer.parseInt(colors[2]));
                            Log.i("색갈이 나오는중", String.valueOf(rgbs));
                            custom_gradient_dbHelper.insert(name, rgbs);
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
//    public abstract void temp();
}

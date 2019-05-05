package com.example.aromind.Sentiment;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.internal.http.HttpConnection;

import org.json.JSONArray;
import org.json.JSONException;

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

public class ConnectHttpServer {



    public void connect(final double jsonArray){
        try {
            URL url = new URL("http://arominds.com:8000/emotionvalue");
            new AsyncTask<URL, Void, String>() {
                @Override
                protected String doInBackground(URL... urls) {
                    String result = new String();
                    if (urls == null || urls.length<1){
                        Log.i("cunn", "No Http URLS");
                        return null;
                    }
                    try {
                        HttpURLConnection connection = (HttpURLConnection) urls[0].openConnection();
                        Log.i("cunn2", "Complite Connection");
//                        connection.setRequestMethod("GET");//getㅂ방식

                        connection.setRequestMethod("POST");//post방식
                        connection.setDoOutput(true);//쓰기모드 POST강제실행
                        connection.setDoInput(true);//읽기모드
                        connection.setUseCaches(false);
                        connection.setDefaultUseCaches(false);

                        OutputStream os = connection.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        writer.write("value="+String.valueOf(jsonArray));
                        writer.flush();
                        writer.close();
                        Log.i("cunn3", String.valueOf(jsonArray));
                        //읽기모드
                        InputStream is = connection.getInputStream();
                        StringBuilder builder = new StringBuilder();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line + "\n");
                        }
                        result = builder.toString();
                        Log.i("cunn4", result);
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
//                    try {
//                        JSONArray jsonArray = new JSONArray(s);
//                        Log.i("cunn4", "ConnectHttpServerSendOk");
//                        Log.i("cunn4", jsonArray.toString());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                    Log.i("cunn5", s);
                }
            }.execute(url);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
    }
}

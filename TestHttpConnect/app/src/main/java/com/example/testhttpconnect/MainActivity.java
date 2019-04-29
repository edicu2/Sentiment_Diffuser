package com.example.testhttpconnect;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.e);

        String example = "[{\"score\":0.5}]";
        Double d = 0.3654;
        connect(d);
    }

    public void connect(final Double jsonArray){

        Log.i("cunn2", jsonArray.toString());
        try {
            final URL url = new URL("http://ec2-54-180-103-228.ap-northeast-2.compute.amazonaws.com:8000/emotionvalue");
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
                        Log.i("cunn2", jsonArray.toString());
                        HttpURLConnection connection = (HttpURLConnection) urls[0].openConnection();
                        Log.i("cunn2", urls[0].toString());
                        Log.i("cunn2", "Complite Connection");
//                        connection.setRequestMethod("GET");//getㅂ방식

                        connection.setRequestMethod("POST");//post방식
                        connection.setDoOutput(true);//쓰기모드 POST강제실행
                        connection.setDoInput(true);//읽기모드
                        connection.setUseCaches(false);
                        connection.setDefaultUseCaches(false);
                        Log.i("cunn2", "Complite Connection");

                        OutputStream os = connection.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        writer.write("value="+String.valueOf(jsonArray));
                        writer.flush();

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
                    textView.setText(s);
                }
            }.execute(url);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
    }
}

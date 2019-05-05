package com.example.aromind.Model;

import android.os.AsyncTask;
import android.util.Log;

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

public class Http {
    public void connect(final String name,final String positive,final String neutral,final String negative,final String colors,final String bright){

        try {
            final URL url = new URL(com.example.aromind.Data.URL.SERVERURL);
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

                        connection.setRequestMethod("POST");//post방식
                        connection.setDoOutput(true);//쓰기모드 POST강제실행
                        connection.setDoInput(true);//읽기모드
                        connection.setUseCaches(false);
                        connection.setDefaultUseCaches(false);
                        Log.i("cunn2", "Complite Connection");
                        Log.i("cunn2naew", name);
                        OutputStream os = connection.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        writer.write("name="+name);
                        if(positive != null) {
                            writer.write("&positive=" + positive);
                            writer.write("&neutral=" + neutral);
                            writer.write("&negative=" + negative);
                            writer.write("&colors=" + colors);
                            writer.write("&bright=" + bright);
                        }
                        writer.flush();

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
                }
            }.execute(url);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
    }
}

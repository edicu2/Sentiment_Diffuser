package com.example.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class AsyntTaskEx extends AsyncTask<String,Void,String>{

    public String result;
    public String s;
    public AsyntTaskEx(){

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        OutputStream out;
        try {
            File file = new File(String.valueOf(s));
            URL url = new URL("http://carmarts.shop");
            URLConnection httpConn = url.openConnection();
            httpConn.setDoOutput(true);
            httpConn.setUseCaches(false);
            httpConn.setRequestProperty("Content-type", "application/octet-stream");
            httpConn.setRequestProperty("Content-Length", String.valueOf(file.length()));

            out = httpConn.getOutputStream();

            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int readcount = 0;
            while ((readcount = fis.read(buffer)) != -1) {
                out.write(buffer, 0, readcount);
            }
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("Select",result);
        return result;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}

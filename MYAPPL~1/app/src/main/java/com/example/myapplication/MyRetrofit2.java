package com.example.myapplication;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit2 {

    public static final String URL = "http://49.143.18.93:8000/";

    static Retrofit mRetrofit;

    public static Retrofit getRetrofit2(){

        if(mRetrofit == null){

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(3000, TimeUnit.SECONDS)
                    .readTimeout(3000, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .build();


            mRetrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return mRetrofit;
    }
}

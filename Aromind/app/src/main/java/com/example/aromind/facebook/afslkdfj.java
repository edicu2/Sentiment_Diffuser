package com.example.aromind.facebook;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import com.example.aromind.Activity.MainActivity;
import com.example.aromind.Sentiment.SentimentServicr;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class afslkdfj extends MainActivity {
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;

    public String message="";



    public afslkdfj(Context context){
        fb(context);
    }

    public void fb(final Context context){

        callbackManager = CallbackManager.Factory.create();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        //facebook Token
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        //게시글 뽑아 오기
        final GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken,
                "/me/posts",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        // Insert your code here
                        String result = null;
                        Log.i("BBBm", response.toString());
                        JSONObject jsonObject = response.getJSONObject();
                        if (jsonObject == null){
                            Log.i("BBBm", "bad");
                            return;
                        }
                        Log.i("BBBm1", jsonObject.toString());
                        String data = jsonObject.optString("data");
                        Log.i("BBBm2", data);
                        try {
                            JSONArray jsonArray = new JSONArray(data);
                            //게시글에서 첫번째 게시물만 가져오는것
                            JSONObject j = jsonArray.getJSONObject(0);
                            message = j.optString("message");
                            String time = j.optString("created_time");

                            Intent intent = new Intent(context, SentimentServicr.class);
                            intent.putExtra("message", message);
                            context.startService(intent);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "message,created_time");
        request.setParameters(parameters);
        request.executeAsync();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}

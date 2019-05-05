package com.example.aromind.facebook;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.aromind.R;
import com.example.aromind.Sentiment.SentimentServicr;
import com.example.aromind.Sentiment.Sentiment_HistoryHelper;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class Facebook extends AppCompatActivity implements View.OnClickListener{

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private ArrayList arrayList_message = new ArrayList();
    private ArrayList arrayList_updateTime_message = new ArrayList();
    private String message;
    private Button submit, cancle;

    int action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS);
//        AppEventsLogger.activateApp(this);

        //facebook login
//        loginButton = (LoginButton) findViewById(R.id.login_button);
//
//        loginButton.setReadPermissions("email");
//        loginButton.setReadPermissions(Arrays.asList("user_status"));
//        loginButton.setReadPermissions("user_posts");

        callbackManager = CallbackManager.Factory.create();

        // Callback registration
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                // App code
//            }
//
//            @Override
//            public void onCancel() {
//                // App code
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                // App code
//            }
//        });

//        //실제 로긍니 되면 이쪽에서 아림림
//        LoginManager.getInstance().registerCallback(callbackManager,
//                new FacebookCallback<LoginResult>() {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//                        // App code
//                        if (loginResult == null){
//                            Log.i("AAA", "bad");
//                            return;
//                        }else {
//                            Log.i("AAA", loginResult.toString());
//                        }
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        // App code
//                    }
//
//                    @Override
//                    public void onError(FacebookException exception) {
//                        // App code
//                    }
//                });

        //토큰가져오기
//        accessTokenTracker = new AccessTokenTracker() {
//            @Override
//            protected void onCurrentAccessTokenChanged(
//                    AccessToken oldAccessToken,
//                    AccessToken currentAccessToken) {
//                // Set the access token using
//                // currentAccessToken when it's loaded or set.
//            }
//        };
//
//        //ㅍ,로필 추적ㄷ
//        profileTracker = new ProfileTracker() {
//            @Override
//            protected void onCurrentProfileChanged(
//                    Profile oldProfile,
//                    Profile currentProfile) {
//                // App code
//
//            }
//        };

       // fb();

//        GraphRequest requests = GraphRequest.newGraphPathRequest(
//                accessToken,
//                "/me/",
//                new GraphRequest.Callback() {
//                    @Override
//                    public void onCompleted(GraphResponse response) {
//                        // Insert your code here
//                        JSONObject jsonObject = response.getJSONObject();
//                        Log.i("AAAp", jsonObject.toString());
//                        String albums = jsonObject.optString("albums");
//                        try {
//                            JSONObject jsonObject2 = new JSONObject(albums);
//                            Log.i("AAAp1", jsonObject2.toString());
//                            String data = jsonObject2.optString("data");
//                            JSONArray jsonArray = new JSONArray(data);
//                            Log.i("AAAp2", data);
//                            JSONObject jsonObject3 = jsonArray.getJSONObject(1);
//                            String photos = jsonObject3.optString("photos");
//                            Log.i("AAAp3", photos);
//                            JSONObject jsonObject4 = new JSONObject(photos);
//                            String data2 = jsonObject4.optString("data");
//                            Log.i("AAAp4", data2);
//                            JSONArray jsonArray2 = new JSONArray(data2);
//
//                            for (int i=0; i<jsonArray2.length(); i++){
//                                JSONObject jsonObject5 = jsonArray2.getJSONObject(i);
//                                String images = jsonObject5.optString("images");
//                                Log.i("AAAp5", images);
//                                String update_time = jsonObject5.optString("updated_time");
//                                JSONArray jsonArray3 = new JSONArray(images);
//                                JSONObject jsonObject6 = jsonArray3.getJSONObject(0);
//                                String source = jsonObject6.optString("source");
////                                Log.i("AAAp6", source);
//
//                                arrayList_image.add(source);
//                                arrayList_updateTime_image.add(update_time);
//
//                            }
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//
//        Bundle parameterss = new Bundle();
//        parameterss.putString("fields", "albums{photos{images,updated_time}}");
//        requests.setParameters(parameterss);
//        requests.executeAsync();d

        submit = findViewById(R.id.submit);
        cancle = findViewById(R.id.cancle);
        submit.setOnClickListener(this);
        cancle.setOnClickListener(this);
    }

//    public void fb(){
//
//        callbackManager = CallbackManager.Factory.create();
//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        //facebook Token
//        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
//
//        //게시글 뽑아 오기
//        final GraphRequest request = GraphRequest.newGraphPathRequest(
//                accessToken,
//                "/me/posts",
//                new GraphRequest.Callback() {
//                    @Override
//                    public void onCompleted(GraphResponse response) {
//                        // Insert your code here
//
//                        Log.i("AAAm", response.toString());
//                        JSONObject jsonObject = response.getJSONObject();
//                        if (jsonObject == null){
//                            Log.i("AAAm", "bad");
//                            return;
//                        }
//                        Log.i("AAAm1", jsonObject.toString());
//                        String data = jsonObject.optString("data");
//                        Log.i("AAAm2", data);
//                        try {
//                            JSONArray jsonArray = new JSONArray(data);
//                            JSONObject j = jsonArray.getJSONObject(0);
//                            message = j.optString("message");
//                            String time = j.optString("created_time");
//                            Log.i("AAAMESSAGE", message);
//                            Log.i("AAACREATETIME", time);
//                            arrayList_message.add(message);
//                            arrayList_updateTime_message.add(time);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "message,created_time");
//        request.setParameters(parameters);
//        request.executeAsync();
//
//        FacebookBoradcast facebookBoradcast = new FacebookBoradcast();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("com.example.aromind.SEND_FACEBOOK");
//        registerReceiver(facebookBoradcast, intentFilter);
//
//        Intent data = new Intent(this, FacebookBoradcast.class);
//        data.putExtra("message", message);
//        sendBroadcast(data);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        profileTracker.stopTracking();
    }

    private Sentiment_HistoryHelper sentiment_historyHelper;
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit){
            finish();
        }else if (v.getId() == R.id.cancle){
            finish();
        }
    }

}

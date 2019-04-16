package com.example.facebookapi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private TextView textView;
    private ArrayList arrayList_image = new ArrayList();
    private ArrayList arrayList_updateTime_image = new ArrayList();
    private ArrayList arrayList_message = new ArrayList();
    private ArrayList arrayList_updateTime_message = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        FacebookSdk.addLoggingBehavior(LoggingBehavior.REQUESTS);
        AppEventsLogger.activateApp(this);

        textView = findViewById(R.id.textView);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        loginButton.setReadPermissions(Arrays.asList("user_status"));
        loginButton.setReadPermissions("user_posts");
//        // If using in a fragment
//        loginButton.setFragment(this);

        callbackManager = CallbackManager.Factory.create();

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code

                Log.i("AAA", String.valueOf(loginResult));

//                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile"));
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

//        LoginManager.getInstance().logInWithPublishPermissions(this, Arrays.asList("user_birthday"));

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.i("AAA", String.valueOf(loginResult));
//                        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//                        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
//        LoginManager.getInstance().logInWithPublishPermissions(MainActivity.this, Arrays.asList("publish_actions"));


        //토큰가져오기
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        };

        //ㅍ,로필 추적ㄷ
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                // App code

            }
        };
        // If the access token is available already assign it.
        accessToken = AccessToken.getCurrentAccessToken();

        //게시글 뽑아 오기
        final GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken,
                "/me/posts",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        // Insert your code here
                        Log.i("AAAm", response.toString());

                        JSONObject jsonObject = response.getJSONObject();
                        Log.i("AAAm1", jsonObject.toString());
                        String data = jsonObject.optString("data");
                        Log.i("AAAm2", data);
                        try {
                            JSONArray jsonArray = new JSONArray(data);
                            for (int i = 0; i<jsonArray.length(); i++){
                                JSONObject j = jsonArray.getJSONObject(i);
                                String message = j.optString("message");
                                String time = j.optString("created_time");
                                Log.i("AAAMESSAGE", message);
                                Log.i("AAACREATETIME", time);
                                arrayList_message.add(message);
                                arrayList_updateTime_message.add(time);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "message,created_time");
        request.setParameters(parameters);
        request.executeAsync();

        //사진 가져오기
        GraphRequest requests = GraphRequest.newGraphPathRequest(
                accessToken,
                "/me/",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        // Insert your code here
                        JSONObject jsonObject = response.getJSONObject();
                        Log.i("AAAp", jsonObject.toString());
                        String albums = jsonObject.optString("albums");
                        try {
                            JSONObject jsonObject2 = new JSONObject(albums);
                            Log.i("AAAp1", jsonObject2.toString());
                            String data = jsonObject2.optString("data");
                            JSONArray jsonArray = new JSONArray(data);
                            Log.i("AAAp2", data);
                            JSONObject jsonObject3 = jsonArray.getJSONObject(1);
                            String photos = jsonObject3.optString("photos");
                            Log.i("AAAp3", photos);
                            JSONObject jsonObject4 = new JSONObject(photos);
                            String data2 = jsonObject4.optString("data");
                            Log.i("AAAp4", data2);
                            JSONArray jsonArray2 = new JSONArray(data2);

                            for (int i=0; i<jsonArray2.length(); i++){
                                JSONObject jsonObject5 = jsonArray2.getJSONObject(i);
                                String images = jsonObject5.optString("images");
                                Log.i("AAAp5", images);
                                String update_time = jsonObject5.optString("updated_time");
                                JSONArray jsonArray3 = new JSONArray(images);
                                JSONObject jsonObject6 = jsonArray3.getJSONObject(0);
                                String source = jsonObject6.optString("source");
//                                Log.i("AAAp6", source);

                                arrayList_image.add(source);
                                arrayList_updateTime_image.add(update_time);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        Bundle parameterss = new Bundle();
        parameterss.putString("fields", "albums{photos{images,updated_time}}");
        requests.setParameters(parameterss);
        requests.executeAsync();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }
}

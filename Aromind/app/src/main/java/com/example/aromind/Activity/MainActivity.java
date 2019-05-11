package com.example.aromind.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.aromind.Activity.MainActivity_fragment.BottomNavigationHelper;
import com.example.aromind.Activity.MainActivity_fragment.MenuAlarm;
import com.example.aromind.Activity.MainActivity_fragment.MenuEmotion;
import com.example.aromind.Activity.MainActivity_fragment.MenuMypage;
import com.example.aromind.Activity.MainActivity_fragment.MenuRemote;
import com.example.aromind.Activity.MainActivity_fragment.MenuSetting;
import com.example.aromind.Face.MyJobService;
import com.example.aromind.Face.MyRetrofit2;
import com.example.aromind.Face.UploadService;
import com.example.aromind.Model.Mqtt;
import com.example.aromind.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ProfileTracker;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView title;
    private ImageView img;
    private final int REQUEST_PERMISSON_CODE = 1000;
    public static final int MY_BACKGROUND_JOB = 0;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private MenuMypage p_mypage = new MenuMypage();
    private MenuRemote p_remote = new MenuRemote();
    private MenuAlarm p_alarm = new MenuAlarm();
    private MenuEmotion p_emotion = new MenuEmotion();
    private MenuSetting p_setting = new MenuSetting();

    //facebook
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;

    private String message;

    public Context mContext;

    @androidx.annotation.RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mqtt
        Mqtt mqttConnect = new Mqtt(this);

        //페이스북 로그인 됬음을 알아오는 콜백 메서드
        callbackManager = CallbackManager.Factory.create();

        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        title = (TextView)findViewById(R.id.title);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        // 첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, p_remote).commitAllowingStateLoss();
        bottomNavigationView.setSelectedItemId(R.id.remote);
        title.setText("Remote");

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.remote: {
                        transaction.replace(R.id.frame_layout, p_remote).commitAllowingStateLoss();
                        title.setText("Remote");
                        break;
                    }
                    case R.id.alarm: {
                        transaction.replace(R.id.frame_layout, p_alarm).commitAllowingStateLoss();
                        title.setText("Alarm");
                        break;
                    }
                    case R.id.emotion: {
                        transaction.replace(R.id.frame_layout, p_emotion).commitAllowingStateLoss();
                        title.setText("Emotion");
                        break;
                    }
                    case R.id.setting: {
                        transaction.replace(R.id.frame_layout, p_setting).commitAllowingStateLoss();
                        title.setText("Setting");
                        break;
                    }
                    case R.id.mypage: {
                        transaction.replace(R.id.frame_layout, p_mypage).commitAllowingStateLoss();
                        title.setText("My Page");
                        break;
                    }
                }
                return true;
            }
        });

        //get permission checked
        if (checkPermissionFromDevice()) {
            Toast.makeText(getApplicationContext(), "Allow Permissions", Toast.LENGTH_SHORT).show();
        } else {
            requestPermissions();
        }


    //azure face 부분
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        final int JOB_ID = 1;
        boolean hasBeenScheduled = false;
        for (JobInfo jobInfo : jobScheduler.getAllPendingJobs()) {
            if (jobInfo.getId() == JOB_ID) {
                hasBeenScheduled  = true;
            }
        }

        MyJobService.startJob(getApplicationContext());

    }//end onCreate


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void scheduleJob(Context context) {
        JobScheduler js =
                (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(
                MY_BACKGROUND_JOB,
                new ComponentName(context, MyJobService.class));
        builder.addTriggerContentUri(
                new JobInfo.TriggerContentUri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        JobInfo.TriggerContentUri.FLAG_NOTIFY_FOR_DESCENDANTS));
        js.schedule(builder.build());
    }


    private String getRealPathFromURI(Uri contentURI) {
        Cursor c = getContentResolver().query(Uri.parse(contentURI.toString()), null,null,null,null);
        c.moveToNext();
        String absolutePath = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
        Log.i("Select",absolutePath);
        return absolutePath;
    }

    public void uploadImage(Uri uri){

        UploadService service = MyRetrofit2.getRetrofit2().create(UploadService.class);
        File file = new File(getRealPathFromURI(uri));
        MultipartBody.Part body1 = prepareFilePart("image", uri);
        RequestBody description = createPartFromString("hello, this is description speaking");
        Call<ResponseBody> call = service.uploadFile(description, body1);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("Test","성공");
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Test","실패");
            }
        });
    }

    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        File file = new File(getRealPathFromURI(fileUri));
        RequestBody requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }


    // Storage Permissions variables
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };



    //persmission method.
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // save file

            } else {
                Toast.makeText(getApplicationContext(), "PERMISSION_DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //Permissions Request Method
    private void requestPermissions(){
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.SEND_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        },REQUEST_PERMISSON_CODE);
    }

    private Boolean checkPermissionFromDevice() {
        int send_sms = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int read_sms = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        int receive_sms = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        int read_external_storage_result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int write_external_storage_result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return write_external_storage_result == PackageManager.PERMISSION_GRANTED &&
                send_sms == PackageManager.PERMISSION_GRANTED &&
                read_sms == PackageManager.PERMISSION_GRANTED &&
                receive_sms == PackageManager.PERMISSION_GRANTED &&
                read_external_storage_result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }



}

package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.projectoxford.face.*;
import com.microsoft.projectoxford.face.contract.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity {
    private final int PICK_IMAGE = 1;
    private ImageView img;
    private TextView tv;
    private MyBroadCastReceiver my;

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.imageView1);
        tv = findViewById(R.id.textView);

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        final int JOB_ID = 1;
        boolean hasBeenScheduled = false;
        for (JobInfo jobInfo : jobScheduler.getAllPendingJobs()) {
            if (jobInfo.getId() == JOB_ID) {
                hasBeenScheduled  = true;
            }
        }

        MyJobService.startJob(getApplicationContext());
        MyBroadCastReceiver my = new MyBroadCastReceiver();
        IntentFilter intentfilter = new IntentFilter(); intentfilter.addAction("com.dwfox.myapplication.SEND_BROAD_CAST");
        registerReceiver(my,intentfilter);

    }
    public static final int MY_BACKGROUND_JOB = 0;

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Uri selectedImage = data.getData();
                    uploadImage(selectedImage);
                    final File file = new File(String.valueOf(getRealPathFromURI(selectedImage)));
                    Log.i("SelectResult",file.getName());
                    String imageUrl = "{\"url\":\"http://49.143.18.93:8000/storage/albumImage/"+file.getName()+"\"}";
                    Face face = new Face(imageUrl);
                }
            }).start();
        }
    }

    public void uploadImage(Uri uri){

        UploadService service = MyRetrofit2.getRetrofit2().create(UploadService.class);
        File file = new File(getRealPathFromURI(uri));
        MultipartBody.Part body1 = prepareFilePart("image", uri);
        RequestBody description = createPartFromString("hello, this is description speaking");
        Call<ResponseBody> call = service.uploadFile(description, body1);
        tv.setText(call.request().url().toString()); //todo 디버깅용
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
        unregisterReceiver(my);
    }
}
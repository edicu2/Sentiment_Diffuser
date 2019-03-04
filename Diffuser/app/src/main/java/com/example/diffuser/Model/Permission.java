package com.example.diffuser.Model;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

public class Permission {
    private final int REQUEST_PERMISSON_CODE = 1000;
    private Activity activity;

    public Permission(Activity activity){
        this.activity = activity;
    }
    //Permissions Request Method
    private void requestPermissions(){
        ActivityCompat.requestPermissions(activity, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_PHONE_STATE,
        },REQUEST_PERMISSON_CODE);
    }

    private Boolean checkPermissionFromDevice(){
        int write_external_storage_result = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);
        int read_phone_state = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        return write_external_storage_result == PackageManager.PERMISSION_GRANTED && record_audio_result ==PackageManager.PERMISSION_GRANTED && read_phone_state == PackageManager.PERMISSION_GRANTED;
    }

    public void PermissionCheck(){
        if (checkPermissionFromDevice()){
            Log.i("Permission","success");
        }else{
            requestPermissions();
        }
    }
}

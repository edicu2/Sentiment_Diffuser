package com.example.diffuser.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.diffuser.Data.Setting;


public class Login {
    private Context context;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public Login(Context context){
        this.context = context;

        sharedPreferences = context.getSharedPreferences("pref",0);
        editor = sharedPreferences.edit();
    }

    /* 자동로그인을 하는지 가져와주는 메쏘드 */
    public boolean getAutoLogin(){
        boolean getAuto = sharedPreferences.getBoolean("login",false);
        return getAuto;
    }

    /* 로그인 할때 자동로그인하는지 안하는지 저장! */
    public void saveAutoLogin(boolean autoLogin){
        editor.putBoolean("login",autoLogin);
        editor.commit();
    }

    public boolean checkLogin(String inputId, String inputPw){
        /* 로그인 처리를 해준다. */
        Log.d(Setting.Tag,"로그인 시도");



        // saveAutoLogin(true); // 로그인 성공할경우 자동로그인을 켜놓는다.
        return true; // 임시로 true 해준다. true - 로그인 성공, false - 로그인 실패
    }

    public boolean SignUp(String inpuId, String inputPw){
        /* 회원가입 시도 */
        Log.d(Setting.Tag,"회원가입 시도");

        return true; // 임시로 true 해준다. true - 회원가입 성공, false - 회원가입 실패
    }

    public void saveId(String id){
        Log.d(Setting.Tag,"ID 저장 : "+id);

        editor.putString("UserId",id);
        editor.commit();
    }
}

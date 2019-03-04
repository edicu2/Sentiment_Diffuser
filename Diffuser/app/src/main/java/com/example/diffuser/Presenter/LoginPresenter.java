package com.example.diffuser.Presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.diffuser.Activity.LoginActivity;
import com.example.diffuser.Activity.MainActivity;
import com.example.diffuser.Data.Setting;
import com.example.diffuser.Interface.LoginInterface;


public class LoginPresenter implements LoginInterface.Presenter {

    private LoginInterface.View view;
    private Context context;
    private Activity activity;

    public LoginPresenter(LoginInterface.View view, Context context, Activity activity){
        this.view = view;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void presenterView() {
        view.setView();
    }

    @Override
    public void Login(String id, String pw) {
        // Login Model에서 id , pw 가져오는 거 아직 안하고 천천히 일단 임시 아이디 비밀번호로  조작
        if(Setting.temp_id.equals(id) && Setting.temp_pw.equals(pw)){
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
            activity.finish();
        }else {
            Toast.makeText(context, "ID, PW를 확인해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

}

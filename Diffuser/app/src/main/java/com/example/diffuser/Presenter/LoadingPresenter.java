package com.example.diffuser.Presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.example.diffuser.Activity.LoginActivity;
import com.example.diffuser.Interface.LoadingInterface;
import com.example.diffuser.Model.MqttConnect;
import com.example.diffuser.R;


public class LoadingPresenter implements LoadingInterface.Presenter {

    private LoadingInterface.View view;

    private Context context;
    private Activity activity;

    public LoadingPresenter(LoadingInterface.View view, Context context, Activity activity){
        this.view = view;
        this.context = context;
        this.activity = activity;

    }

    @Override
    public void presenterView() {
        view.setView();
    }

    @Override
    public void moveLogin() {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        activity.overridePendingTransition(R.anim.page_fade_in, R.anim.page_fade_out);
        activity.finish();
    }
}

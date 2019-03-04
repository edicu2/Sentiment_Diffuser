package com.example.diffuser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.diffuser.Data.Setting;
import com.example.diffuser.Interface.LoadingInterface;
import com.example.diffuser.Model.Login;
import com.example.diffuser.Presenter.LoadingPresenter;
import com.example.diffuser.R;

public class LoadingActivity extends AppCompatActivity implements LoadingInterface.View, View.OnClickListener {

    private LoadingInterface.Presenter presenter;

    private ImageButton login_move;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        presenter = new LoadingPresenter(LoadingActivity.this,getApplicationContext(),LoadingActivity.this);
        presenter.presenterView();
    }

    @Override
    public void setView() {
        login_move = (ImageButton)findViewById(R.id.loginButton);
        login_move.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        presenter.moveLogin();
    }


}

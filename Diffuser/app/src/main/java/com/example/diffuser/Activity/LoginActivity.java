package com.example.diffuser.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diffuser.Interface.LoginInterface;
import com.example.diffuser.Presenter.LoginPresenter;
import com.example.diffuser.R;

public class LoginActivity extends Activity implements LoginInterface.View ,View.OnClickListener{

    private LoginInterface.Presenter presenter;

    private EditText email, password;
    private Button btn_login, btn_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(LoginActivity.this, getApplicationContext(),this);
        presenter.presenterView();
    }

    @Override
    public void setView() {
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        btn_login = (Button)findViewById(R.id.login);
        btn_login.setOnClickListener(this);
        btn_register = (Button)findViewById(R.id.register);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.login){
            String id = email.getText().toString();
            String pw = password.getText().toString();
            presenter.Login(id,pw);
        }
    }
}

package com.example.diffuser.Presenter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.constraint.solver.widgets.WidgetContainer;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;


import com.example.diffuser.Activity.MainActivity;
import com.example.diffuser.Data.Setting;
import com.example.diffuser.Interface.MainInterface;
import com.example.diffuser.Interface.Button.Command;
import com.example.diffuser.Model.MqttConnect;
import com.example.diffuser.Model.Permission;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static com.example.diffuser.Data.Setting.b;
import static com.example.diffuser.Data.Setting.bright;
import static com.example.diffuser.Data.Setting.g;
import static com.example.diffuser.Data.Setting.r;


public class MainPresenter implements MainInterface.Presenter {

    private Command in ;
    private Permission permission;
    private MqttConnect mqttConnect;
    private MainInterface.View view;
    private Context context;
    private Activity activity;

    public MainPresenter(MainInterface.View view, Context context, Activity activity){
        this.view = view;
        this.context = context;
        this.activity = activity;
        //permission Check
        permission = new Permission(activity);
        permission.PermissionCheck();
        //Mqtt Connect
        mqttConnect = new MqttConnect(activity);
    }

    @Override
    public void presenterView() {
        view.setView();
    }

    public void mqttSubCallback(final TextView temp){
        MqttConnect.client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
            }
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                if(new String(message.getPayload()).split("_")[1].equals("low") ||
                        new String(message.getPayload()).split("_")[1].equals("enough"))
                    temp.setText(new String(message.getPayload()));
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
            }
        });
    }

    public void setColor(int pixel, int bright){
        r = Color.red(pixel);
        g = Color.green(pixel);
        b = Color.blue(pixel);
        Setting.bright = bright;
    }
    public void setBright(int bright){
        Setting.bright = bright;
    }




}

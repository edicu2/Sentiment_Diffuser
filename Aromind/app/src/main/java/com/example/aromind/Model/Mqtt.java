package com.example.aromind.Model;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.aromind.Activity.Loding;
import com.example.aromind.Activity.MainActivity_fragment.MenuRemote;
import com.example.aromind.Data.URL;
import com.example.aromind.R;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import static org.apache.commons.lang3.StringUtils.split;

public class Mqtt {

    private static String clientId;
    private final String CLIENTID = "hyeonbin";
    private final String PASSWORD = "password";
    public static MqttAndroidClient client;
    private static Activity activity;
    private String mqttGetMessage ="";
    private static Random random = new Random();
    private View view;
    private Context context;

    public Mqtt(final Activity activity, final Context context){
        this.activity = activity;
        this.context = context;
        clientId = MqttClient.generateClientId();

        client = new MqttAndroidClient(activity, URL.MQTTURL, clientId);

        MqttConnectOptions options = new MqttConnectOptions();
        try {
            options.setUserName(CLIENTID);
            options.setPassword(PASSWORD.toCharArray());
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.i("MQTT","mqttConnect");
                    int qos = 1;
                    try {
                        IMqttToken subToken = client.subscribe(URL.MQTTTOPIC, qos);
                        subToken.setActionCallback(new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                Log.i("MQTT","mqttSubStart");
                                clientSub(activity);
                            }
                            @Override
                            public void onFailure(IMqttToken asyncActionToken,
                                                  Throwable exception) {
                            }
                        });
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                    CheckThread checkThread = new CheckThread();
                    Thread t = new Thread(checkThread);
                    t.setDaemon(true);
                    t.start();

                }//end public void onSuccess
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(activity, "onFailure", Toast.LENGTH_SHORT).show();
                }//end public void onFailure
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }//end connectMQTTServer

//        // 라즈베리파이로부터 값이 올 때.
//        client.setCallback(new MqttCallbackExtended() {
//            @Override
//            public void connectComplete(boolean reconnect, String serverURI) {
//
//            }
//
//            @Override
//            public void connectionLost(Throwable cause) { }
//
//            @Override
//            public void messageArrived(String topic, MqttMessage message) throws Exception {
////                String msg = new String(message.getPayload());
////                Log.i("콜백을1", msg);
////                Log.i("콜백을2", msg.substring(0,1));
////                if (msg.substring(0,1).equals("%")){
////                    Log.i("콜백을3", msg.substring(1));
////                    int id = Integer.parseInt(msg.substring(1));
////                    Log.i("콜백을4", String.valueOf(id));
////                    Http_getCustomCard http_getCustomCard = new Http_getCustomCard(id, context);
//////                    Intent intent = new Intent(activity, Loding.class);
//////                    activity.startActivity(intent);
////                }
//
//            }
//
//            @Override
//            public void deliveryComplete(IMqttDeliveryToken token) { }
//        });
    }

//    public void setCallback(MqttCallbackExtended callback) {
//        client.setCallback(callback);
//    }

    public static void clientPub(final Activity acQtivity,String payload){
        Log.i("Mqtt",payload);
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(URL.MQTTTOPIC, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }

    public static void clientSub(final Activity activity){
        int qos = 2;
        try {
            IMqttToken subToken = client.subscribe(URL.MQTTTOPIC, qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    Log.i("MQTT","subConnected");
                    //Mqtt.clientPub(null,"temp_humidity");
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    //mqtt 상태 확인하는 부분
    public static void checkMqtt() {
        byte[] encodedPayload = new byte[0];
        String payload = "위대한4조 취업성공을 기원합니다";
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(URL.MQTTTOPIC, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }


    public static Activity getActivity(){
        return activity;
    }

    public String getMqttGetMessage(){
        return mqttGetMessage;
    }




}

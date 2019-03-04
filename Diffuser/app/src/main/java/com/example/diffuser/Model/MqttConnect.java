package com.example.diffuser.Model;

import android.app.Activity;
import android.media.MediaCodec;
import android.util.Log;
import android.widget.Toast;

import com.example.diffuser.Data.ServerURL;
import com.example.diffuser.Data.Setting;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class MqttConnect {

    private static String clientId;
    public static MqttAndroidClient client;



    public MqttConnect(final Activity activity){
        clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(activity, ServerURL.MQTTURL, clientId);
        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.i("MQTT","mqttConnect");
                    int qos = 1;
                    try {
                        IMqttToken subToken = client.subscribe(ServerURL.MQTTTOPIC, qos);
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
                }//end public void onSuccess
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(activity, "onFailure", Toast.LENGTH_SHORT).show();
                }//end public void onFailure
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }//end connectMQTTServer


    }

    public static void clientPub(final Activity activity,String payload){
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(ServerURL.MQTTTOPIC, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }


    public static void clientSub(final Activity activity){
        int qos = 2;
        try {
            IMqttToken subToken = client.subscribe(ServerURL.MQTTTOPIC, qos);
            subToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.i("MQTT","subConnected");
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}

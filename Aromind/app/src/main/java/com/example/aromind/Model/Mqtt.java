package com.example.aromind.Model;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.example.aromind.Data.URL;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class Mqtt {

    private static String clientId;
    public static MqttAndroidClient client;



    public Mqtt(final Activity activity){
        clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(activity, URL.MQTTURL, clientId);
        try {
            IMqttToken token = client.connect();
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

}

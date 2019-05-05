package com.example.aromind.Model;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.aromind.Data.URL;

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

public class Mqtt{

    private static String clientId;
    private final String CLIENTID = "hyeonbin";
    private final String PASSWORD = "password";
    public static MqttAndroidClient client;
    private static Activity activity;
    private static Random random = new Random();

    public Mqtt(final Activity activity){
        this.activity = activity;
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


        // 라즈베리파이로부터 값이 올 때.
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String msg = new String(message.getPayload());
//                if(topic.equals(URL.MQTTTOPIC)) {
//
//                }
                if (msg.equals("금상원짱장맨")){
                    Log.i("MATTTTTT", msg);
                }else {
                    Http_getCustomCard http_getCustomCard = new Http_getCustomCard(Integer.parseInt(msg));
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }


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
        String payload = "금상원짱장맨";
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


}

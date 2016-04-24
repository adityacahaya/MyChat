package com.example.android.mychat.Network;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttToken;
import org.json.JSONObject;

public class MqttHandler {

    final static String TAG = "MqttHandler";
    final static String topic = "HIMACHAT";
    Context context;
    MqttAndroidClient client;
    MqttCallback mqttCallback;

    public MqttHandler(Context context, MqttCallback mqttCallback) {
        this.context = context;
        this.mqttCallback = mqttCallback;
        setutMQTT();
    }

    private void setutMQTT() {
        String clientID = MqttClient.generateClientId();
        client = new MqttAndroidClient(context, NetHelper.getMqttDomain(context), clientID);
        client.setCallback(mqttCallback);
    }

    public void connect() {
        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.i(TAG, "mqtt connect success");
                    subscribe();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.i(TAG, "mqtt connect failed");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            client.disconnect();
            //client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void subscribe() {
        int qos = 1;
        try {
            IMqttToken subscribeToken = client.subscribe(topic, qos);
            subscribeToken.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.i(TAG, "mqtt subscribe success");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.i(TAG, "mqtt subscribe failed");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publish(String username, String messageText, boolean jenis_kelamin,
                        String date) {
        try {
            MqttMessage message = new MqttMessage();
            message.setQos(1);
            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("message", messageText);
            json.put("jenis_kelamin", jenis_kelamin);
            json.put("date", date);
            Log.i(TAG, "publish : " + json.toString());
            message.setPayload(json.toString().getBytes());
            client.publish(topic, message);
            Log.i(TAG, "onPublish success");
        } catch (Exception e) {
            Log.i(TAG, "onPublish error");
            e.printStackTrace();
        }
    }

}

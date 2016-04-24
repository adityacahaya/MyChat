package com.example.android.mychat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.mychat.Adapter.ChatViewAdapter;
import com.example.android.mychat.Model.Chat;
import com.example.android.mychat.Network.MqttHandler;
import com.example.android.mychat.Network.NetHelper;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GroupChat extends AppCompatActivity implements View.OnClickListener {
    String username;
    boolean jenis_kelamin;

    ListView chat;
    EditText message;
    Button send;
    MqttHandler handler;

    ChatViewAdapter chatViewAdapter;
    ArrayList<Chat> chatArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        username = AppConnfig.getLoggedUserName(GroupChat.this);
        jenis_kelamin = AppConnfig.getLoggedGender(GroupChat.this);

        chat = (ListView) findViewById(R.id.listViewChat);

        chatViewAdapter = new ChatViewAdapter(GroupChat.this,
                username, chatArrayList);

        chat.setAdapter(chatViewAdapter);

        message = (EditText) findViewById(R.id.txtMessage);
        send = (Button) findViewById(R.id.btnSend);

        send.setOnClickListener(this);

        handler = new MqttHandler(GroupChat.this, callback);
    }

    MqttCallback callback = new MqttCallback() {
        @Override
        public void connectionLost(Throwable cause) {

        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            try {
                Chat chat = Chat.parseJSON(message.toString());
                chatArrayList.add(chat);
                chatViewAdapter.notifyDataSetChanged();
                scrollMyListViewToBottom();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {

        }
    };

    public void scrollMyListViewToBottom() {
        chat.post(new Runnable() {
            @Override
            public void run() {
                chat.setSelection(chat.getCount()-1);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        handler.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.disconnect();
    }


    @Override
    public void onClick(View v) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        String date = dateFormat.format(Calendar.getInstance().getTime());
        switch (v.getId()) {
            case R.id.btnSend: {
                handler.publish(username, message.getText().toString(), jenis_kelamin, date);
                message.setText("");
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_group_chat, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AppConnfig.setLoggedInStatus(GroupChat.this, AppConnfig.SIGNED_OUT);
        Intent intent = new Intent(GroupChat.this, LoginView.class);
        startActivity(intent);
        finish();
        return super.onOptionsItemSelected(item);
    }
}

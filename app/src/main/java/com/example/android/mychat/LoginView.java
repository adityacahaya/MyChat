package com.example.android.mychat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.mychat.Network.NetHelper;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public class LoginView extends AppCompatActivity implements View.OnClickListener {

    EditText username, password;
    Button login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_view);

        username = (EditText) findViewById(R.id.txtUsername);
        password = (EditText) findViewById(R.id.txtPassword);
        login = (Button) findViewById(R.id.btnLogin);
        register = (Button) findViewById(R.id.btnSignUp);

        login.setOnClickListener(this);
        register.setOnClickListener(this);

        if(AppConnfig.getSignedInStatus(LoginView.this)){
            Intent intent = new Intent(LoginView.this, GroupChat.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin: {
                if(!TextUtils.isEmpty(username.getText().toString())){
                    if(!TextUtils.isEmpty(password.getText().toString())){
                        NetHelper.doLogin(LoginView.this, username.getText().toString(),
                                password.getText().toString(), responseHandler);
                    }else{
                        password.setError("fill this field");
                    }
                }else{
                    username.setError("fill this field");
                }
                break;
            }
            case R.id.btnSignUp: {
                Intent intent = new Intent(LoginView.this, RegisterView.class);
                startActivity(intent);
                break;
            }
        }
    }

    AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            try{
                //terima respone dari server
                String result = new String(responseBody, "UTF-8");
                switch (result){
                    //respon jika password salah
                    case "pass":{
                        Toast.makeText(LoginView.this, "Password Salah", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    default:{
                        Intent intent = new Intent(LoginView.this, GroupChat.class);
                        startActivity(intent);
                        AppConnfig.saveUser(LoginView.this, username.getText().toString(), Boolean.parseBoolean(result));
                        AppConnfig.setLoggedInStatus(LoginView.this, AppConnfig.SIGNED_IN);
                        finish();
                        break;
                    }
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(LoginView.this, "Username atau Password Salah", Toast.LENGTH_SHORT).show();
        }
    };
}

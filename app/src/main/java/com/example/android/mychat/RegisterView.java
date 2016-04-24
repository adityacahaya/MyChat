package com.example.android.mychat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.mychat.Network.NetHelper;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class RegisterView extends AppCompatActivity implements View.OnClickListener {

    Spinner jenis_kelamin;
    EditText nama_lengkap, email, username, password;
    CheckBox setuju;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_view);

        jenis_kelamin = (Spinner) findViewById(R.id.spnrJenisKelamin);
        nama_lengkap = (EditText) findViewById(R.id.txtNamaLengkap);
        email = (EditText) findViewById(R.id.txtEmail);
        username = (EditText) findViewById(R.id.txtUsername);
        password = (EditText) findViewById(R.id.txtPassword);
        setuju = (CheckBox) findViewById(R.id.cbSetuju);
        btnRegister = (Button) findViewById(R.id.btnRegis);

        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegis: {
                if (!TextUtils.isEmpty(nama_lengkap.getText().toString())) {
                    if (isEmailValid(email.getText().toString())) {
                        if (!TextUtils.isEmpty(username.getText().toString())) {
                            if (!TextUtils.isEmpty(password.getText().toString())) {
                                if (setuju.isChecked()) {
                                    NetHelper.doRegister(RegisterView.this,
                                            nama_lengkap.getText().toString(),
                                            email.getText().toString().toString(),
                                            username.getText().toString(),
                                            password.getText().toString(),
                                            jenis_kelamin.getSelectedItem().equals("Laki-Laki"),
                                            responseHandler);
                                } else {
                                    setuju.setError("Klik Pada Check Setuju");
                                }
                            } else {
                                password.setError("Masukkan Password");
                            }
                        } else {
                            username.setError("Masukkan Username");
                        }
                    } else {
                        email.setError("Masukkan Email yang Benar");
                    }
                } else {
                    nama_lengkap.setError("Masukkan Nama");
                }
                break;
            }
        }
    }

    boolean isEmailValid(String email){
        if(!TextUtils.isEmpty(email) && email.contains("@")){
            return true;
        }else{
            return false;
        }
    }

    AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            Toast.makeText(RegisterView.this, "Register Success", Toast.LENGTH_SHORT).show();
            finish();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(RegisterView.this, "Username atau Password Salah", Toast.LENGTH_SHORT).show();
        }
    };

}

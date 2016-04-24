package com.example.android.mychat.Network;

import android.content.Context;
import android.util.Log;

import com.example.android.mychat.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class NetHelper {

    private static AsyncHttpClient client = new AsyncHttpClient();
    private static final String TAG = "NetHelper";

    public static String getWebDomain(Context context){
        return "http://"+context.getString(R.string.domain_url)+":3000/api";
    }

    public static String getMqttDomain(Context context){
        return "tcp://"+context.getString(R.string.domain_url)+":1883";
    }

    public static void doLogin(Context context, String username, String password,
                          AsyncHttpResponseHandler responseHandler){ //membuat kondisi untuk pengambilan data dari internet
        String url = getWebDomain(context)+"/login";
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("password", password);

        Log.i(TAG, "doLogin : "+url);

        client.post(url, params, responseHandler);
    }

    public static void doRegister(Context context, String nama_lengkap, String email,
                                  String username, String password, boolean gender, AsyncHttpResponseHandler event){
        //register url
        String url = getWebDomain(context)+"/add_user";

        //request
        RequestParams params = new RequestParams();
        params.put("nama_lengkap", nama_lengkap);
        params.put("email", email);
        params.put("username", username);
        params.put("password", password);
        params.put("gender", gender);

        Log.i(TAG, "doRegister : " + url);

        //method post menggunakan library loop
        client.post(url, params, event);
    }
}

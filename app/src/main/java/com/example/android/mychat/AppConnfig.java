package com.example.android.mychat;

import android.content.Context;
import android.content.SharedPreferences;

public class AppConnfig {

    final static String pref = "MyChat";
    final static String user = "username";
    final static String gender = "jenis_kelamin";
    final static String signed = "signed";

    private static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(pref, Context.MODE_APPEND);
    }
    public static void saveUser(Context context, String username, boolean jenis_kelamin){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(user, username);
        editor.putBoolean(gender, jenis_kelamin);
        editor.apply();
    }
    public static String getLoggedUserName(Context context){
        return getSharedPreferences(context).getString(user,"");
    }
    public static boolean getLoggedGender(Context context){
        return getSharedPreferences(context).getBoolean(gender, true);
    }
    public static boolean SIGNED_IN = true;
    public static boolean SIGNED_OUT = false;
    public static void setLoggedInStatus(Context context, boolean status){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(signed, status);
        editor.apply();
    }
    public static boolean getSignedInStatus(Context context){
        return getSharedPreferences(context).getBoolean(signed, false);
    }
}

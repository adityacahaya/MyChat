package com.example.android.mychat.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class Chat {
    String username;
    String message;
    boolean jenis_kelamin;
    String date;

    public Chat() {
    }

    public Chat(String username, String message, boolean jenis_kelamin, String date) {
        this.username = username;
        this.message = message;
        this.jenis_kelamin = jenis_kelamin;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public Chat setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Chat setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean isJenis_kelamin() {
        return jenis_kelamin;
    }

    public Chat setJenis_kelamin(boolean jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Chat setDate(String date) {
        this.date = date;
        return this;
    }

    public static Chat parseJSON(String response) throws JSONException{
        Chat chat = new Chat();

        JSONObject object = new JSONObject(response);
        chat.setUsername(object.getString("username"))
                .setJenis_kelamin(object.getBoolean("jenis_kelamin"))
                .setMessage(object.getString("message"))
                .setDate(object.getString("date"));

        return chat;
    }

}

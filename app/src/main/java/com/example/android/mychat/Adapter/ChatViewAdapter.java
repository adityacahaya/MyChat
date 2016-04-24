package com.example.android.mychat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.mychat.Model.Chat;
import com.example.android.mychat.R;

import java.util.List;

public class ChatViewAdapter extends BaseAdapter {

    List<Chat> chatList;
    String username;
    Context context;

    public ChatViewAdapter(Context context, String username, List<Chat> chatList) {
        this.chatList = chatList;
        this.username = username;
        this.context = context;
    }

    @Override
    public int getCount() {
        return chatList.size();
    }

    @Override
    public Object getItem(int position) {
        return chatList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Chat chat = ((Chat) getItem(position));
        boolean isMyChat = chat.getUsername().equals(username);
        View messageView;
        if (isMyChat) {
            messageView = inflater.inflate(R.layout.list_row_me, parent, false);
        } else {
            messageView = inflater.inflate(R.layout.list_row_other, parent, false);

            ImageView imageView = (ImageView) messageView
                    .findViewById(R.id.imageView2);
            if (chat.isJenis_kelamin()) {
                imageView.setImageResource(R.drawable.man);
            } else {
                imageView.setImageResource(R.drawable.woman);
            }

            TextView username = (TextView) messageView
                    .findViewById(R.id.txtUsername);
            username.setText(chat.getUsername());
        }
        TextView message = (TextView) messageView.findViewById(R.id.txtMyMessage);
        message.setText(chat.getMessage());
        TextView time = (TextView) messageView.findViewById(R.id.txtTime);
        time.setText(chat.getDate());

        return messageView;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }
}

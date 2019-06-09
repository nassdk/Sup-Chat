package com.example.firabasefirstexperience.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firabasefirstexperience.Model.Chat;
import com.example.firabasefirstexperience.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {


    public static final int MSG_LEFT = 0;
    public static final int MSG_RIGHT = 1;

    private Context mContext;
    private List<Chat> listChats;

    private FirebaseUser fUser;

    public ChatAdapter(Context mContext, List<Chat> listChats) {
        this.mContext = mContext;
        this.listChats = listChats;
    }


    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == MSG_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, viewGroup, false);
            return new ChatAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, viewGroup, false);
            return new ChatAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder viewHolder, int i) {

        Chat chat = listChats.get(i);

        viewHolder.tv_showMessage.setText(chat.getMessage());
    }

    @Override
    public int getItemCount() {
        return listChats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_showMessage;
        public ImageView backgroundImage;


        public ViewHolder(View itemView) {
            super(itemView);

            tv_showMessage = itemView.findViewById(R.id.tv_showMessage);
            backgroundImage = itemView.findViewById(R.id.backgroundImage);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fUser = FirebaseAuth.getInstance().getCurrentUser();

        if (listChats.get(position).getSender().equals(fUser.getUid())) {
            return MSG_RIGHT;
        } else {
            return MSG_LEFT;
        }
    }
}

package com.example.firabasefirstexperience.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.firabasefirstexperience.activity.MainActivity;
import com.example.firabasefirstexperience.model.Chat;
import com.example.firabasefirstexperience.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {


    public static final int MSG_LEFT = 0;
    public static final int MSG_RIGHT = 1;

    private Context mContext;
    private List<Chat> listChats;

    private FirebaseUser fUser;

    public MessageAdapter(Context mContext, List<Chat> listChats) {
        this.mContext = mContext;
        this.listChats = listChats;
    }


    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == MSG_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, viewGroup, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, viewGroup, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder viewHolder, int i) {

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

package com.example.firabasefirstexperience.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firabasefirstexperience.R;
import com.example.firabasefirstexperience.activity.ChatActivity;
import com.example.firabasefirstexperience.activity.DiffProfileActivity;
import com.example.firabasefirstexperience.activity.MainActivity;
import com.example.firabasefirstexperience.model.User;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder> {

    private Context mContext;
    private List<User> listUsers;

    public ChatsAdapter(Context mContext, List<User> listUsers) {
        this.mContext = mContext;
        this.listUsers = listUsers;
    }


    @NonNull
    @Override
    public ChatsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, viewGroup, false);
        return new ChatsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsAdapter.ViewHolder viewHolder, int i) {
        final User user = listUsers.get(i);
        viewHolder.tv_Name.setText(user.getUserName());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra("userId", user.getId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_Name;
        public ImageView userImage;


        public ViewHolder(View itemView) {
            super(itemView);

            tv_Name = itemView.findViewById(R.id.tv_Name);
            userImage = itemView.findViewById(R.id.userImage);
        }
    }


}

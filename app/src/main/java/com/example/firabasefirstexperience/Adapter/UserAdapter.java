package com.example.firabasefirstexperience.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firabasefirstexperience.Activities.ChatActivity;
import com.example.firabasefirstexperience.Model.User;
import com.example.firabasefirstexperience.R;


import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private List<User> listUsers;

    public UserAdapter(Context mContext, List<User> listUsers) {
        this.mContext = mContext;
        this.listUsers = listUsers;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, viewGroup, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
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

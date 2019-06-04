package com.example.firabasefirstexperience.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.firabasefirstexperience.Profile.User;
import com.example.firabasefirstexperience.R;


import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context mContext;
    private List<User> listUsers;

    public Adapter(Context mContext, List<User> listUsers) {
        this.mContext = mContext;
        this.listUsers = listUsers;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, viewGroup , false);
        return new Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        User user = listUsers.get(i);
        viewHolder.tv_Name.setText(user.getUserName());

        if(!(user.getImageURL() == null)) {
            Glide.with(mContext).load(user.getImageURL()).into(viewHolder.userImage);
        }
    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_Name;
        public ImageView userImage;


        public ViewHolder(View itemView) {
            super(itemView);

            tv_Name = itemView.findViewById(R.id.tv_Name);
            userImage = itemView.findViewById(R.id.userImage);
        }
    }




}

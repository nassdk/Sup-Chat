package com.example.firabasefirstexperience.adapter;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.firabasefirstexperience.activity.DiffProfileActivity;
import com.example.firabasefirstexperience.model.User;
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

        viewHolder.bind(listUsers.get(i));

    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_Name;
        private ImageView userImage;


        ViewHolder(View itemView) {
            super(itemView);

            tv_Name = itemView.findViewById(R.id.tv_Name);
            userImage = itemView.findViewById(R.id.userImage);
        }

        void bind(final User user) {
            tv_Name.setText(user.getUserName());

            if (user.getImageURL().equals("default")) {
                userImage.setImageResource(R.mipmap.ic_launcher_round);
            } else {

                Glide
                        .with(mContext)
                        .load(user.getImageURL())
                        .into(userImage);
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DiffProfileActivity.class);
                    intent.putExtra("id", user.getId());
                    mContext.startActivity(intent);
                }
            });
        }
    }


}

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
import com.example.firabasefirstexperience.model.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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

        viewHolder.bind(listUsers.get(i));

    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_Name;
        private ImageView userImage;
        private CircleImageView civ_StatusOn;
        private CircleImageView civ_StatusOff;


        ViewHolder(View itemView) {
            super(itemView);

            tv_Name = itemView.findViewById(R.id.tv_Name);
            userImage = itemView.findViewById(R.id.userImage);
            civ_StatusOff = itemView.findViewById(R.id.civStatus_Off);
            civ_StatusOn = itemView.findViewById(R.id.civStatus_On);
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

            if (user.getStatus().equals("online")) {
                civ_StatusOn.setVisibility(View.VISIBLE);
                civ_StatusOff.setVisibility(View.GONE);
            } else {
                civ_StatusOn.setVisibility(View.GONE);
                civ_StatusOff.setVisibility(View.VISIBLE);
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ChatActivity.class);
                    intent.putExtra("userId", user.getId());
                    mContext.startActivity(intent);
                }
            });

        }
    }
}

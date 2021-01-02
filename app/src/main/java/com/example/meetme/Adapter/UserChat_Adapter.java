package com.example.meetme.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetme.ChatActivity1;
import com.example.meetme.Entity.Chat;
import com.example.meetme.Entity.User;
import com.example.meetme.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserChat_Adapter extends RecyclerView.Adapter<UserChat_Adapter.ViewHolder_For_All> {
    private Context mContext;
    private List<User> mUsers;

    public UserChat_Adapter(Context context, List<User> users) {
        this.mContext = context;
        this.mUsers = users;
    }

    @Override
    public int getItemCount() {
        return mUsers == null ? 0 : mUsers.size();
    }

    @Override
    public UserChat_Adapter.ViewHolder_For_All onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_user_item, parent, false);
        return new UserChat_Adapter.ViewHolder_For_All(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserChat_Adapter.ViewHolder_For_All holder, int position) {
        User user = mUsers.get(position);
        holder.chat_LBL_name.setText(user.getName());

        if(user.getMainImage() != null){
            Picasso.with(mContext).load(user.getMainImage()).into(holder.chat_IMG_imageProfile);
        } else {
            holder.chat_IMG_imageProfile.setImageResource(R.mipmap.ic_launcher);
        }

        holder.chat_LBL_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity1.class);
                intent.putExtra("id", user.getId());
                intent.putExtra("name", user.getName());
                intent.putExtra("image", user.getMainImage().toString());
                intent.putExtra("email", user.getEmail());
                mContext.startActivity(intent);
            }
        });
    }

    private User getItem(int position) {
        return mUsers.get(position);
    }

    static class ViewHolder_For_All extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView chat_IMG_imageProfile;
        public TextView chat_LBL_name;

        public ViewHolder_For_All(View itemView) {
            super(itemView);
            chat_IMG_imageProfile = itemView.findViewById(R.id.chat_user_profile);
            chat_LBL_name = itemView.findViewById(R.id.chat_user_name);
        }

        @Override
        public void onClick(View view) {
            Log.d("onclick", "onClick " + getLayoutPosition());
        }
    }
}

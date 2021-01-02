package com.example.meetme.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetme.Entity.Chat;
import com.example.meetme.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder_For_All> {

    public  static final int MSG_TYPE_LEFT = 0;
    public  static final int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private List<Chat> mChat;
    private Uri imageUrl;
    FirebaseUser currentUser;

    public MessageAdapter(Context context, List<Chat> chat, Uri imageUrl) {
        this.mContext = context;
        this.mChat = chat;
        this.imageUrl = imageUrl;
    }

    @Override
    public int getItemCount() {
        return mChat == null ? 0 : mChat.size();
    }

    @Override
    public MessageAdapter.ViewHolder_For_All onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == MSG_TYPE_RIGHT){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right, parent, false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left, parent, false);
        }
        return new MessageAdapter.ViewHolder_For_All(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder_For_All holder, int position) {
            Chat chat = mChat.get(position);
            holder.chat_LBL_message.setText(chat.getMessage());

            if(imageUrl != null){
                Picasso.with(mContext).load(imageUrl).into(holder.chat_IMG_imageProfile);
            } else {
                holder.chat_IMG_imageProfile.setImageResource(R.mipmap.ic_launcher);
            }
    }

    private Chat getItem(int position) {
        return mChat.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(currentUser.getUid())){
            return MSG_TYPE_RIGHT;
        }else
            return MSG_TYPE_LEFT;
    }

    static class ViewHolder_For_All extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView chat_IMG_imageProfile;
        public TextView chat_LBL_message;

        public ViewHolder_For_All(View itemView) {
            super(itemView);
            chat_IMG_imageProfile = itemView.findViewById(R.id.chat_message_image);
            chat_LBL_message = itemView.findViewById(R.id.chat_message_text);
        }

        @Override
        public void onClick(View view) {
            Log.d("onclick", "onClick " + getLayoutPosition());
        }
    }

}

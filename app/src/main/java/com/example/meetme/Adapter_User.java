package com.example.meetme;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Adapter_User extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int FEMALE = 0;
    private final int MALE = 1;
    private Context context;
    private ArrayList<User> articles;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Bitmap bitmap;

    public Adapter_User(Context context, ArrayList<User> articles) {
        this.context = context;
        this.articles = articles;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return articles == null ? 0 : articles.size();
    }


    // specify the row layout file and click for each row
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_for_all, parent, false);
            return new ViewHolder_For_All(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        User user = getItem(position);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
            ViewHolder_For_All mHolder = (ViewHolder_For_All) holder;
            mHolder.article_LBL_title.setText(user.getName());
            mHolder.article_LBL_subTitle.setText(user.getAge() + "");
            mHolder.article_LBL_city.setText(user.getCity());
            mHolder.article_IMG_back.setBackgroundColor(Color.GRAY);
            String urlImage;
            urlImage = checkClient(LoginActivity.currentUserEmail);
            mHolder.article_IMG_back.setBackgroundColor(Color.GRAY);
            mHolder.list_for_all_BTN_openChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChatActivity1.class);
                    context.startActivity(intent);
                }
            });
            Glide.with(context)
                    .load(urlImage)
                    .apply(RequestOptions.placeholderOf(R.drawable.bake))
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource,
                                                    @Nullable Transition<? super Drawable> transition) {

                            mHolder.article_IMG_back.setBackground(resource);
                        }
                    });


    }


    private String checkClient(String currentUserEmail) {
        for (User user : MainActivity.allClients.allClientsInDB) {
            if (user.getEmail().equals(currentUserEmail))
                return user.getMainImage();
        }
        return null;
    }


    private User getItem(int position) {
        return articles.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).getPersonGender() == User.Gender.MALE) {
            return MALE;
        } else if (getItem(position).getPersonGender() == User.Gender.FEMALE) {
            return FEMALE;
        }

        return FEMALE;
    }

    static class ViewHolder_For_All extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView article_IMG_back;
        public TextView article_LBL_title,article_LBL_city;
        public TextView article_LBL_subTitle;
        public Button list_for_all_BTN_openChat;

        public ViewHolder_For_All(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            article_IMG_back = itemView.findViewById(R.id.article_IMG_back);
            article_LBL_title = itemView.findViewById(R.id.article_LBL_title);
            article_LBL_subTitle = itemView.findViewById(R.id.article_LBL_subTitle);
            article_LBL_city = itemView.findViewById(R.id.article_LBL_city);
            list_for_all_BTN_openChat = itemView.findViewById(R.id.list_for_all_BTN_openChat);
        }

        @Override
        public void onClick(View view) {
            Log.d("onclick", "onClick " + getLayoutPosition());
        }
    }

}

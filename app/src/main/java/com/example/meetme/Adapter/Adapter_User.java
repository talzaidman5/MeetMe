package com.example.meetme.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetme.ChatActivity;
import com.example.meetme.Entity.User;
import com.example.meetme.MainActivity;
import com.example.meetme.R;
import com.example.mylibrary.MainActivityLibrary;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import com.squareup.picasso.Picasso;

public class Adapter_User extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int FEMALE = 0;
    private final int MALE = 1;
    private Context context;
    private ArrayList<User> articles;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Bitmap bitmap;
    ArrayList<String> allImages = new ArrayList<>();

    private int imagesLoadIndex = 0;
    private int numOfImages;

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
        getImagesUrls(user.getEmail());
        mHolder.article_LBL_title.setText(user.getFirstName());
        mHolder.article_LBL_subTitle.setText(user.getAge() + "");
        mHolder.article_LBL_city.setText(user.getCity());
        mHolder.article_PRB_progressBar1.setVisibility(View.VISIBLE);
        this.getImageFromStorage(mHolder.article_IMG_imageProfile, user, mHolder.article_PRB_progressBar1);
        mHolder.list_for_all_BTN_openChat.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("id", user.getId());
            intent.putExtra("name", user.getFirstName());
            intent.putExtra("image", user.getMainImage().toString());
            intent.putExtra("email", user.getEmail());
            context.startActivity(intent);
        });
        mHolder.article_IMG_imageProfile.setOnClickListener(view -> openImagesPage(user, allImages));
    }

    public void getImagesUrls(String userEmail) {
        StorageReference reference = storageReference.child(userEmail);
        reference.listAll().addOnSuccessListener(listResult -> readImagesFromStorage(listResult));
    }

    private void readImagesFromStorage(ListResult listResult) {
        numOfImages = listResult.getItems().size();
        for(int i = 0 ; i < numOfImages ; i++){
            loadOneImage(listResult.getItems().get(i));
        }
    }

    private void loadOneImage(StorageReference storageReference) {
        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
            allImages.add(uri.toString());
            imagesLoadIndex+=1;
        });
    }

    private void openImagesPage(User user, ArrayList<String> imagesList) {
        MainActivityLibrary.initImages((Activity) context);
        MainActivityLibrary.changeTitle("Pictures Of "+user.getFirstName());
        MainActivityLibrary.changeButtonText("Back");
        MainActivityLibrary.numberOfImageInRow(1,570,570);
        MainActivityLibrary.openAlbum((Activity) context,imagesList, null);
    }


    private void getImageFromStorage(ImageView image, User user, ProgressBar progressBar) {
        storageReference.child(user.getEmail()).child("profile").getDownloadUrl().addOnSuccessListener(uri -> {
            user.setMainImage(uri);
            Picasso.get().load(uri).into(image);
            progressBar.setVisibility(View.INVISIBLE);
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
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

    public static class ViewHolder_For_All extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView article_IMG_imageProfile;
        public TextView article_LBL_title, article_LBL_city;
        public TextView article_LBL_subTitle;
        public Button list_for_all_BTN_openChat;
        public ProgressBar article_PRB_progressBar1;

        public ViewHolder_For_All(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            article_IMG_imageProfile = itemView.findViewById(R.id.article_IMG_imageProfile);
            article_LBL_title = itemView.findViewById(R.id.article_LBL_title);
            article_LBL_subTitle = itemView.findViewById(R.id.article_LBL_subTitle);
            article_LBL_city = itemView.findViewById(R.id.article_LBL_city);
            list_for_all_BTN_openChat = itemView.findViewById(R.id.list_for_all_BTN_openChat);
            article_PRB_progressBar1 = itemView.findViewById(R.id.article_PRB_progressBar1);
        }

        @Override
        public void onClick(View view) {
            Log.d("onclick", "onClick " + getLayoutPosition());
        }
    }

}

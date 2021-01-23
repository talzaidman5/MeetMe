package com.example.meetme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meetme.Entity.User;
import com.example.meetme.utils.Photos;
import com.example.meetme.utils.PhotosController;
import com.example.mylibrary.MainActivityLibrary;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MyProfileActivity extends AppCompatActivity {
    private TextView myProfile_TXT_mail, myProfile_TXT_age, myProfile_TXT_name, myProfile_TXT_city, myProfile_TXT_height, myProfile_TXT_gender,
            login_EDT_interestingInGender;
    private Button myProfile_BTN_settings;
    private ImageView myProfile_IMG_profileImage;
    private StorageReference storageReference;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        findViews();
        getSupportActionBar().hide();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        User user = returnUserFromMail(fUser.getEmail());
        if (user != null) {
            myProfile_TXT_mail.setText(user.getEmail());
            myProfile_TXT_age.setText(user.getAge());
            myProfile_TXT_name.setText((user.getFirstName() + " " + user.getLastName()));
            myProfile_TXT_city.setText(user.getCity());
            myProfile_TXT_height.setText(user.getHeight());
            if (user.getPersonGender().equals(User.Gender.FEMALE))
                myProfile_TXT_gender.setText(R.string.female);
            else
                myProfile_TXT_gender.setText(R.string.male);
            if (user.getPersonPreferenceGender().equals(User.Gender.FEMALE))
                login_EDT_interestingInGender.setText(R.string.female);
            else
                login_EDT_interestingInGender.setText(R.string.male);


            storageReference.child(user.getEmail()).child("profile").getDownloadUrl()
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            user.setMainImage(uri);
                            Picasso.get().load(uri).into(myProfile_IMG_profileImage);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }

        myProfile_BTN_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadPhotos();

            }
        });
    }

    private void findViews() {
        myProfile_TXT_mail = findViewById(R.id.myProfile_TXT_mail);
        myProfile_TXT_age = findViewById(R.id.myProfile_TXT_age);
        myProfile_TXT_name = findViewById(R.id.myProfile_TXT_name);
        myProfile_TXT_city = findViewById(R.id.myProfile_TXT_address);
        myProfile_IMG_profileImage = findViewById(R.id.myProfile_IMG_profileImage);
        myProfile_TXT_height = findViewById(R.id.myProfile_TXT_height);
        myProfile_TXT_gender = findViewById(R.id.myProfile_TXT_gender);
        login_EDT_interestingInGender = findViewById(R.id.login_EDT_interestingInGender);
        myProfile_BTN_settings = findViewById(R.id.myProfile_BTN_settings);

    }

    private User returnUserFromMail(String currentUserEmail) {
        if (MainActivity.allClients != null) {
            for (User userTemp : MainActivity.allClients.allClientsInDB) {
                if (userTemp.getEmail().equals(currentUserEmail))
                    return userTemp;
            }
        }
        return null;
    }

    private void downloadPhotos() {
        new PhotosController().fetchAllPhotos(new PhotosController.CallBack_Photos() {
            @Override
            public void photos(Photos photos) {
                if (photos != null) {
                    ArrayList<String> strings = new ArrayList<>();
                    for (int i = 0; i < photos.getUrls().length; i++) {
                        strings.add(photos.getUrls()[i]);
                    }
                    MainActivityLibrary.initImages(MyProfileActivity.this);
                    MainActivityLibrary.changeTitle("choose your background photo");
                    MainActivityLibrary.changeButtonText("Back");
                    MainActivityLibrary.numberOfImageInRow(1, 570, 570);
                    MainActivityLibrary.openAlbum(MyProfileActivity.this, strings, new MainActivityLibrary.OnImageClickedCallBack() {
                        @Override
                        public void onImageClicked(ImageView image, String imageUrl) {
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("backgroundImageUrl", imageUrl).apply();
                        }
                    });
                } else
                    Log.d("pttt", "Photos empty");
            }
        });
    }
}
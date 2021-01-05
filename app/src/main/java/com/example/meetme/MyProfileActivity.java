package com.example.meetme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meetme.Entity.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MyProfileActivity extends AppCompatActivity {
    private TextView myProfile_TXT_mail, myProfile_TXT_age, myProfile_TXT_name, myProfile_TXT_city,myProfile_TXT_height,myProfile_TXT_gender,
            login_EDT_interestingInGender;
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
        myProfile_TXT_mail.setText(user.getEmail());
        myProfile_TXT_age.setText(user.getAge());
        myProfile_TXT_name.setText(user.getFirstName());
        myProfile_TXT_city.setText(user.getCity());
        myProfile_TXT_height.setText(user.getHeight());
        myProfile_TXT_gender.setText(user.getPersonGender().toString());
        login_EDT_interestingInGender.setText(user.getPersonPreferenceGender().toString());

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

    private void findViews() {
        myProfile_TXT_mail = findViewById(R.id.myProfile_TXT_mail);
        myProfile_TXT_age = findViewById(R.id.myProfile_TXT_age);
        myProfile_TXT_name = findViewById(R.id.myProfile_TXT_name);
        myProfile_TXT_city = findViewById(R.id.myProfile_TXT_address);
        myProfile_IMG_profileImage = findViewById(R.id.myProfile_IMG_profileImage);
        myProfile_TXT_height = findViewById(R.id.myProfile_TXT_height);
        myProfile_TXT_gender = findViewById(R.id.myProfile_TXT_gender);
        login_EDT_interestingInGender = findViewById(R.id.login_EDT_interestingInGender);

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
}
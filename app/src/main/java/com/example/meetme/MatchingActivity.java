package com.example.meetme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetme.Fragment.Fragment_signUp_third;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MatchingActivity extends AppCompatActivity {
    private RecyclerView matching_LST_news;
    private CircleImageView profileImage;
    private Button matchine_BTN_logout;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);
        getSupportActionBar().hide();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        matching_LST_news = findViewById(R.id.matching_LST_news);
        matchine_BTN_logout = findViewById(R.id.matching_BTN_logout);
        profileImage = findViewById(R.id.matching_profileImage);

        matchine_BTN_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mFireBaseAuth.signOut();
                Intent intent = new Intent(MatchingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if (MainActivity.allClients.getAllClientsInDB() != null)
            users = MainActivity.allClients.getAllClientsInDB();
        else
            Toast.makeText(MatchingActivity.this, "אין נתונים", Toast.LENGTH_SHORT).show();

        ArrayList<User> allUsers = new ArrayList<>();
//        User user = new User();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        User user = returnUserFromMail(currentUser.getEmail());
        getImageFromStorage(this.profileImage,user);
        if (user != null) {
            allUsers = checkUsers(user);
            Adapter_User adapter_article = new Adapter_User(this, allUsers);
            matching_LST_news.setLayoutManager(new LinearLayoutManager(this));
            matching_LST_news.setItemAnimator(new DefaultItemAnimator());
            matching_LST_news.setAdapter(adapter_article);
        }
    }

    private void getImageFromStorage(ImageView image, User user) {
        storageReference.child(user.getEmail()).child("profile").getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        user.setMainImage(uri);
                        Picasso.with(MatchingActivity.this).load(uri).into(image);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private User returnUserFromMail(String currentUserEmail) {
        for (User userTemp : MainActivity.allClients.allClientsInDB) {
            if (userTemp.getEmail().equals(currentUserEmail))
                return userTemp;
        }
        return null;
    }

    private ArrayList<User> checkUsers(User user) {
        ArrayList<User> allUsers = new ArrayList<>();
        for (User userTemp : MainActivity.allClients.allClientsInDB) {
            if (userTemp.getPersonGender().equals(user.getPersonPreferenceGender()) &&
                    userTemp.getHeight().equals(user.getPreferenceHeight()) &&
                    user.getPersonGender().equals(userTemp.getPersonPreferenceGender()) &&
                    user.getHeight().equals(userTemp.getPreferenceHeight()))
                allUsers.add(userTemp);
        }
        return allUsers;
    }
}
package com.example.meetme;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity1 extends AppCompatActivity {

    CircleImageView profileImage;
    TextView userName;
    User currentUser;
    String chatUserName;
    String chatUserId;
    Uri chatUserImage;
    String chatUserEmail;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        profileImage = findViewById(R.id.chat_profileImage);
        userName = findViewById(R.id.chat_userName);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.currentUser = getCurrentUser(firebaseUser.getEmail());

        chatUserName = getIntent().getStringExtra("name");
        chatUserId = getIntent().getStringExtra("id");
        chatUserImage = Uri.parse(getIntent().getStringExtra("image"));
        chatUserEmail = getIntent().getStringExtra("email");

        Picasso.with(ChatActivity1.this).load(chatUserImage).into(this.profileImage);
        this.userName.setText(chatUserName);
    }

    private User getCurrentUser(String email) {
        for (User user : MainActivity.allClients.allClientsInDB) {
            if (user.getEmail().equals(firebaseUser.getEmail())){
                return user;
            }
        }
        return  null;
    }
}
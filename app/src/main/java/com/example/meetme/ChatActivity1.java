package com.example.meetme;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity1 extends AppCompatActivity {

    CircleImageView profileImage;
    Toolbar toolbar;
    User currentUser;
    String chatUserName;
    String chatUserId;
    Uri chatUserImage;
    String chatUserEmail;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    FirebaseUser chatUser;

    Intent intent;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();

        profileImage = findViewById(R.id.chat_profileImage);
        toolbar = findViewById(R.id.chat_Toolbar);
        setActionBar(toolbar);

        intent = getIntent();
        chatUserName = intent.getStringExtra("name");
        chatUserId = intent.getStringExtra("id");
        chatUserImage = Uri.parse(getIntent().getStringExtra("image"));
        chatUserEmail = intent.getStringExtra("email");

        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setTitle(chatUserName);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Picasso.get().load(chatUserImage).into(this.profileImage);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.currentUser = getCurrentUser(firebaseUser.getEmail());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ChatActivity1.this,MainActivity.class));
                finish();
                return true;
        }
        return false;
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
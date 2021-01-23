package com.example.meetme;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetme.Adapter.MessageAdapter;
import com.example.meetme.Entity.Chat;
import com.example.meetme.Entity.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    private CircleImageView profileImage;
    private Toolbar toolbar;
    private EditText editText;
    private ImageButton sendBtn;

    private MessageAdapter messageAdapter;
    private List<Chat> mChat;

    private RecyclerView allMessages;

    private User currentUser;
    private String chatUserName;
    private String chatUserId;
    private Uri chatUserImage;
    private String chatUserEmail;
    private FirebaseUser firebaseUser;
    private ImageView imageViewBackground;
    Intent intent;

    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();

        imageViewBackground = findViewById(R.id.chat_background);
        profileImage = findViewById(R.id.chat_profileImage);
        allMessages = findViewById(R.id.chat_messages);
        allMessages.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        allMessages.setLayoutManager(linearLayoutManager);

        toolbar = findViewById(R.id.chat_Toolbar);
        sendBtn = findViewById(R.id.chat_btn_send);
        editText = findViewById(R.id.chat_text_send);
        setActionBar(toolbar);

        intent = getIntent();
        chatUserName = intent.getStringExtra("name");
        chatUserId = intent.getStringExtra("id");
        chatUserImage = Uri.parse(getIntent().getStringExtra("image"));
        chatUserEmail = intent.getStringExtra("email");
        setBackgroundColor();
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

        readMessages(firebaseUser.getUid(), chatUserId, chatUserImage);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = editText.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(firebaseUser.getUid(), chatUserId, msg);
                    hideKeyboard();
                } else {
                    Toast.makeText(ChatActivity.this, "לא ניתן לשלוח הודעה ריקה", Toast.LENGTH_SHORT);
                }
                editText.setText("");
            }
        });
    }

    private void setBackgroundColor() {
        String bgUrl = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("backgroundImageUrl", "");
        if (!bgUrl.equals("")) {
            Picasso.get().load(bgUrl).into(imageViewBackground);
        }
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) ChatActivity.this.getSystemService(ChatActivity.this.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private User getCurrentUser(String email) {
        for (User user : MainActivity.allClients.allClientsInDB) {
            if (user.getEmail().equals(firebaseUser.getEmail())) {
                return user;
            }
        }
        return null;
    }

    private void sendMessage(String sender, String receiver, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        reference.child("Chats").push().setValue(hashMap);
    }

    private void readMessages(String myid, String chatUserId, Uri ImageUrl) {
        mChat = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mChat.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Chat chat = snap.getValue(Chat.class);
                    if ((chat.getReceiver().equals(myid) && chat.getSender().equals(chatUserId)) ||
                            (chat.getReceiver().equals(chatUserId) && chat.getSender().equals(myid))) {
                        mChat.add(chat);
                    }

                    messageAdapter = new MessageAdapter(ChatActivity.this, mChat, ImageUrl);
                    allMessages.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
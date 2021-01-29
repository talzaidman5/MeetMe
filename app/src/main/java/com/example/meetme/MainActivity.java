package com.example.meetme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.meetme.utils.Photos;
import com.example.meetme.utils.PhotosController;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    public static FirebaseAuth mFireBaseAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser firebaseUser;
    private Button activity_main_BTN_login, activity_main_BTN_signUp,activity_main_BTN_about;
    public static AllClients allClients= new AllClients();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initFirebase();
        readFromDB();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        activity_main_BTN_login = findViewById(R.id.activity_main_BTN_login);
        activity_main_BTN_signUp= findViewById(R.id.activity_main_BTN_signUp);
        activity_main_BTN_about= findViewById(R.id.activity_main_BTN_about);
        activity_main_BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userExists();
            }
        });
        activity_main_BTN_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initFirebase() {
        mFireBaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
    }

    private void readFromDB() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                allClients = dataSnapshot.getValue(AllClients.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    private void userExists() {
        if (firebaseUser == null) {
            LoginActivity loginActivity = new LoginActivity();
            loginActivity.show(getSupportFragmentManager(),"exe");
        } else{
            Toast.makeText(getApplicationContext(),
                    "welcome back!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, MatchingActivity.class);
            startActivity(intent);
        }
    }


}
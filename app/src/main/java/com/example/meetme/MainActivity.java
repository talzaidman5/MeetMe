package com.example.meetme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    public static FirebaseAuth mFireBaseAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;

    private Button activity_main_BTN_login, activity_main_BTN_signUp,activity_main_BTN_about;
    public static AllClients allClients= new AllClients();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initFirebase();
        readFromDB();

        activity_main_BTN_login = findViewById(R.id.activity_main_BTN_login);
        activity_main_BTN_signUp= findViewById(R.id.activity_main_BTN_signUp);
        activity_main_BTN_about= findViewById(R.id.activity_main_BTN_about);

        activity_main_BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFromDB();
                LoginActivity loginActivity = new LoginActivity();
                loginActivity.show(getSupportFragmentManager(),"exe");
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
        myRef = database.getReference("message");
    }

    private void readFromDB() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allClients = dataSnapshot.getValue(AllClients.class);

            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
}
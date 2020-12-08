package com.example.meetme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MatchingActivity extends AppCompatActivity {
    private RecyclerView main_LST_news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);
        getSupportActionBar().hide();

        main_LST_news = findViewById(R.id.main_LST_news);

        ArrayList<User> users = MainActivity.allClients.getAllClientsInDB();
        Adapter_User adapter_article = new Adapter_User(this, users);
        main_LST_news.setLayoutManager(new LinearLayoutManager(this));
        main_LST_news.setItemAnimator(new DefaultItemAnimator());
        main_LST_news.setAdapter(adapter_article);

    }
}
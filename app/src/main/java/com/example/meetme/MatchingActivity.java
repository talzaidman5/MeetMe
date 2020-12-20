package com.example.meetme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.meetme.Fragment.Fragment_signUp_third;

import java.util.ArrayList;

public class MatchingActivity extends AppCompatActivity {
    private RecyclerView main_LST_news;
    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);
        getSupportActionBar().hide();

        main_LST_news = findViewById(R.id.main_LST_news);

        if (MainActivity.allClients.getAllClientsInDB() != null)
            users = MainActivity.allClients.getAllClientsInDB();
        else
            Toast.makeText(MatchingActivity.this, "אין נתונים", Toast.LENGTH_SHORT).show();
        ArrayList<User> allUsers = new ArrayList<>();
        User user = new User();
        user = returnUserFromMail(LoginActivity.currentUserEmail);
        if (user != null) {
            allUsers = checkUsers(user);
            Adapter_User adapter_article = new Adapter_User(this, allUsers);
            main_LST_news.setLayoutManager(new LinearLayoutManager(this));
            main_LST_news.setItemAnimator(new DefaultItemAnimator());
            main_LST_news.setAdapter(adapter_article);
        }
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
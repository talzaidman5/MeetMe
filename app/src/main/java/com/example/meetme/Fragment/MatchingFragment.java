package com.example.meetme.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meetme.Adapter.Adapter_User;
import com.example.meetme.MainActivity;
import com.example.meetme.R;
import com.example.meetme.Entity.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MatchingFragment extends Fragment {
    private RecyclerView matching_LST_news;
    private Adapter_User adapterUser;
    private ArrayList<User> allUsers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matching, container, false);
        // Inflate the layout for this fragment
        matching_LST_news = view.findViewById(R.id.fragmentMatching_LST_news);
//        matching_LST_news.setHasFixedSize(true);
        matching_LST_news.setLayoutManager(new LinearLayoutManager(getContext()));
        allUsers = new ArrayList<>();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        User user = returnUserFromMail(currentUser.getEmail());

        if (user != null) {
            allUsers = readAdaptUsers(user);
            adapterUser = new Adapter_User(getContext(), allUsers);
            matching_LST_news.setItemAnimator(new DefaultItemAnimator());
            matching_LST_news.setAdapter(adapterUser);
        }
        return view;
    }

    private User returnUserFromMail(String currentUserEmail) {
        if(MainActivity.allClients!=null) {
            for (User userTemp : MainActivity.allClients.getAllClientsInDB()) {
                if (userTemp.getEmail().equals(currentUserEmail))
                    return userTemp;
            }
        }
        return null;
    }

    private ArrayList<User> readAdaptUsers(User user) {
        ArrayList<User> allUsers = new ArrayList<>();
        for (User userTemp : MainActivity.allClients.getAllClientsInDB()) {
            if (userTemp.getPersonGender().equals(user.getPersonPreferenceGender()) &&
                    userTemp.getHeight().equals(user.getPreferenceHeight()) &&
                    user.getPersonGender().equals(userTemp.getPersonPreferenceGender()) &&
                    user.getHeight().equals(userTemp.getPreferenceHeight()))
                allUsers.add(userTemp);
        }
        return allUsers;
    }
}
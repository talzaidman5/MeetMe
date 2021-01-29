package com.example.meetme.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.meetme.Adapter.Adapter_User;
import com.example.meetme.Adapter.UserChat_Adapter;
import com.example.meetme.AllClients;
import com.example.meetme.Entity.Chat;
import com.example.meetme.Entity.User;
import com.example.meetme.MainActivity;
import com.example.meetme.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {
    private RecyclerView chats;

    private UserChat_Adapter adapterUser;
    private List<User> mUsers;
    public static int numOfChats;
    private TextView fragmentChat_TXT;
    FirebaseUser fuser;
    DatabaseReference reference;

    private List<String> usersList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        chats = view.findViewById(R.id.fragmentChat_chats);
        fragmentChat_TXT = view.findViewById(R.id.fragmentChat_TXT);
        chats.setHasFixedSize(true);
        chats.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        usersList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    Chat chat = snap.getValue(Chat.class);
                    if(chat.getSender().equals(fuser.getUid()))
                        usersList.add(chat.getReceiver());
                    else if(chat.getReceiver().equals(fuser.getUid()))
                        usersList.add(chat.getSender());
                }
                numOfChats = usersList.size();
                readChats();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    private void readChats() {
        mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users").child("allClientsInDB");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for (User user : MainActivity.allClients.getAllClientsInDB()) {
                    for(String id:usersList) {
                        if (user.getId().equals(id)){
                            if (mUsers.size() != 0){
                                for (User user1:mUsers){
                                    if (!user.getId().equals(user1.getId()) && !mUsers.contains(user)){
                                        mUsers.add(user);
                                    }
                                }
                            } else{
                                mUsers.add(user);
                            }
                        }
                    }
                }
                if(mUsers.size()!=0) {
                    adapterUser = new UserChat_Adapter(getContext(), mUsers);
                    chats.setAdapter(adapterUser);
                    fragmentChat_TXT.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
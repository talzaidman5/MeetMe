package com.example.meetme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.meetme.Entity.User;
import com.example.meetme.Fragment.ChatFragment;
import com.example.meetme.Fragment.MatchingFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MatchingActivity extends AppCompatActivity {
    private CircleImageView profileImage;
    private FirebaseStorage storage;
    private Toolbar toolbar;
    private StorageReference storageReference;
    private ArrayList<User> users;
    private Button logout;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);
        getSupportActionBar().hide();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        profileImage = findViewById(R.id.matching_profileImage);
        toolbar = findViewById(R.id.matching_Toolbar);
        setActionBar(toolbar);

        if (MainActivity.allClients != null)
            users = MainActivity.allClients.getAllClientsInDB();
        else
            Toast.makeText(MatchingActivity.this, "אין נתונים", Toast.LENGTH_SHORT).show();

        TabLayout tabLayout = findViewById(R.id.matching_tabLayout);
        ViewPager viewPager = findViewById(R.id.matching_viewPager);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MatchingActivity.this,MainActivity.class));
                finish();

            }
        });
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new MatchingFragment(), "התאמות");
        viewPagerAdapter.addFragment(new ChatFragment(),"שיחות");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray1)));
        tabLayout.setupWithViewPager(viewPager);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        User user = returnUserFromMail(currentUser.getEmail());
        getImageFromStorage(this.profileImage,user);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setTitle("ההתאמות שלך");
        getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray2)));
        getActionBar().setDisplayHomeAsUpEnabled(false);
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
                startActivity(new Intent(MatchingActivity.this,MainActivity.class));
                finish();
                return true;
        }
        return false;
    }

    private void getImageFromStorage(ImageView image, User user) {
        if (user != null){
            storageReference.child(user.getEmail()).child("profile").getDownloadUrl()
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            user.setMainImage(uri);
                            Picasso.get().load(uri).into(image);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }

    private User returnUserFromMail(String currentUserEmail) {
        if(MainActivity.allClients!=null) {
            for (User userTemp : MainActivity.allClients.allClientsInDB) {
                if (userTemp.getEmail().equals(currentUserEmail))
                    return userTemp;
            }
        }
        return null;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm){
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment frag, String title){
            fragments.add(frag);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position){
            return titles.get(position);
        }
    }
}
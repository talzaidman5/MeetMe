package com.example.meetme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.meetme.Fragment.Fragment_signUp_first;
import com.example.meetme.Fragment.Fragment_signUp_second;


public class SignUpActivity extends AppCompatActivity {
    public static boolean isDone=false;

    public enum Hobbies {
        ARTIST,YOGA,BICYCLE, JOYSTICK, BAKE, CAMERA,GUITAR;
    }
    private FrameLayout main_signUp_fragment;
    private Fragment_signUp_first fragmentSignUpFirst;
    private Fragment_signUp_second fragment_signUp_second;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sign_up);
        getSupportActionBar().hide();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        if(isDone){
            fragment_signUp_second = new Fragment_signUp_second();
            getSupportFragmentManager().beginTransaction().add(R.id.main_signUp_fragment, fragmentSignUpFirst).commit();
        }
        else {
            fragmentSignUpFirst = new Fragment_signUp_first();
            getSupportFragmentManager().beginTransaction().add(R.id.main_signUp_fragment, fragmentSignUpFirst).commit();
        }
    }

}
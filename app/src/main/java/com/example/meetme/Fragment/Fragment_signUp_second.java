package com.example.meetme.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.meetme.R;
import com.example.meetme.SignUpActivity;
import com.example.meetme.Entity.User;
import com.google.android.material.slider.Slider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Fragment_signUp_second extends Fragment {

    private ImageButton signUp_BTN_artist, signUp_BTN_yoga, signUp_BTN_bicycle, signUp_BTN_joystick, signUp_BTN_bake, signUp_BTN_camera, signUp_BTN_guitar;
    private ArrayList<SignUpActivity.Hobbies> hobbiesToUser = new ArrayList<>();
    private Spinner signUp_LSV_minAge, signUp_LSV_maxAge;
    private CheckBox InterestingInFemale, InterestingInMale;
    private Button signUp_BTN_continue;
    private EditText editTextInterestingInHeight;
    private boolean checkDetails = false;
    private Boolean statusMale = false, statusFemale = false;
    private float distance;
    private TextView distance2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_second, container, false);
        findViews(view);

        initSpinner();
        signUp_BTN_artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signUp_BTN_artist.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.artist).getConstantState())) {
                    signUp_BTN_artist.setBackgroundResource(R.drawable.artist_color);
                    hobbiesToUser.add(SignUpActivity.Hobbies.ARTIST);
                } else
                    signUp_BTN_artist.setBackgroundResource(R.drawable.artist);
            }
        });
        signUp_BTN_bake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signUp_BTN_bake.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bake).getConstantState())) {
                    signUp_BTN_bake.setBackgroundResource(R.drawable.bake_color);
                    hobbiesToUser.add(SignUpActivity.Hobbies.BAKE);
                } else
                    signUp_BTN_bake.setBackgroundResource(R.drawable.bake);
            }
        });
        signUp_BTN_yoga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signUp_BTN_yoga.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.yoga).getConstantState())) {
                    signUp_BTN_yoga.setBackgroundResource(R.drawable.yoga_color);
                    hobbiesToUser.add(SignUpActivity.Hobbies.YOGA);
                } else
                    signUp_BTN_yoga.setBackgroundResource(R.drawable.yoga);
            }
        });
        signUp_BTN_bicycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signUp_BTN_bicycle.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bicycle).getConstantState())) {
                    signUp_BTN_bicycle.setBackgroundResource(R.drawable.bicycle_color);
                    hobbiesToUser.add(SignUpActivity.Hobbies.BICYCLE);
                } else
                    signUp_BTN_bicycle.setBackgroundResource(R.drawable.bicycle);
            }
        });
        signUp_BTN_joystick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signUp_BTN_joystick.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.joystick).getConstantState())) {
                    signUp_BTN_joystick.setBackgroundResource(R.drawable.joystick_color);
                    hobbiesToUser.add(SignUpActivity.Hobbies.JOYSTICK);
                } else
                    signUp_BTN_joystick.setBackgroundResource(R.drawable.joystick);
            }
        });
        signUp_BTN_guitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signUp_BTN_guitar.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.guitar).getConstantState())) {
                    signUp_BTN_guitar.setBackgroundResource(R.drawable.guitar_color);
                    hobbiesToUser.add(SignUpActivity.Hobbies.GUITAR);
                } else
                    signUp_BTN_guitar.setBackgroundResource(R.drawable.guitar);
            }
        });
        signUp_BTN_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signUp_BTN_camera.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.camera).getConstantState())) {
                    signUp_BTN_camera.setBackgroundResource(R.drawable.camera_color);
                    hobbiesToUser.add(SignUpActivity.Hobbies.CAMERA);
                } else
                    signUp_BTN_camera.setBackgroundResource(R.drawable.camera);
            }
        });
        InterestingInFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!statusMale) {
                    InterestingInMale.setEnabled(false);
                    statusMale = true;
                } else {
                    InterestingInMale.setEnabled(true);
                    InterestingInFemale.setEnabled(true);
                    statusMale = false;
                }
            }
        });
        InterestingInMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!statusFemale) {
                    InterestingInMale.setEnabled(true);
                    InterestingInFemale.setEnabled(false);
                    statusFemale = true;
                } else {
                    InterestingInFemale.setEnabled(true);
                    InterestingInMale.setEnabled(true);
                    statusFemale = false;
                }
            }
        });
        signUp_BTN_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAllDetails()) {
                    Fragment_signUp_first.user.setPersonPreferenceGender(checkInterestingIn());
                    Fragment_signUp_first.user.setHobbies(hobbiesToUser);
                    Fragment_signUp_first.user.setPreferenceHeight(editTextInterestingInHeight.getText().toString());
                    Fragment_signUp_first.user.setMinAge(Integer.parseInt(signUp_LSV_minAge.getSelectedItem().toString()));
                    Fragment_signUp_first.user.setMaxAge(Integer.parseInt(signUp_LSV_maxAge.getSelectedItem().toString()));
                    Fragment_signUp_first.user.setDistance(distance);

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

                    Fragment fragment = new Fragment_signUp_third();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    ft.replace(R.id.main_signUp_fragment, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });
        return view;
    }

    private void findViews(View view) {
        signUp_BTN_continue = view.findViewById(R.id.signUp_BTN_end);
        signUp_BTN_artist = view.findViewById(R.id.signUp_BTN_artist);
        signUp_BTN_yoga = view.findViewById(R.id.signUp_BTN_yoga);
        signUp_BTN_bicycle = view.findViewById(R.id.signUp_BTN_bicycle);
        signUp_BTN_joystick = view.findViewById(R.id.signUp_BTN_joystick);
        signUp_BTN_bake = view.findViewById(R.id.signUp_BTN_bake);
        signUp_BTN_camera = view.findViewById(R.id.signUp_BTN_camera);
        signUp_BTN_guitar = view.findViewById(R.id.signUp_BTN_guitar);
        signUp_LSV_minAge = view.findViewById(R.id.signUp_LSV_minAge);
        signUp_LSV_maxAge = view.findViewById(R.id.signUp_LSV_maxAge);
        InterestingInFemale = view.findViewById(R.id.InterestingInFemale);
        InterestingInMale = view.findViewById(R.id.InterestingInMen);
        editTextInterestingInHeight = view.findViewById(R.id.editTextInterestingInHeight);
    }

    private void initSpinner() {
        ArrayAdapter<CharSequence> adapterMinAge = ArrayAdapter.createFromResource(getContext(), R.array.minAge, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterMaxAge = ArrayAdapter.createFromResource(getContext(), R.array.maxAge, android.R.layout.simple_spinner_item);
        adapterMinAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signUp_LSV_minAge.setAdapter(adapterMinAge);

        adapterMaxAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signUp_LSV_maxAge.setAdapter(adapterMaxAge);

    }

    private User.Gender checkInterestingIn() {
        if (InterestingInFemale.isChecked())
            return User.Gender.FEMALE;
        return User.Gender.MALE;
    }

    private boolean checkAllDetails() {
         checkDetails = true;
        if(signUp_LSV_minAge.getSelectedItem().equals("מינימלי")){
            checkDetails = false;
        }
        if(signUp_LSV_minAge.getSelectedItem().equals("מקסימלי")){
            checkDetails = false;
        }
//        if (editTextDistance.getText().toString().matches("")) {
//            editTextDistance.setError("אנא הקלד מרחק");
//            checkDetails = false;
//        } else {
//            if (!checkIsIfNumber(editTextDistance.getText().toString())) {
//                checkDetails = false;
//                editTextDistance.setError("הקלד רק מספרים");
//            }
//        }
        if (editTextInterestingInHeight.getText().toString().matches("")) {
            editTextInterestingInHeight.setError("אנא הקלד מרחק");
            checkDetails = false;
        }
        if(!checkIsIfNumber(editTextInterestingInHeight.getText().toString())) {
            editTextInterestingInHeight.setError("אנא הקלד רק מספרים");
            checkDetails = false;
        }
        return checkDetails;
    }
    public boolean checkIsIfNumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!(str.charAt(i) >= '0' && str.charAt(i) <= '9'))
                return false;
        }
        return true;
    }

}
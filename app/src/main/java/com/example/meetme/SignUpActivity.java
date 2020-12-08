package com.example.meetme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {
    private Spinner signUp_LSV_minAge,signUp_LSV_maxAge;
    private Button signUp_BTN_createUser;
    private EditText editTextEmail,editTextPassword,editTextName,editTextAge,editTextCity,editTextDistance;
    private CheckBox signUp_female,signUp_men,InterestingInFemale,InterestingInMen;
    private ImageButton signUp_BTN_artist,signUp_BTN_yoga,signUp_BTN_bicycle, signUp_BTN_joystick,signUp_BTN_bake,signUp_BTN_camera,signUp_BTN_guitar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();
        findView();
        initSpinner();
        signUp_BTN_artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(signUp_BTN_artist.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.artist).getConstantState()))
                    signUp_BTN_artist.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.artist_color));
                else
                    signUp_BTN_artist.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.artist));
            }
        });
        signUp_BTN_bake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(signUp_BTN_bake.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bake).getConstantState()))
                    signUp_BTN_bake.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bake_color));
                else
                    signUp_BTN_bake.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bake));

            }
        });
        signUp_BTN_yoga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(signUp_BTN_yoga.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.yoga).getConstantState()))
                    signUp_BTN_yoga.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.yoga_color));
                else
                    signUp_BTN_yoga.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.yoga));
            }
        });
        signUp_BTN_bicycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(signUp_BTN_bicycle.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.bicycle).getConstantState()))
                    signUp_BTN_bicycle.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bicycle_color));
                else
                    signUp_BTN_bicycle.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bicycle));
            }
        });
        signUp_BTN_joystick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(signUp_BTN_joystick.getBackground().equals(getResources().getDrawable(R.drawable.joystick)))
                    signUp_BTN_joystick.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.joystick_color));
                else
                    signUp_BTN_joystick.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.joystick));
            }
        });
        signUp_BTN_guitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(signUp_BTN_guitar.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.guitar).getConstantState()))
                    signUp_BTN_guitar.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.guitar_color));
                else
                    signUp_BTN_guitar.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.guitar));
            }
        });
        signUp_BTN_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(signUp_BTN_camera.getBackground().getConstantState().equals(getResources().getDrawable(R.drawable.camera).getConstantState()))
                    signUp_BTN_camera.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.camera_color));
                else
                    signUp_BTN_camera.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.camera));
            }
        });



        signUp_BTN_createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int d = Integer.parseInt(editTextAge.getText().toString());
                User userTemp = new User(editTextName.getText().toString(),Integer.parseInt(editTextAge.getText().toString()),checkGender(),editTextCity.getText().toString(),
                        checkHobbies(),(int) signUp_LSV_minAge.getSelectedItem(), (int) signUp_LSV_maxAge.getSelectedItem(),checkInterestingIn());
                MainActivity.allClients.addUser(userTemp);
                myRef.setValue(MainActivity.allClients);
            }
        });



    }

    private User.Gender checkGender() {
        if(signUp_female.isSelected())
            return User.Gender.FEMALE;
            else
                return User.Gender.MALE;
    }

    private ArrayList<String> checkHobbies() {
        ArrayList<Drawable>allHobbiesTemp=new ArrayList<>();
        ArrayList<String>allHobbies=new ArrayList<>();

        allHobbiesTemp.add(signUp_BTN_artist.getBackground());
        allHobbiesTemp.add(signUp_BTN_yoga.getBackground());
        allHobbiesTemp.add(signUp_BTN_bicycle.getBackground());
        allHobbiesTemp.add(signUp_BTN_joystick.getBackground());
        allHobbiesTemp.add(signUp_BTN_bake.getBackground());
        allHobbiesTemp.add(signUp_BTN_camera.getBackground());
        allHobbiesTemp.add(signUp_BTN_guitar.getBackground());
        for (Drawable temp:allHobbiesTemp) {
            if(temp.getConstantState().toString().contains("_color")) {
                String[] t = temp.getConstantState().toString().split("_");
                allHobbies.add(t[0]);
            }
        }
        return allHobbies;
    }

    private User.Gender checkInterestingIn() {
        if(InterestingInFemale.isSelected())
            return User.Gender.FEMALE;
        else
            return User.Gender.MALE;
    }

    private void initSpinner() {
        ArrayAdapter<CharSequence> adapterMinAge = ArrayAdapter.createFromResource(this, R.array.minAge, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterMaxAge = ArrayAdapter.createFromResource(this, R.array.maxAge, android.R.layout.simple_spinner_item);
        adapterMinAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signUp_LSV_minAge.setAdapter(adapterMinAge);
        adapterMaxAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signUp_LSV_maxAge.setAdapter(adapterMaxAge);
    }

    private void findView() {
        signUp_LSV_minAge = findViewById(R.id.signUp_LSV_minAge);
        signUp_LSV_maxAge = findViewById(R.id.signUp_LSV_maxAge);
        signUp_BTN_createUser = findViewById(R.id.signUp_BTN_createUser);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        signUp_female = findViewById(R.id.signUp_female);
        signUp_men = findViewById(R.id.signUp_men);
        editTextAge = findViewById(R.id.editTextAge);
        editTextCity = findViewById(R.id.editTextCity);
        InterestingInFemale = findViewById(R.id.InterestingInFemale);
        InterestingInMen = findViewById(R.id.InterestingInMen);
        editTextDistance = findViewById(R.id.editTextDistance);
        signUp_BTN_artist = findViewById(R.id.signUp_BTN_artist);
        signUp_BTN_yoga = findViewById(R.id.signUp_BTN_yoga);
        signUp_BTN_bicycle  = findViewById(R.id.signUp_BTN_bicycle);
        signUp_BTN_joystick = findViewById(R.id.signUp_BTN_joystick);
        signUp_BTN_bake = findViewById(R.id.signUp_BTN_bake);
        signUp_BTN_camera = findViewById(R.id.signUp_BTN_camera);
        signUp_BTN_guitar = findViewById(R.id.signUp_BTN_guitar);

    }
}
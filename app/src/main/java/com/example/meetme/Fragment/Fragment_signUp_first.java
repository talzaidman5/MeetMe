package com.example.meetme.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.meetme.MainActivity;
import com.example.meetme.R;
import com.example.meetme.Entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Fragment_signUp_first extends Fragment {
    private Button signUp_BTN_continue;
    private EditText editTextEmail, editTextPassword, signUp_EDT_LastName,editTextFirstName, editTextAge, editTextCity, editTextHeight;
    private CheckBox signUp_female, signUp_men;       
    private LinearLayout signUp_LIN_gender;
    private Spinner signUp_LSV_Status;
    public static User user;
    private Boolean checkDetails = true;
    private Boolean statusMale = false, statusFemale = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_first, container, false);
        findView(view);
        ArrayAdapter<CharSequence> adapterStatus = ArrayAdapter.createFromResource(getContext(), R.array.status, android.R.layout.simple_spinner_item);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signUp_LSV_Status.setAdapter(adapterStatus);
        signUp_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!statusMale) {
                    signUp_men.setEnabled(false);
                    statusMale = true;
                } else {
                    signUp_men.setEnabled(true);
                    signUp_female.setEnabled(true);
                    statusMale = false;
                }
            }
        });
        signUp_men.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!statusFemale) {
                    signUp_men.setEnabled(true);
                    signUp_female.setEnabled(false);
                    statusFemale = true;
                } else {
                    signUp_female.setEnabled(true);
                    signUp_men.setEnabled(true);
                    statusFemale = false;
                }
            }
        });
        signUp_BTN_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        return view;
    }

    public void registerUser() {
        if (checkAllDetails()) {

            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            MainActivity.mFireBaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Fragment_signUp_first.this.getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String uid = MainActivity.mFireBaseAuth.getCurrentUser().getUid();
                                openNextSignUpPage(uid);
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(getContext(), "המייל שהזנת כבר קיים", Toast.LENGTH_LONG).show();
                                } else
                                    Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    private void openNextSignUpPage(String uid) {
        user = new User(uid, editTextFirstName.getText().toString(),  signUp_EDT_LastName.getText().toString(),
                editTextAge.getText().toString(),
                checkGender(), editTextCity.getText().toString()
                , null, 0, 0, null,
                editTextEmail.getText().toString(),
                editTextPassword.getText().toString(), 0,
                editTextHeight.getText().toString(), "0");
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

        Fragment fragment = new Fragment_signUp_second();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_signUp_fragment, fragment);
        ft.addToBackStack(null);
        ft.commit();

    }


    private boolean checkAllDetails() {
        checkDetails = true;
        if (editTextEmail.getText().toString().matches("")) {
            editTextEmail.setError("אנא הקלד כתובת מייל");
            checkDetails = false;
        }
      
        if (editTextPassword.getText().toString().matches("")) {
            editTextPassword.setError("אנא הקלד סיסמא");
            checkDetails = false;
        }
        if (editTextPassword.getText().toString().length() < 6) {
            editTextPassword.setError("אורך הסיסמא צריך להיות לפחות 6 תווים");
            checkDetails = false;
        }
        if (editTextAge.getText().toString().equals("")) {
            editTextAge.setError("אנא הקלד את גילך");
            checkDetails = false;
        }
        if (editTextHeight.getText().toString().equals("")) {
            editTextHeight.setError("אנא הקלד את גילך");
            checkDetails = false;
        }
        if (!checkIsIfNumber(editTextAge.getText().toString())) {
            editTextAge.setError("אנא הקלד את גילך במספרים בלבד");
            checkDetails = false;
        }
        if (editTextFirstName.getText().toString().matches("")) {
            editTextFirstName.setError("אנא הקלד שם");
            checkDetails = false;
        }
        if (!checkIsAlpha(editTextFirstName.getText().toString())) {
            checkDetails = false;
            editTextFirstName.setError("הקלד רק אותיות");
        }
        if (signUp_EDT_LastName.getText().toString().matches("")) {
            signUp_EDT_LastName.setError("אנא הקלד שם");
            checkDetails = false;
        }
        if (!checkIsAlpha(signUp_EDT_LastName.getText().toString())) {
            checkDetails = false;
            signUp_EDT_LastName.setError("הקלד רק אותיות");
        }

        if (editTextHeight.getText().toString().length() < 2) {
            editTextHeight.setError("אורך הגובה לפחות שתי אותיות");
            checkDetails = false;
        }
        if (!checkIsIfNumber(editTextHeight.getText().toString())) {
            editTextHeight.setError("אנא הקלד רק מספרים");
            checkDetails = false;
        }
        if (editTextCity.getText().toString().matches("")) {
            editTextCity.setError("אנא הקלד שם");
            checkDetails = false;
        } else {
            if (!checkIsAlpha(editTextCity.getText().toString())) {
                checkDetails = false;
                editTextCity.setError("הקלד רק אותיות");
            }
        }
        if (editTextCity.getText().toString().length() < 2) {
            editTextCity.setError("אורך שם העיר לפחות שתי אותיות");
            checkDetails = false;
        }

        return checkDetails;
    }

    public boolean checkIsAlpha(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!((str.charAt(i) >= 'a' && str.charAt(i) <= 'z') || (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z')))
                return false;
        }
        return true;
    }

    public boolean checkIsIfNumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!(str.charAt(i) >= '0' && str.charAt(i) <= '9'))
                return false;
        }
        return true;
    }

    private void findView(View view) {
        signUp_BTN_continue = view.findViewById(R.id.signUp_BTN_end);
        editTextEmail = view.findViewById(R.id.signUp_EDT_Email);
        editTextPassword = view.findViewById(R.id.login_EDT_Password);
        signUp_EDT_LastName = view.findViewById(R.id.signUp_EDT_LastName);
        editTextFirstName = view.findViewById(R.id.editTextFirstName);
        signUp_female = view.findViewById(R.id.signUp_female);
        signUp_men = view.findViewById(R.id.signUp_men);
        editTextAge = view.findViewById(R.id.editTextAge);
        editTextCity = view.findViewById(R.id.textInputCity);
        signUp_LSV_Status = view.findViewById(R.id.signUp_LSV_Status);
        signUp_LSV_Status = view.findViewById(R.id.signUp_LSV_Status);
        editTextHeight = view.findViewById(R.id.editTextHeight);
        signUp_LIN_gender = view.findViewById(R.id.signUp_LIN_gender);
    }

    private User.Gender checkGender() {
        if (signUp_female.isChecked())
            return User.Gender.FEMALE;
        else return User.Gender.MALE;
    }
}
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
    private EditText editTextEmail, editTextPassword, editTextName, editTextAge, editTextCity, editTextHeight;
    private CheckBox signUp_female, signUp_men;
    private LinearLayout signUp_LIN_gender;
    private Spinner signUp_LSV_Status;
    public static User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_first, container, false);
        findView(view);
        ArrayAdapter<CharSequence> adapterStatus = ArrayAdapter.createFromResource(getContext(), R.array.status, android.R.layout.simple_spinner_item);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signUp_LSV_Status.setAdapter(adapterStatus);

        signUp_BTN_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        return view;
    }

    public void registerUser() {
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

    private void openNextSignUpPage(String uid) {
        user = new User(uid,editTextName.getText().toString(),
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

    private void findView(View view) {
        signUp_BTN_continue = view.findViewById(R.id.signUp_BTN_end);
        editTextEmail = view.findViewById(R.id.signUp_EDT_Email);
        editTextPassword = view.findViewById(R.id.login_EDT_Password);
        editTextName = view.findViewById(R.id.editTextName);
        signUp_female = view.findViewById(R.id.signUp_female);
        signUp_men = view.findViewById(R.id.signUp_men);
        editTextAge = view.findViewById(R.id.editTextAge);
        editTextCity = view.findViewById(R.id.editTextCity);
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
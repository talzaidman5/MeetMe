package com.example.meetme.Fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.meetme.R;
import com.example.meetme.User;
import com.example.meetme.utils.MySheredP;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Date;

public class FragmentFirstSignUp extends Fragment {
    private Button signUp_BTN_continue;
    private EditText editTextEmail, editTextPassword, editTextName, editTextAge, editTextCity;
    private CheckBox signUp_female, signUp_men;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("message");
    private Fragment_signUp_second fragmentFirstSignUp;
    private Spinner signUp_LSV_Status;
    public static SharedPreferences sharedpreferences;
    private Gson gson = new Gson();
    private MySheredP msp;
    public static final String KEY_MSP = "user";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_first, container, false);
        findView(view);
        msp = new MySheredP(getContext());

        ArrayAdapter<CharSequence> adapterStatus = ArrayAdapter.createFromResource(getContext(), R.array.status, android.R.layout.simple_spinner_item);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signUp_LSV_Status.setAdapter(adapterStatus);

        signUp_BTN_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

                Fragment fragment = new Fragment_signUp_second();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ft.replace(R.id.main_signUp_fragment, fragment);
                ft.addToBackStack(null);
                ft.commit();
                Gson gson = new Gson();
                gson.toJson(editTextName.getText().toString() + editTextEmail.getText() + editTextAge.getText().toString() + checkGender().toString() + editTextCity.getText().toString()
                        + "tal@gmail.com" + editTextPassword.getText().toString() + signUp_LSV_Status.getSelectedItem());
                putOnMSP(gson);
            }
        });

        return view;
    }

    private void findView(View view) {
        signUp_BTN_continue = view.findViewById(R.id.signUp_BTN_end);
        editTextEmail = view.findViewById(R.id.login_EDT_Email);
        editTextPassword = view.findViewById(R.id.login_EDT_Password);
        editTextName = view.findViewById(R.id.editTextName);
        signUp_female = view.findViewById(R.id.signUp_female);
        signUp_men = view.findViewById(R.id.signUp_men);
        editTextAge = view.findViewById(R.id.editTextAge);
        editTextCity = view.findViewById(R.id.editTextCity);
        signUp_LSV_Status = view.findViewById(R.id.signUp_LSV_Status);
        signUp_LSV_Status = view.findViewById(R.id.signUp_LSV_Status);
    }

    private User.Gender checkGender() {
        if (signUp_female.isChecked())
            return User.Gender.FEMALE;
        else return User.Gender.MALE;
    }


    private String getFromMSP() {
        String data = msp.getString(KEY_MSP, "NA");
//        MainActivity.allClients = new AllClients(data);
        return data;
    }

    private void putOnMSP(Gson gson) {
        msp.putString(KEY_MSP, gson);
    }


}
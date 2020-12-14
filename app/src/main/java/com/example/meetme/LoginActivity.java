package com.example.meetme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatDialogFragment {
    private View view;
    private EditText login_EDT_Email,login_EDT_Password;
    private Button cirLoginButton;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.activity_login,null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        login_EDT_Email = view.findViewById(R.id.login_EDT_Email);
        login_EDT_Password = view.findViewById(R.id.login_EDT_Password);
        cirLoginButton = view.findViewById(R.id.cirLoginButton);
        cirLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkDetails()){
                    Intent intent = new Intent(LoginActivity.this.getContext(),MatchingActivity.class);
                    startActivity(intent);
                }
            }
        });

        return dialog;
    }

    private boolean checkDetails() {

        for (User tempUser:MainActivity.allClients.allClientsInDB) {
            if(tempUser!=null) {
                if (tempUser.getEmail().equals(login_EDT_Email.getText().toString()))
                    if (tempUser.getPassword().equals(login_EDT_Password.getText().toString()))
                        return true;
            }
        }
    return false;
    }
}
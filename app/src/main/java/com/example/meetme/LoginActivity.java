package com.example.meetme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatDialogFragment {
    private View view;
    private EditText login_EDT_Email, login_EDT_Password;
    private Button cirLoginButton ;
    private TextView viewForgotPassword,login_BTN_signUp;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Users");

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.activity_login, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();

        initViews();
        return dialog;
    }

    private void initViews() {
        this.login_EDT_Email = view.findViewById(R.id.signUp_EDT_Email);
        this.login_EDT_Password = view.findViewById(R.id.login_EDT_Password);
        this.cirLoginButton = view.findViewById(R.id.cirLoginButton);
        this.viewForgotPassword = (TextView) view.findViewById(R.id.viewForgotPassword);
        this.login_BTN_signUp = view.findViewById(R.id.login_BTN_signUp);

        this.cirLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                onLoginClicked();
            }
        });
        this.viewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                onForgotPasswordClicked();
            }
        });
        this.login_BTN_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                Intent intent = new Intent(view.getContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void hideKeyboard(){
        InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(view.getContext().INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void onForgotPasswordClicked() {
        String mail = login_EDT_Email.getText().toString().trim();
        if (mail.isEmpty()) {
            login_EDT_Email.setError("Invalid email");
            login_EDT_Email.requestFocus();
        } else {
            FirebaseAuth.getInstance().sendPasswordResetEmail(mail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "The password was sent to your email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "The email entered is incorrect", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void onLoginClicked() {
        String mail = login_EDT_Email.getText().toString().trim();
        String cus_password = login_EDT_Password.getText().toString();
        MainActivity.mFireBaseAuth
                .signInWithEmailAndPassword(mail, cus_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this.getContext(), MatchingActivity.class);
                            startActivity(intent);
                            dismiss();
                        } else {
                            Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
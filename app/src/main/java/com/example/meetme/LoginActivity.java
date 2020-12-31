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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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
    private Button cirLoginButton,login_BTN_signUp;
    private TextView viewForgotPassword;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

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
                userExists();
            }
        });
        this.viewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
        this.login_BTN_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void userExists() {
        String mail = login_EDT_Email.getText().toString().trim();
        String cus_password = login_EDT_Password.getText().toString();
        if (mail.isEmpty()) {
            login_EDT_Email.setError("Invalid email");
            login_EDT_Email.requestFocus();
        } else if (cus_password.isEmpty()) {
            login_EDT_Password.setError("Invalid password");
            login_EDT_Password.requestFocus();
        } else {
            MainActivity.mFireBaseAuth
                    .signInWithEmailAndPassword(mail, cus_password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this.getContext(), MatchingActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getContext(), "שגיאה באחד הנתונים!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    protected void sendEmail() {

        final Intent emailIntent1 = new Intent(Intent.ACTION_SEND);
        emailIntent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        emailIntent1.putExtra(Intent.EXTRA_EMAIL, new String[]{"talzaidman5@gmail.com"});
        emailIntent1.putExtra(Intent.EXTRA_SUBJECT, "[" + "iSchedule" + "] - " + " CSV File");
        emailIntent1.putExtra(Intent.EXTRA_TEXT, "talzaidman5@gamil.com" + "\n\n\nSent from iSchedule App");
        emailIntent1.putExtra(Intent.EXTRA_CC, new String[]{""});
        emailIntent1.setData(Uri.parse("mailto:")); // or just "mailto:" for blank
        emailIntent1.setType("text/html");

        getContext().startActivity(Intent.createChooser(emailIntent1, "Send email using"));
    }
}
package com.example.meetme.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.meetme.AllClients;
import com.example.meetme.MainActivity;
import com.example.meetme.MatchingActivity;
import com.example.meetme.R;
import com.example.meetme.SignUpActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_signUp_third extends Fragment {

    private ImageView sign_up_thirdIMG_1, sign_up_third_IMG_2, sign_up_third_IMG_3, sign_up_third_IMG_4, sign_up_third_IMG_5, sign_up_third_IMG_6,image;
    private ArrayList<SignUpActivity.Hobbies> hobbiesToUser = new ArrayList<>();
    private CircleImageView sign_up_IMG_logo;
    private final int PICK_IMAGE_REQUEST = 22;
    private Uri filePath;
    private Button signUp_BTN_end;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Users");
    private Bitmap bitmap;
    private FirebaseStorage storage;
    private StorageReference storageReference;
  //  private ArrayList<>allImagesArrayList = new ArrayList();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_third, container, false);
        sign_up_IMG_logo = view.findViewById(R.id.sign_up_IMG_logo);
        findViews(view);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        signUp_BTN_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerUser();
                if (MainActivity.allClients == null) {
                    MainActivity.allClients = new AllClients();
                }
            //    uploadImageAndRegisterLogo();
            }
        });
        sign_up_thirdIMG_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage(sign_up_thirdIMG_1);
               //uploadImageAndRegister();


            }
        });
        sign_up_third_IMG_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage(sign_up_third_IMG_2);
               // uploadImageAndRegister();
            }
        });
        sign_up_third_IMG_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage(sign_up_third_IMG_3);
            }
        });

        sign_up_IMG_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImageAndRegisterLogo();
                SelectImage(sign_up_IMG_logo);

            }
        });
        return view;
    }

    public void registerUser() {
        MainActivity.mFireBaseAuth.createUserWithEmailAndPassword(Fragment_signUp_first.user.getEmail(), Fragment_signUp_first.user.getPassword())
                .addOnCompleteListener(Fragment_signUp_third.this.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            MainActivity.allClients.addUser(Fragment_signUp_first.user);
                            myRef.setValue(MainActivity.allClients);
                            FirebaseUser user = MainActivity.mFireBaseAuth.getCurrentUser();
                            Intent intent = new Intent(getContext(), MatchingActivity.class);
                            startActivity(intent);
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getContext(), "This email is exists", Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void findViews(View view) {
        sign_up_thirdIMG_1 = view.findViewById(R.id.sign_up_third_IMG_1);
        sign_up_third_IMG_2 = view.findViewById(R.id.sign_up_third_IMG_2);
        sign_up_third_IMG_3 = view.findViewById(R.id.sign_up_third_IMG_3);
        sign_up_third_IMG_4 = view.findViewById(R.id.sign_up_third_IMG_4);
        sign_up_third_IMG_5 = view.findViewById(R.id.sign_up_third_IMG_5);
        sign_up_third_IMG_6 = view.findViewById(R.id.sign_up_third_IMG_6);
        signUp_BTN_end = view.findViewById(R.id.signUp_BTN_end);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == -1
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {
                bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(this.getActivity().getContentResolver(), filePath);
                image.setImageBitmap(bitmap);

            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    private void SelectImage(ImageView imageToChange) {

        image = imageToChange;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
    }

    private void uploadImageAndRegisterLogo() {
        if (filePath != null) {

            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("טוען...");
            progressDialog.show();

            StorageReference ref = storageReference.child(Fragment_signUp_first.user.getEmail()+"_profile");
            String str=Fragment_signUp_first.user.getEmail()+"_profile";

            ref.child(str).putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Fragment_signUp_first.user.setMainImage(String.valueOf(uri));
                        }
                    });
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "תמונה הועלתה!", Toast.LENGTH_SHORT).show();
                //      registerUser();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {// Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("הועלה " + (int) progress + "%");
                                }
                            });
        }
    }
    private void uploadImageAndRegister() {
        if (filePath != null) {

            ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("טוען...");
            progressDialog.show();

            StorageReference ref = storageReference.child(Fragment_signUp_first.user.getEmail());

            ref.child(Fragment_signUp_first.user.getEmail()).putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Fragment_signUp_first.user.setMainImage(String.valueOf(uri));
                        }
                    });
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "תמונה הועלתה!", Toast.LENGTH_SHORT).show();
               //     registerUser();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {// Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(
                                UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("הועלה " + (int) progress + "%");
                        }
                    });
        }
    }

}
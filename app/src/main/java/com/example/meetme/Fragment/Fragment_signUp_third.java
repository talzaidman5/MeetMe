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
import android.widget.ProgressBar;
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
import java.net.URI;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_signUp_third extends Fragment {

    private ImageView sign_up_thirdIMG_1, sign_up_third_IMG_2, sign_up_third_IMG_3, sign_up_third_IMG_4, sign_up_third_IMG_5, sign_up_third_IMG_6, image;
    private Map<ImageView, Uri> images;
    private CircleImageView sign_up_IMG_logo;
    private ProgressDialog progressDialog;
    private final int PICK_IMAGE_REQUEST = 22;
    private Uri filePath;
    private boolean profileImage = false;
    private Button signUp_BTN_end;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Users");
    private Bitmap bitmap;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private int imageIndex = 0;
    private int imageName = 0;
    private int numOfImages = 0;

    //  private ArrayList<>allImagesArrayList = new ArrayList();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_third, container, false);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        images = new HashMap<ImageView, Uri>();
        findViews(view);
        setOnClickFunctions();
        this.images = new HashMap<ImageView, Uri>();
        return view;
    }

    private void setOnClickFunctions() {
        for (ImageView img : this.images.keySet()) {
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SelectImage(img);
                }
            });
        }

        signUp_BTN_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.allClients == null) {
                    MainActivity.allClients = new AllClients();
                }
                uploadImages();

            }
        });

        sign_up_IMG_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectImage(sign_up_IMG_logo);
            }
        });
    }

    private void findViews(View view) {
        sign_up_IMG_logo = view.findViewById(R.id.sign_up_IMG_logo);
        sign_up_thirdIMG_1 = view.findViewById(R.id.sign_up_third_IMG_1);
        sign_up_third_IMG_2 = view.findViewById(R.id.sign_up_third_IMG_2);
        sign_up_third_IMG_3 = view.findViewById(R.id.sign_up_third_IMG_3);
        sign_up_third_IMG_4 = view.findViewById(R.id.sign_up_third_IMG_4);
        sign_up_third_IMG_5 = view.findViewById(R.id.sign_up_third_IMG_5);
        sign_up_third_IMG_6 = view.findViewById(R.id.sign_up_third_IMG_6);
        images.put(sign_up_thirdIMG_1, null);
        images.put(sign_up_third_IMG_2, null);
        images.put(sign_up_third_IMG_3, null);
        images.put(sign_up_third_IMG_4, null);
        images.put(sign_up_third_IMG_5, null);
        images.put(sign_up_third_IMG_6, null);
        images.put(sign_up_IMG_logo, null);

        signUp_BTN_end = view.findViewById(R.id.signUp_BTN_end);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == -1
                && data != null
                && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(this.getActivity().getContentResolver(), filePath);
                image.setImageBitmap(bitmap);
                numOfImages+=1;
                images.put(image, filePath);
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

    private void uploadImages() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("טוען...");
        progressDialog.show();
        for (ImageView img : this.images.keySet()) {
            if (this.images.get(img) != null) {
                uploadOneImage(this.images.get(img), img);
            }
        }
    }

    private void uploadOneImage(Uri path, ImageView img) {
        StorageReference ref = storageReference.child(Fragment_signUp_first.user.getEmail());
        String imageName;
        if (img.getClass() == CircleImageView.class){
            imageName = "profile";
            this.profileImage = true;
        }
        else{
            imageName = "" + this.imageName;
            this.imageName+=1;
        }
        ref.child(imageName).putFile(path).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                imageIndex+=1;
                if (imageIndex == numOfImages){
                    progressDialog.dismiss();
                    MainActivity.allClients.addUser(Fragment_signUp_first.user);
                    myRef.setValue(MainActivity.allClients);
                    Intent intent = new Intent(getContext(), MatchingActivity.class);
                    startActivity(intent);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {// Error, Image not uploaded
                        Toast.makeText(getContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage("הועלה " + (int) progress + "%");
                        ProgressBar progressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleSmall);
                        progressDialog.setView(progressBar);
                    }
                });
    }

}
package com.example.meetme.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.meetme.MainActivity;
import com.example.meetme.R;
import com.example.meetme.SignUpActivity;
import com.example.meetme.User;
import com.example.meetme.utils.MySheredP;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_signUp_third extends Fragment {

    private ImageView sign_up_thirdIMG_1,sign_up_third_IMG_2,sign_up_third_IMG_3,sign_up_third_IMG_4,sign_up_third_IMG_5,sign_up_third_IMG_6;
    private ArrayList<SignUpActivity.Hobbies> hobbiesToUser= new ArrayList<>();
    private CircleImageView sign_up_IMG_logo;
    private Boolean isImage= false;
    private final int PICK_IMAGE_REQUEST = 22;
    private Uri filePath;
    private Button signUp_BTN_end;
    private Gson gson = new Gson();
    private MySheredP msp;
    public static final String KEY_MSP  = "user";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("message");
    public ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_third, container, false);
        sign_up_IMG_logo = view.findViewById(R.id.sign_up_IMG_logo);
        msp = new MySheredP(getContext());
        findViews(view);
        signUp_BTN_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data  = msp.getString(KEY_MSP, "NA");
                String temp = new Gson().fromJson(data, String.class);
                User userTemp = new User(temp.toString());
                MainActivity.allClients.addUser(userTemp);
                myRef.setValue(MainActivity.allClients);
            }
        });
        sign_up_thirdIMG_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage(sign_up_thirdIMG_1);

            }
        });   sign_up_third_IMG_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage(sign_up_third_IMG_2);

            }
        });   sign_up_third_IMG_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage(sign_up_third_IMG_3);

            }
        });

        sign_up_IMG_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isImage = true;
                SelectImage(sign_up_IMG_logo);
            }
        });

//        Gson gson = new Gson();
//        gson.toJson(sign_up_thirdIMG_1.getDrawable().toString()+sign_up_third_IMG_2.getDrawable().toString()+
//                sign_up_third_IMG_3.getDrawable().toString()+sign_up_third_IMG_4.getDrawable().toString()+
//                sign_up_third_IMG_5.getDrawable().toString()+sign_up_third_IMG_6.getDrawable().toString());
//         putOnMSP(gson);

        return view;
    }
    private String getFromMSP(){
        String data  = msp.getString(KEY_MSP, "NA");
//        MainActivity.allClients = new AllClients(data);
        return data;
    }
    private void putOnMSP(Gson gson){
        msp.putString(KEY_MSP,gson);
    }
    private void findViews(View view) {
        sign_up_thirdIMG_1=view.findViewById(R.id.sign_up_third_IMG_1);
        sign_up_third_IMG_2=view.findViewById(R.id.sign_up_third_IMG_2);
        sign_up_third_IMG_3=view.findViewById(R.id.sign_up_third_IMG_3);
        sign_up_third_IMG_4=view.findViewById(R.id.sign_up_third_IMG_4);
        sign_up_third_IMG_5=view.findViewById(R.id.sign_up_third_IMG_5);
        sign_up_third_IMG_6=view.findViewById(R.id.sign_up_third_IMG_6);
        signUp_BTN_end=view.findViewById(R.id.signUp_BTN_end);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == -1
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(this.getActivity().getContentResolver(), filePath);
                image.setImageBitmap(bitmap);

            }

            catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    private void SelectImage(ImageView imageToChange) {

        image =imageToChange;
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

}
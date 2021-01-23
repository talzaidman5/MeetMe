package com.example.meetme.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;


public class PhotosController {
    final String BASE_URL = "https://pastebin.com/raw/";

    private CallBack_Photos callBack_Photos;

    Callback<Photos> PhotosCallBack = new Callback<Photos>() {
        @Override
        public void onResponse(Call<Photos> call, Response<Photos> response) {

            if (response.isSuccessful()) {
                Photos Photos = response.body();

                if (callBack_Photos != null) {
                    callBack_Photos.photos(Photos);
                }
            } else {
                System.out.println(response.errorBody());
            }
        }

        @Override
        public void onFailure(Call<Photos> call, Throwable t) {
            Log.d("pttt",t.getMessage());
            t.printStackTrace();
        }
    };



    public void fetchAllPhotos(CallBack_Photos callBack_Photos) {
        this.callBack_Photos = callBack_Photos;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        PhotosAPI PhotosAPI = retrofit.create(PhotosAPI.class);

        Call<Photos> call = PhotosAPI.loadPhotos();
        call.enqueue(PhotosCallBack);
    }

    public interface CallBack_Photos {
        void photos(Photos Photos);
    }

}

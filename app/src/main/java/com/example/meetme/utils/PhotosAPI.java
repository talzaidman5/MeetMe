package com.example.meetme.utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PhotosAPI {
    @GET("mSUvct2C")
    Call<Photos> loadPhotos();

}

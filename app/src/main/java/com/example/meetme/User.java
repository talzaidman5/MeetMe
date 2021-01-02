package com.example.meetme;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;

public class User{

    public enum Gender {
        MALE,
        FEMALE
    }
    private String name;
    private String age;
    private Gender personGender;
    private String city;
    private ArrayList<SignUpActivity.Hobbies> hobbies;
    private int minAge;
    private int maxAge;
    private Gender personPreferenceGender;
    private Uri mainImage;
    private String id;
    private String email;
    private String profession;
    private String status;
    private String password;
    private int distance;
    private String height;
    private String preferenceHeight;



    public User(String id, String name, String age, Gender personGender, String city, ArrayList<SignUpActivity.Hobbies> hobbies, int minAge,
                int maxAge, Gender personPreferenceGender,String email, String password,int distance, String height, String preferenceHeight) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.personGender = personGender;
        this.city = city;
        this.hobbies = hobbies;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.personPreferenceGender = personPreferenceGender;
        this.email = email;
        this.password = password;
        this.distance = distance;
        this.height = height;
        this.preferenceHeight = preferenceHeight;
    }
    public User(){}
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPreferenceHeight() {
        return preferenceHeight;
    }

    public void setPreferenceHeight(String preferenceHeight) {
        this.preferenceHeight = preferenceHeight;
    }

    public int getDistance() {
        return distance;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setPersonGender(Gender personGender) {
        this.personGender = personGender;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setHobbies(ArrayList<SignUpActivity.Hobbies> hobbies) {
        this.hobbies = hobbies;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public void setPersonPreferenceGender(Gender personPreferenceGender) {
        this.personPreferenceGender = personPreferenceGender;
    }

    public void setMainImage(Uri mainImage) {
        this.mainImage = mainImage;
    }

    public String getAge() {
        return age;
    }

    public Gender getPersonGender() {
        return personGender;
    }

    public String getCity() {
        return city;
    }

    public ArrayList<SignUpActivity.Hobbies> getHobbies() {
        return hobbies;
    }

    public int getMinAge() {
        return minAge;
    }

    public String getId() {
        return id;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public Gender getPersonPreferenceGender() {
        return personPreferenceGender;
    }

    public Uri getMainImage() {
        return mainImage;
    }

    public String getEmail() {
        return email;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

}

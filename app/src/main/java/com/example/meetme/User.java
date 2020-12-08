package com.example.meetme;

import java.util.ArrayList;

public class User {
    public enum Gender {
        MALE,
        FEMALE
    }
    private String name;
    private String age;
    private Gender personGender;
    private String city;
    private ArrayList<String>hobbies;
    private int minAge;
    private int maxAge;
    private Gender personPreferenceGender;
    private  String mainImage = "";


    public User(String name, String age, Gender personGender, String city, ArrayList<String> hobbies, int minAge,
                int maxAge, Gender personPreferenceGender) {
        this.name = name;
        this.age = age;
        this.personGender = personGender;
        this.city = city;
        this.hobbies = hobbies;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.personPreferenceGender = personPreferenceGender;
    }
    public User(){}
    public String getName() {
        return name;
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

    public void setHobbies(ArrayList<String> hobbies) {
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

    public void setMainImage(String mainImage) {
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

    public ArrayList<String> getHobbies() {
        return hobbies;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public Gender getPersonPreferenceGender() {
        return personPreferenceGender;
    }

    public String getMainImage() {
        return mainImage;
    }
}

package com.example.meetme.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class MySheredP {
    private SharedPreferences prefs;

    public MySheredP(Context context) {
         prefs = context.getSharedPreferences("MyPref1", MODE_PRIVATE);
    }


    public String getString(String key, String defValue)
    {
       return prefs.getString(key  , defValue);
    }

    public void putString(String key, Gson value)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value.toString());
        editor.apply();
    }

    public int getInt(String key, int defValue)
    {
        return prefs.getInt(key  , defValue);
    }


    public void putInt(String key, int value)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }


    public void removeKey(String key)
    {
        prefs.edit().remove(key);
    }


    public boolean isValid()
    {
        if(!prefs.contains("initialized"))
            return true;
        return false;
    }
}

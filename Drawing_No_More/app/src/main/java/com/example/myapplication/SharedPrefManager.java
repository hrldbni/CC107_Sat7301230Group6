package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SharedPrefManager {
    private static SharedPrefManager instance;
    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";


    private static Context ctx;

    private SharedPrefManager(Context context) {
        ctx = context;

    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public boolean userLogin(int uid, String username, String email) {

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_UID, uid);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_UID, email);

        editor.apply();

        return true;

    }

    //CHeck if user is logged in

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.getString(KEY_USERNAME, null) != null) {
         return true;
        }
        return  false;
    }

    public boolean loggedOut(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}

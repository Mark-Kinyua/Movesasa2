package com.muriithi.movesasa;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_NAME = "full_name";
    public static final String KEY_DATE = "birth_date";
    public static final String KEY_EMAIL = "email_address";
    public static final String KEY_PHONE = "phone_number";
    public static final String KEY_USER = "user_type";
    public static final String KEY_PASSWORD = "pass_word";

    public SessionManager(Context _context) {
        context = _context;
        sharedPreferences = context.getSharedPreferences("sharedLoginPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(String full_name, String birth_date, String email_address, String phone_number, String user_type, String pass_word) {

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_NAME, full_name);
        editor.putString(KEY_DATE, birth_date);
        editor.putString(KEY_EMAIL, email_address);
        editor.putString(KEY_PHONE, phone_number);
        editor.putString(KEY_USER, user_type);
        editor.putString(KEY_PASSWORD, pass_word);

        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_NAME, sharedPreferences.getString(KEY_NAME, null));
        userData.put(KEY_DATE, sharedPreferences.getString(KEY_DATE, null));
        userData.put(KEY_EMAIL, sharedPreferences.getString(KEY_EMAIL, null));
        userData.put(KEY_PHONE, sharedPreferences.getString(KEY_PHONE, null));
        userData.put(KEY_USER, sharedPreferences.getString(KEY_USER, null));
        userData.put(KEY_PASSWORD, sharedPreferences.getString(KEY_PASSWORD, null));

        return userData;
    }

    public Boolean checkLogin() {
        if (sharedPreferences.getBoolean(IS_LOGIN, false)) {
            return true;
        } else {
            return false;
        }
    }

    public void logoutUserSession() {
        editor.clear();
        editor.commit();
    }
}

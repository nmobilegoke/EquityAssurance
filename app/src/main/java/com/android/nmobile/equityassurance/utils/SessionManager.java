package com.android.nmobile.equityassurance.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.android.nmobile.equityassurance.ui.LoginActivity;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * @author Dev Rupesh Saxena
 */

public class SessionManager {

    static Context ctx;
    static SharedPreferences _preference;
    private static Editor editor;
    private static ArrayList<String> _UserInfoList;
    public static final String EMAIL = "Email";
    public static final String PASSWORD = "Password";
    public static final String SHA1PASSWORD = "Sha1Password";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String PHONENO = "PhoneNo";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    public static void intialize(Context ctx) {
        SessionManager.ctx = ctx;
        _preference = ctx.getSharedPreferences("SessionManager", Context.MODE_PRIVATE);
        editor = _preference.edit();
        _UserInfoList = new ArrayList<String>();
    }

    public static void add(String firstName, String lastName, String phoneNo, String email, String password) {

        String sha1Password = null;
        editor.putString(FIRST_NAME, firstName);
        editor.putString(LAST_NAME, lastName);
        editor.putString(PHONENO, phoneNo);
        editor.putString(EMAIL, email);
        editor.putString(PASSWORD, password);

        try {

            sha1Password = new SHA1HashKey().getSHA1(password);

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        editor.putString(SHA1PASSWORD, sha1Password);
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        editor.commit(); // commit changes
    }

    public static ArrayList<String> getSessionInfo() {

        _UserInfoList = new ArrayList<String>();

        String UserFirstName = _preference.getString(FIRST_NAME, null); // getting String
        String UserLastName = _preference.getString(LAST_NAME, null); // getting String
        String UserPhoneNo = _preference.getString(PHONENO, null); // getting String
        String UserEmail = _preference.getString(EMAIL, null); // getting String
        String Password = _preference.getString(PASSWORD, null); // getting String
        String UserSHA1Pass = _preference.getString(SHA1PASSWORD, null); // getting String

        _UserInfoList.add(UserFirstName); // 0 index
        _UserInfoList.add(UserLastName); // 1 index
        _UserInfoList.add(UserPhoneNo); // 2 index
        _UserInfoList.add(UserEmail); // 3 index
        _UserInfoList.add(Password); // 4 index
        _UserInfoList.add(UserSHA1Pass); // 5 index

        return _UserInfoList;

    }

    public static void remove() {

        editor.remove(FIRST_NAME); // will delete key name
        editor.remove(LAST_NAME); // will delete key email
        editor.remove(PHONENO);
        editor.remove(EMAIL);
        editor.remove(PASSWORD);
        editor.remove(SHA1PASSWORD);
        editor.commit();
    }

    public static void clearAll() {
        editor.clear();
        editor.commit();
        Intent intent = new Intent(ctx, LoginActivity.class);
        ctx.startActivity(intent);
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public static boolean isLoggedIn() {
        return _preference.getBoolean(IS_LOGIN, false);
    }

}


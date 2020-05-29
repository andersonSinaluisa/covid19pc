package com.today.covid_19puntoscriticos.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.today.covid_19puntoscriticos.R;

public class MainPreference {

    public static void userdata(Context c, String email, String username, String provider, String id){


            SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
            SharedPreferences.Editor edit =sharedPref.edit();
            edit.putString("email",email);
            edit.putString("username",username);
            edit.putString("provider",provider);
            edit.putString("id",id);
            edit.commit();

    }
    public static String email(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getString("email","");
    }
    public static String username(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getString("username","");
    }
    public static String id(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getString("id","");
    }
}

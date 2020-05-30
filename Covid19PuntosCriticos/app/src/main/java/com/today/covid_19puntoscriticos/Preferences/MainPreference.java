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

    public static void ageData(Context c, String age){

        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        SharedPreferences.Editor edit =sharedPref.edit();
        edit.putString("age",age);
        edit.commit();

    }
    public static String getAge(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getString("age","");
    }


    public static void diseases(Context c, boolean diseases){

        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        SharedPreferences.Editor edit =sharedPref.edit();
        edit.putBoolean("diseases",diseases);
        edit.commit();

    }

    public static void poll(Context c, boolean poll){

        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        SharedPreferences.Editor edit =sharedPref.edit();
        edit.putBoolean("poll",poll);
        edit.commit();

    }

    public static void basicInfo(Context c, boolean basiInfo){

        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        SharedPreferences.Editor edit =sharedPref.edit();
        edit.putBoolean("basicInfo",basiInfo);
        edit.commit();

    }





    public static boolean getDiseases(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getBoolean("diseases",false);
    }

    public static boolean getBasicInfo(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getBoolean("basicInfo",false);
    }
    public static boolean getPoll(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getBoolean("poll",false);
    }

}

package com.today.covid_19puntoscriticos.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.today.covid_19puntoscriticos.R;

import java.util.Date;

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

    public static void genrData(Context c, String genr){

        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        SharedPreferences.Editor edit =sharedPref.edit();
        edit.putString("genr",genr);
        edit.commit();

    }
    public static String getAge(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getString("age","");
    }
    public static String getGenr(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getString("genr","");
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

    public static void notificationPoll(Context c, boolean notPoll){

        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        SharedPreferences.Editor edit =sharedPref.edit();
        edit.putBoolean("notPoll",notPoll);
        edit.commit();

    }

    public static boolean getNotPoll(Context c){

        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getBoolean("notPoll",false);
    }

    public static void notificationDate(Context c, String notDate){

        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        SharedPreferences.Editor edit =sharedPref.edit();
        edit.putString("notDate",notDate);
        edit.commit();

    }

    public static String getNotificationDate(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getString("notDate",null);
    }

    public static void positionBoolean(Context c, boolean enablePosition){

        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        SharedPreferences.Editor edit =sharedPref.edit();
        edit.putBoolean("enablePosition",enablePosition);
        edit.commit();

    }

    public static boolean getPositionBoolean(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getBoolean("enablePosition",false);
    }

    public static void token(Context c, String token){

        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        SharedPreferences.Editor edit =sharedPref.edit();
        edit.putString("token",token);
        edit.commit();

    }
    public static String getToken(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getString("token","");
    }

    public static void n_sintomas(Context c, int n_sintomas){

        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        SharedPreferences.Editor edit =sharedPref.edit();
        edit.putInt("n_sintomas",n_sintomas);
        edit.commit();

    }

    public static int getN_sintomas(Context c){
        SharedPreferences sharedPref= c.getSharedPreferences(c.getString(R.string.app_name), c.MODE_PRIVATE);
        return sharedPref.getInt("n_sintomas",0);
    }


}

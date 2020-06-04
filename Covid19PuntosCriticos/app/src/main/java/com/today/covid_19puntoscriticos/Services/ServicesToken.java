package com.today.covid_19puntoscriticos.Services;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.today.covid_19puntoscriticos.Config.Firebase;
import com.today.covid_19puntoscriticos.Model.Dispositivo;

import static com.today.covid_19puntoscriticos.Preferences.MainPreference.getToken;
import static com.today.covid_19puntoscriticos.Preferences.MainPreference.id;
import static com.today.covid_19puntoscriticos.Preferences.MainPreference.token;

public class ServicesToken extends FirebaseMessagingService {


    @Override
    public void onNewToken(@NonNull String s) {

        super.onNewToken(s);
        System.out.println("token "+s);


        registrarToken(s);


    }


    /** registrar el el tokenobtenido como parametro **/
    private void registrarToken(String token) {
        final Firebase db = new Firebase();

        // Get new Instance ID token


            if (getToken(this).equals("") || getToken(this).isEmpty() || getToken(this) != null) {
                token(this, token);
            }



    }

}

package com.today.covid_19puntoscriticos.Services;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.today.covid_19puntoscriticos.Config.Firebase;
import com.today.covid_19puntoscriticos.Model.Dispositivo;

import static com.today.covid_19puntoscriticos.Preferences.MainPreference.id;

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
        final DatabaseReference refd =db.getmDatabase("dispositivos");
        // Get new Instance ID token

        if(!id(ServicesToken.this).equals("") || id(ServicesToken.this)!=null || !id(ServicesToken.this).isEmpty()){
            String id_usuario = id(ServicesToken.this);
            Dispositivo dis = new Dispositivo();
            dis.setId(id_usuario);
            dis.setId_usuario(id_usuario);
            dis.setEstado(1);
            dis.setToken(token);
            refd.child(dis.getId()).setValue(dis);
        }



    }

}

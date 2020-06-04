package com.today.covid_19puntoscriticos.Services;
import android.annotation.SuppressLint;
import android.app.Service;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import 	android.content.BroadcastReceiver;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.today.covid_19puntoscriticos.Config.Firebase;
import com.today.covid_19puntoscriticos.Model.HistorialPosition;
import com.today.covid_19puntoscriticos.Preferences.MainPreference;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.today.covid_19puntoscriticos.Preferences.MainPreference.email;
import static com.today.covid_19puntoscriticos.Preferences.MainPreference.getPositionBoolean;
import static com.today.covid_19puntoscriticos.Preferences.MainPreference.username;

public class ServicesUbication extends Service {

    private Context thisContext = this;
    private String email ;
    private String username ;
    private String id;

    private double longitud = 0;
    private double latitud = 0;
    private boolean validatorSymptoms;
    private HashMap<String,Object> m = new HashMap<String, Object>();

    final Firebase db = new Firebase();

    private int count;

  

    @Override
    public void onCreate() {


    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent i, int flag, int id_process) {
        email= email(thisContext);
        username = username(thisContext);
        id= MainPreference.id(thisContext);
        LocationManager locationManager = (LocationManager) thisContext.getSystemService(Context.LOCATION_SERVICE);

        System.out.println("CONTADOR "+ count);
        assert locationManager != null;

            LocationListener locationListener = new LocationListener() {
                @SuppressLint("ShowToast")
                @Override
                public void onLocationChanged(Location location) {
                    System.out.println(getPositionBoolean(thisContext)+"---"+location.getLatitude()+"--->"+location.getLongitude());

                    if(getPositionBoolean(thisContext)){
                        HistorialPosition p = new HistorialPosition();
                        p.setId(UUID.randomUUID().toString());
                        p.setId_usuario(id);
                        p.setLatitud(location.getLatitude());
                        p.setLongitud(location.getLongitude());
                        final DatabaseReference posicion = db.getmDatabase("Historial");
                        posicion.child(p.getId()).setValue(p);
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };


            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

            if(longitud!=0 && latitud!=0){

            }



        return START_STICKY;
    }


    @Override
    public void onDestroy(){

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    private void getSymptoms(String id){

        final DatabaseReference posicion = db.getmDatabase("Poll");
        Query q = posicion.orderByChild("id_usuario").equalTo(id);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot obj : dataSnapshot.getChildren()){


                        for (int x =0; x<obj.getChildrenCount();x++){
                            System.out.println(obj.getKey()+"==>" +obj.getValue());
                                HashMap<String,Object> o = (HashMap<String,Object>) obj.getValue();

                            for (Map.Entry<String, Object> entry : o.entrySet()){
                                System.out.println(entry.getKey()+"==>"+entry.getValue());
                                if(!entry.getKey().equals("id") || !entry.getKey().equals("id_usuario")){
                                    if(entry.getValue().equals(true)){
                                        count++;
                                        System.out.println(count + " CONTADOR");
                                    }

                                }
                            }
                        }


                    }
                   /* for (Map.Entry<String, Object> entry : m.entrySet()){
                        System.out.println(entry.getKey()+"==>"+entry.getValue());

                        HashMap.Entry<String,Object> values =entry ;
                        for (Map.Entry<String, Object> entryValues : values.){

                            System.out.println(entryValues.getKey()+"==>"+entryValues.getValue());
                            if(!entry.getKey().equals("id") || !entry.getKey().equals("id_usuario")){
                                if(entry.getValue().equals(true)){
                                    count=+1;
                                    System.out.println(count + " CONTADOR");
                                }

                            }
                        }




                    }*/

                    if(count>10){
                        validatorSymptoms=true;
                    }else{
                        validatorSymptoms=false;
                    }

                }else{
                    validatorSymptoms=false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}

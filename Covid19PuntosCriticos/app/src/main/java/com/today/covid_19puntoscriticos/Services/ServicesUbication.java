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

import androidx.annotation.Nullable;

import static com.today.covid_19puntoscriticos.Preferences.MainPreference.email;
import static com.today.covid_19puntoscriticos.Preferences.MainPreference.username;

public class ServicesUbication extends Service {

    private Context thisContext = this;
    private String email ;
    private String username ;
    private double longitud = 0;
    private double latitud = 0;


    @Override
    public void onCreate() {


    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent i, int flag, int id_process) {
        email= email(thisContext);
        username = username(thisContext);
        LocationManager locationManager = (LocationManager) thisContext.getSystemService(Context.LOCATION_SERVICE);

        assert locationManager != null;
        if (locationManager.isProviderEnabled("gps")) {
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    latitud = location.getLatitude();
                    longitud = location.getLongitude();

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
}

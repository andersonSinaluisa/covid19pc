package com.today.covid_19puntoscriticos.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.today.covid_19puntoscriticos.Main.Earth;
import com.today.covid_19puntoscriticos.Main.WeatherService;
import com.today.covid_19puntoscriticos.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service extends AppCompatActivity {

    private int permissionchecked;
    private double longitud = 0;
    private double latitud = 0;

    private ImageView img;

    private  String EDteamImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        img = (ImageView) findViewById(R.id.img_nasa);

        Glide.with(Service.this).load(EDteamImage).into(img);

        loadLocation();


        if(longitud!=0 && latitud!=0){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.nasa.gov/planetary/apod/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            WeatherService service = retrofit.create(WeatherService.class);
            Date date = new Date();
            SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String fecha = sfd.format(date);
            Call<Earth> earthCall = service.getEarth(longitud+"", latitud+"", "2014-02-01", "0.15", "DEMO_KEY");
            earthCall.enqueue(new Callback<Earth>() {
                @Override
                public void onResponse(Call<Earth> call, Response<Earth> response) {
                    Earth earth = response.body();
                    if (call.isExecuted()) {
                        EDteamImage = earth.getUrl();
                        System.out.println(earth);
                        // Toast.makeText(Service.this, earth.getUrl(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Earth> call, Throwable t) {
                    System.out.println(t.getCause());
                    Toast.makeText(Service.this, "Error", Toast.LENGTH_LONG).show();
                }
            });



        }

    }

    private void loadLocation() {
        permissionchecked = ContextCompat.checkSelfPermission(Service.this, Manifest.permission.ACCESS_FINE_LOCATION);

        if(!(permissionchecked == PackageManager.PERMISSION_GRANTED)){
            //inicia el servicio
            ActivityCompat.requestPermissions(Service.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);


        }else{
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    latitud = location.getLatitude();
                    longitud = location.getLongitude();
                    System.out.println(longitud+"--->"+latitud+" GPS POSICION");

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

        }


    }


}
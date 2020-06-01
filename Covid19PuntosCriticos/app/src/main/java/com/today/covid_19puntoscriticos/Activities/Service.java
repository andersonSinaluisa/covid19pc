package com.today.covid_19puntoscriticos.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.today.covid_19puntoscriticos.Main.Earth;
import com.today.covid_19puntoscriticos.Main.WeatherService;
import com.today.covid_19puntoscriticos.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/planetary/apod")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherService service =retrofit.create(WeatherService.class);

       Call<Earth> earthCall = service.getEarth("95.33","29.78","2018-01-01","0.10","an1IMjVwogVGhmgvr7f0BgEu6u2g3YRW5J2qisFf");
       earthCall.enqueue(new Callback<Earth>() {
           @Override
           public void onResponse(Call<Earth> call, Response<Earth> response) {
               Earth earth = response.body();
               System.out.println(earth.getUrl());
           }

           @Override
           public void onFailure(Call<Earth> call, Throwable t) {
               Toast.makeText(Service.this,"Error",Toast.LENGTH_LONG).show();
           }
       });
    }



}
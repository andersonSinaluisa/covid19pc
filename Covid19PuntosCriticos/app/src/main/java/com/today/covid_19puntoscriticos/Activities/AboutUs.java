package com.today.covid_19puntoscriticos.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.today.covid_19puntoscriticos.R;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        getSupportActionBar().setTitle(R.string.st_about_as);
    }
}

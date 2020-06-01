package com.today.covid_19puntoscriticos.Main.ui.country;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.today.covid_19puntoscriticos.R;


public class CovidCountryDatail extends AppCompatActivity {

    TextView tvDetailCountryName, tvDetailTotalCases, tvDetailTodayCases, tvDetailTotalDeaths,
             tvDetailTodayDeaths, tvDetailTotalRecovered, tvDetailTotalActive, tvDetailTotalCritical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_country_datail);

        //call view

        tvDetailCountryName= findViewById(R.id.tvDetailCountryName);
        tvDetailTotalCases= findViewById(R.id.tvDetailTotalCases);
        tvDetailTodayCases= findViewById(R.id.tvDetailTodayCases);
        tvDetailTotalDeaths= findViewById(R.id.tvDetailTotalDeaths);
        tvDetailTodayDeaths= findViewById(R.id.tvDetailTodayDeaths);
        tvDetailTotalRecovered= findViewById(R.id.tvDetailTotalRecovered);
        tvDetailTotalActive= findViewById(R.id.tvDetailTotalActive);
        tvDetailTotalCritical= findViewById(R.id.tvDetailTotalCritical);


       //call Covid Country
        CovidCountry covidCountry = getIntent().getParcelableExtra("EXTRA_COVID");

        // SET TEX VIEW

        tvDetailCountryName.setText(covidCountry.getmCovidCountry());
        tvDetailTotalCases.setText(covidCountry.getmCases());
        tvDetailTodayCases.setText(covidCountry.getmTodayCases());
        tvDetailTotalDeaths.setText(covidCountry.getmDeaths());
        tvDetailTodayDeaths.setText(covidCountry.getmTodayDeaths());
        tvDetailTotalRecovered.setText(covidCountry.getmRecovered());
        tvDetailTotalActive.setText(covidCountry.getmActive());
        tvDetailTotalCritical.setText(covidCountry.getmCritical());



    }
}


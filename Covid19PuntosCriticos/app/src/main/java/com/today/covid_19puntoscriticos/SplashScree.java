package com.today.covid_19puntoscriticos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.today.covid_19puntoscriticos.Main.MainActivity;

import static com.today.covid_19puntoscriticos.Preferences.MainPreference.id;

public class SplashScree extends AppCompatActivity {
    private  final  int DURACION_SPLASH=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_scree);

        new Handler().postDelayed(new Runnable() {
            public void run() {

                    Intent intent = new Intent(SplashScree.this, LoginActivity.class);
                    startActivity(intent);

                finish();
            };
        }, DURACION_SPLASH);
    }
}
package com.today.covid_19puntoscriticos.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.today.covid_19puntoscriticos.Activities.Poll;
import com.today.covid_19puntoscriticos.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.today.covid_19puntoscriticos.Preferences.MainPreference.getNotPoll;
import static com.today.covid_19puntoscriticos.Preferences.MainPreference.getNotificationDate;
import static com.today.covid_19puntoscriticos.Preferences.MainPreference.notificationDate;
import static com.today.covid_19puntoscriticos.Preferences.MainPreference.notificationPoll;

public class PollService extends Service {

    private Context c=this;
    Calendar calendar = Calendar.getInstance();



    @Override
    public void onCreate() {


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        if(getNotPoll(c) && !getNotificationDate(c).equals("") || !getNotificationDate(c).isEmpty() || getNotificationDate(c)!=null){

            String dateProgamada= getNotificationDate(c);
            if(dateProgamada.equals(calendar.getTime().toString())){
                notification();
            }



        }else{
            notification();
        }





        return START_STICKY;
    }

    private void notification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(c)
                .setSmallIcon(R.drawable.ic_poll)
                .setLargeIcon((((BitmapDrawable)getResources()
                        .getDrawable(R.mipmap.ic_launcher)).getBitmap()))
                .setContentTitle(getResources().getString(R.string.notificationPollTitle))
                .setContentText(getResources().getString(R.string.notificationPollDescription));


        Intent notIntent = new Intent(c, Poll.class);

        PendingIntent contIntent = PendingIntent.getActivity(c, 0, notIntent, 0);

        mBuilder.setContentIntent(contIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(2, mBuilder.build());
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

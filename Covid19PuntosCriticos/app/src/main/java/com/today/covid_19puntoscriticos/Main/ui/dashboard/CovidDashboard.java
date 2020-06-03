package com.today.covid_19puntoscriticos.Main.ui.dashboard;

import android.os.Parcel;

public class CovidDashboard {
    String  mCases,  mDeaths,  mRecovered;

    public CovidDashboard() {}

    public CovidDashboard(String mCases, String mDeaths, String mRecovered) {
        this.mCases = mCases;
        this.mDeaths = mDeaths;
        this.mRecovered = mRecovered;
    }

    public String getmCases() {
        return mCases;
    }

    public void setmCases(String mCases) {
        this.mCases = mCases;
    }

    public String getmDeaths() {
        return mDeaths;
    }

    public void setmDeaths(String mDeaths) {
        this.mDeaths = mDeaths;
    }

    public String getmRecovered() {
        return mRecovered;
    }

    public void setmRecovered(String mRecovered) {
        this.mRecovered = mRecovered;
    }
}

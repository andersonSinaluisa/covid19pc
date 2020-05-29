package com.today.covid_19puntoscriticos.Main.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("COVID-19 Estadistica Global");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
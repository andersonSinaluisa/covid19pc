package com.today.covid_19puntoscriticos.Config;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.today.covid_19puntoscriticos.Adapters.CardAdapter;
import com.today.covid_19puntoscriticos.Model.Pages;

import java.util.ArrayList;
import java.util.List;

public class Firebase extends android.app.Application{
    private DatabaseReference mDatabase;



    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    private void inicializarFirebase(Context c) {
        FirebaseApp.initializeApp(c);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
    }
    public DatabaseReference getmDatabase(String ref) {
        mDatabase= FirebaseDatabase.getInstance().getReference(ref);
        return mDatabase;
    }

    public void setmDatabase(DatabaseReference mDatabase) {
        this.mDatabase = mDatabase;
    }
}

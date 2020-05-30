package com.today.covid_19puntoscriticos.Main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.today.covid_19puntoscriticos.Activities.AboutUs;
import com.today.covid_19puntoscriticos.Activities.BasicInfo;
import com.today.covid_19puntoscriticos.Activities.Diseases;
import com.today.covid_19puntoscriticos.Activities.MyDiagnosis;
import com.today.covid_19puntoscriticos.Activities.Poll;
import com.today.covid_19puntoscriticos.Config.Firebase;
import com.today.covid_19puntoscriticos.Model.Usuario;
import com.today.covid_19puntoscriticos.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Objects;

import static com.today.covid_19puntoscriticos.Preferences.MainPreference.id;
import static com.today.covid_19puntoscriticos.Preferences.MainPreference.userdata;

public class MainActivity extends AppCompatActivity {

    final Firebase db = new Firebase();
    private boolean diseasesValidator;
    private boolean pollValidator;
    private boolean basicInfoValidator;



    private FirebaseUser currentUser = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();






        dataUser(currentUser.getUid(),currentUser.getEmail(),currentUser.getDisplayName(),currentUser.getPhotoUrl(),currentUser.getProviderId());



        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_map)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);



        loadDiseasesAnswer();
        loadPoll();
        loadBasicInfo();


    }

    private void loadBasicInfo() {
        final DatabaseReference diseases = db.getmDatabase("BasicInfo");
        Query q = diseases.orderByChild("id_usuario").equalTo(id(MainActivity.this));
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    basicInfoValidator = true;
                }else{
                    basicInfoValidator = false;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadPoll() {
        final DatabaseReference diseases = db.getmDatabase("Poll");
        Query q = diseases.orderByChild("id_usuario").equalTo(id(MainActivity.this));
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    pollValidator = true;
                }else{
                    pollValidator = false;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void loadDiseasesAnswer() {
        final DatabaseReference diseases = db.getmDatabase("Diseases");
        Query q = diseases.orderByChild("id_usuario").equalTo(id(MainActivity.this));
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           if(dataSnapshot.exists()){
               diseasesValidator = true;
           }else{
               diseasesValidator = false;
           }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void dataUser(String _UID, String _email, String _name, Uri _url, String _provider) {

        final String UID = _UID;
        final String email =_email;
        final String name = _name;
        final String photo_url = _url+"";
        final String provider = _provider;



        final DatabaseReference referenceUsers = db.getmDatabase("Usuarios");

        referenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //si existen registros
                if(dataSnapshot.exists()){
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        Usuario u = obj.getValue(Usuario.class);
                        //si el usuario existe
                        System.out.println(email+"==>"+u.getUID());

                           if(u.getUID().equals(UID)){
                               System.out.println(u.getUID()+"==>"+u.getEmail());

                           }else{
                               Usuario newUser = new Usuario();
                               newUser.setUID(UID);
                               newUser.setEmail(email);
                               newUser.setName(name);
                               newUser.setPhoto_url(photo_url);
                               newUser.setProvider(provider);
                               referenceUsers.child(newUser.getUID()).setValue(newUser);
                               Toast.makeText(MainActivity.this, email, Toast.LENGTH_LONG).show();

                           }


                    }
                }else{
                    //si es el primer registro de toda la tabla
                    Usuario newUser = new Usuario();
                    newUser.setUID(UID);
                    newUser.setEmail(email);
                    newUser.setPhoto_url(photo_url);
                    newUser.setProvider(provider);
                    referenceUsers.child(newUser.getUID()).setValue(newUser);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("USUARIOS",databaseError.getMessage());
            }
        });
        if(id(MainActivity.this).equals("")){

            userdata(MainActivity.this,_email,name,provider,UID);

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.session,menu);

        return true;
    }






    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.myDiagnosis:
                startActivity(new Intent(MainActivity.this, MyDiagnosis.class));
                return true;
            case R.id.poll:
                if(!diseasesValidator){
                    startActivity(new Intent(MainActivity.this, Diseases.class));
                }else{
                    if(!pollValidator){
                        startActivity(new Intent(MainActivity.this, Poll.class));
                    }else {
                        if(!basicInfoValidator){
                            startActivity(new Intent(MainActivity.this, BasicInfo.class));
                        }else {
                            startActivity(new Intent(MainActivity.this, Diseases.class));
                        }
                    }

                }


                return true;
            case R.id.aboutUs:
                startActivity(new Intent(MainActivity.this, AboutUs.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    }







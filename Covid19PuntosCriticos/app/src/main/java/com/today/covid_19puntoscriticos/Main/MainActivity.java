package com.today.covid_19puntoscriticos.Main;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity {

    final Firebase db = new Firebase();


    private TextView user;
    private ImageView imageView;

    private FirebaseUser currentUser = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();


        imageView = (ImageView) findViewById(R.id.img_user);
        user = (TextView) findViewById(R.id.username);


        loadDataInUI(currentUser);
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



    }

    private void loadDataInUI(FirebaseUser currentUser) {

        if(Objects.requireNonNull(currentUser.getDisplayName()).isEmpty()|| currentUser.getDisplayName()!=null){
            user.setText(currentUser.getEmail());
        }else{
            user.setText(currentUser.getDisplayName());

        }

        if(currentUser.getPhotoUrl()==null){
            imageView.setBackground(getResources().getDrawable(R.drawable.ic_person));

        }else{
            Glide.with(MainActivity.this)
                    .load(currentUser.getPhotoUrl().toString())
                    .into(imageView);
        }


    }

}

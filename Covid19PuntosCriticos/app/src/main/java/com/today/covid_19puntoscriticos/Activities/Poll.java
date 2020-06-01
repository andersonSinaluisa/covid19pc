package com.today.covid_19puntoscriticos.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.today.covid_19puntoscriticos.Config.Firebase;
import com.today.covid_19puntoscriticos.LoginActivity;
import com.today.covid_19puntoscriticos.Main.MainActivity;
import com.today.covid_19puntoscriticos.Model.Preguntas;
import com.today.covid_19puntoscriticos.Model.Usuario;
import com.today.covid_19puntoscriticos.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import static com.google.firebase.database.Transaction.*;
import static com.today.covid_19puntoscriticos.Preferences.MainPreference.id;
import static com.today.covid_19puntoscriticos.Preferences.MainPreference.poll;

public class Poll extends AppCompatActivity {

    private LinearLayout main;

    private LinearLayout layoutSymptoms;

    private Button btn;


    final Firebase db = new Firebase();

    //se guardan las respuestas
    HashMap<String,Object> m = new HashMap<String, Object>();

    private int total;
    private FirebaseUser currentUser = null;

    private ProgressDialog dialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);
        main=(LinearLayout) findViewById(R.id.layoutMain);


        layoutSymptoms=(LinearLayout) findViewById(R.id.layoutSymptoms);
        btn = (Button) findViewById(R.id.btn_save_poll);
        dialog= new ProgressDialog(Poll.this);




        //preguntas de los sintomas
        loadSymptoms();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.setTitle(getResources().getString(R.string.saving));
                dialog.setCancelable(false);
                dialog.show();
                Date date = new Date();
                SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
                String fecha = sfd.format(date);
                final String month = fecha.substring(3,5);
                if(!m.isEmpty() || m.size()==total){

                    m.put("id", UUID.randomUUID().toString());
                    if(id(Poll.this).equals("") || id(Poll.this)==null){
                        currentUser = FirebaseAuth.getInstance().getCurrentUser();

                            assert currentUser != null;
                            m.put("id_usuario",currentUser.getUid());

                    }else{
                        m.put("id_usuario",id(Poll.this));
                    }

                    m.put("fecha",fecha);
                    m.put("mes",month);
                    final DatabaseReference poll = db.getmDatabase("Poll");
                    poll.child(m.get("id").toString()).setValue(m);
                    poll(Poll.this,true);
                    dialog.dismiss();
                    startActivity(new Intent(Poll.this, MainActivity.class));
                }else {
                    dialog.dismiss();

                    Toast.makeText(Poll.this, getResources().getString(R.string.vacios), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }









    private void loadSymptoms() {
        //crear una referencia
        final DatabaseReference referenceQuestions = db.getmDatabase("Preguntas");
        //crea un query
        Query q = referenceQuestions.orderByChild("categoria").equalTo("sintomas");
        //ejecuta la consulta
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //si existen datos en la referencia
                if(dataSnapshot.exists()){
                    total = (int) dataSnapshot.getChildrenCount();
                    //recorre con un for each
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        //la clase que recibira estos datos

                     Preguntas p = obj.getValue(Preguntas.class);
                     //si es estado es true
                     if(p.isEstado()){
                         //se muestra en la pantalla
                         loadQuestions(p,layoutSymptoms);
                     }
                        System.out.println(p.getId()+"==>"+p.getDescripcion());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }








    //cargar las preguntas en el layout
    private void loadQuestions(final Preguntas p, LinearLayout layout) {

        if(p.getTipo().equals("radio")){
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final LinearLayout questionLayout = (LinearLayout) inflater.inflate(R.layout.item_question1, null);
            TextView question = (TextView) questionLayout.findViewById(R.id.questionBase);
            RadioButton radioLeft = (RadioButton) questionLayout.findViewById(R.id.answerBaseLeft);
            RadioButton radioRight = (RadioButton) questionLayout.findViewById(R.id.answerBaseRight);
            radioLeft.setText(getResources().getString(R.string.yes));
            radioRight.setText(getResources().getString(R.string.No));
            question.setText(p.getDescripcion());


            radioLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                m.put(p.getId().toString(),1);

                }
            });


            radioRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    m.put(p.getId().toString(),0);
                }
            });


            layout.addView(questionLayout);
        }


    }
}

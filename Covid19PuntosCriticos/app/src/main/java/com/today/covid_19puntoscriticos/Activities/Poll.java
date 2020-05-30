package com.today.covid_19puntoscriticos.Activities;

import androidx.annotation.NonNull;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.today.covid_19puntoscriticos.Config.Firebase;
import com.today.covid_19puntoscriticos.LoginActivity;
import com.today.covid_19puntoscriticos.Main.MainActivity;
import com.today.covid_19puntoscriticos.Model.Preguntas;
import com.today.covid_19puntoscriticos.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

                if(!m.isEmpty() || m.size()==total){

                    m.put("id", UUID.randomUUID().toString());
                    m.put("id_usuario",id(Poll.this));

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

                m.put(p.getId().toString(),true);

                }
            });


            radioRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    m.put(p.getId().toString(),false);
                }
            });


            layout.addView(questionLayout);
        }



        if(p.getTipo().equals("input")){
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final LinearLayout questionLayout = (LinearLayout) inflater.inflate(R.layout.item_input, null);
            TextView t = (TextView) questionLayout.findViewById(R.id.basicQuestionInput);
            t.setText(p.getDescripcion());
            EditText e = (EditText) questionLayout.findViewById(R.id.respuesta);




            layout.addView(questionLayout);
            m.put(p.getId().toString(),e.getText());
        }








    }
}

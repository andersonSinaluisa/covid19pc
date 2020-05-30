package com.today.covid_19puntoscriticos.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.today.covid_19puntoscriticos.Main.MainActivity;
import com.today.covid_19puntoscriticos.Model.Preguntas;
import com.today.covid_19puntoscriticos.R;

import java.util.HashMap;
import java.util.UUID;

import static com.today.covid_19puntoscriticos.Preferences.MainPreference.diseases;
import static com.today.covid_19puntoscriticos.Preferences.MainPreference.id;

public class Diseases extends AppCompatActivity {

    private LinearLayout layoutDiseases;
    private Button btn;

    final Firebase db = new Firebase();

    //se guardan las respuestas
    HashMap<String,Object> m = new HashMap<String, Object>();

    private ProgressDialog dialog ;


    private int total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diseases);
        layoutDiseases=(LinearLayout) findViewById(R.id.layoutDisease);
        btn=(Button) findViewById(R.id.btn_save_diseases);
        //preguntas de enfermedades
        loadDiseases();

        dialog= new ProgressDialog(Diseases.this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setTitle(getResources().getString(R.string.saving));
                dialog.setCancelable(false);
                dialog.show();
                if(m.size()==total){

                    m.put("id", UUID.randomUUID().toString());
                    m.put("id_usuario",id(Diseases.this));
                    final DatabaseReference poll = db.getmDatabase("Diseases");
                    poll.child(m.get("id").toString()).setValue(m);
                    dialog.dismiss();
                    startActivity(new Intent(Diseases.this, MainActivity.class));
                    diseases(Diseases.this,true);
                }else {
                    dialog.dismiss();

                    Toast.makeText(Diseases.this, getResources().getString(R.string.vacios), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    private void loadDiseases() {

        final DatabaseReference referenceQuestions = db.getmDatabase("Preguntas");
        Query q = referenceQuestions.orderByChild("categoria").equalTo("enfermedades");
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    total=(int) dataSnapshot.getChildrenCount();
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        Preguntas p = obj.getValue(Preguntas.class);

                        if(p.isEstado()){
                            if(p.getTipo().equals("radio")){
                                loadQuestions(p,layoutDiseases);

                            }


                            System.out.println(p.getId()+"==>"+p.getDescripcion());
                        }


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
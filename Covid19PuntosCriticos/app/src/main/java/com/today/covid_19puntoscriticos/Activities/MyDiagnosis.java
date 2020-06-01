package com.today.covid_19puntoscriticos.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.today.covid_19puntoscriticos.Config.Firebase;
import com.today.covid_19puntoscriticos.R;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.today.covid_19puntoscriticos.Preferences.MainPreference.email;
import static com.today.covid_19puntoscriticos.Preferences.MainPreference.id;
import static com.today.covid_19puntoscriticos.Preferences.MainPreference.username;

public class MyDiagnosis extends AppCompatActivity {

    private BarChart lineChart;
    private BarDataSet lineDataSet;
    private ImageView img;
    private FirebaseUser currentUser=null;
    private TextView user;
    private TextView recomendaciones;
    final Firebase db = new Firebase();
    private int count;//y
    private int mes; //x
    private int anio;
    private int dia;


    private int evaluate;
    private ArrayList<BarEntry> lineEntries = new ArrayList<BarEntry>();
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_diagnosis);
        lineChart = findViewById(R.id.lineChart);
        img = findViewById(R.id.profile_image);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        user = (TextView)findViewById(R.id.username);
        recomendaciones = (TextView) findViewById(R.id.recomendation);
        getSupportActionBar().hide();

            img.setImageResource(R.drawable.ic_baseline_account);

            if(username(MyDiagnosis.this).equals("")){
                user.setText(email(MyDiagnosis.this));
            }else {
                user.setText(username(MyDiagnosis.this));

            }

            if(currentUser.getPhotoUrl()!=null){
                String url =currentUser.getPhotoUrl().toString();
                if(url.isEmpty() || url.equals("")){
                    img.setImageDrawable(getDrawable(R.drawable.ic_baseline_account));
                }else{

                    Glide.with(MyDiagnosis.this).load(url).into(img);
                }
            }


        loadData();


        System.out.println(evaluate+"EVALUACION");
            if(evaluate==1){
                recomendaciones.setText(getResources().getString(R.string.positives_symptomas));
            }else{
                recomendaciones.setText(getResources().getString(R.string.negative_symptoms));
            }
       /* for (int i = 0; i<11; i++){
            float y = (int) (Math.random() * 8) + 1;
            lineEntries.add(new Entry((float) i,(float)y));
        }*/

        // Unimos los datos al data set

    }

    private void loadData() {

        Date date = new Date();
        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String fecha = sfd.format(date);

        final String year = fecha.substring(6,10);
        final String month = fecha.substring(3,5);
        final String day = fecha.substring(0,2);
        int _year = Integer.parseInt(year);
        final int _month = Integer.parseInt(month);
        int _day = Integer.parseInt(day);
        System.out.println(year+"AÑO");
        final DatabaseReference poll = db.getmDatabase("Poll");
        Query q = poll.orderByChild("id_usuario").equalTo(id(MyDiagnosis.this));
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        

                        for (int x =0; x<obj.getChildrenCount();x++){
                            //nodo id
                            System.out.println(obj.getKey()+"==>" +obj.getValue());
                            //valores dentro del nodo
                            HashMap<String,Object> o = (HashMap<String,Object>) obj.getValue();
                            for (Map.Entry<String, Object> entry : o.entrySet()){
                                System.out.println(entry.getKey()+"==>"+entry.getValue());
                                if(!entry.getKey().equals("id") || !entry.getKey().equals("id_usuario") || !entry.getKey().equals("fecha")){

                                    if(entry.getValue().getClass().toString().equals("class java.lang.Long")){
                                        Long valor =(Long) entry.getValue() ;
                                        int intValue = valor.intValue();
                                        if(intValue==1){
                                            count++;

                                        }
                                    }
                                    System.out.println();

                                }

                                if(entry.getKey().equals("fecha")){
                                    String f =entry.getValue().toString();
                                    String d = f.substring(0,2);
                                    String m = f.substring(3,5);
                                    String y =f.substring(6,10);
                                    mes =Integer.parseInt(m);
                                    anio = Integer.parseInt(y);
                                    dia = Integer.parseInt(d);

                                }

                                lineEntries.add(new BarEntry((float) dia,(float)count/o.size()));



                            }
                            System.out.println(count+"TOTAL DE SINTOMAS");
                            if((count/o.size())>7 && (count/o.size())==o.size()){

                                evaluate=1;
                            }else{
                                evaluate=0;
                            }
                        }

                    }
                    lineDataSet = new BarDataSet(lineEntries, getResources().getString(R.string.diagnosis));
                    Description d = new Description();
                    d.setText(getResources().getString(R.string.meses));
                    // Asociamos al gráfico
                    BarData lineData = new BarData();
                    lineData.addDataSet(lineDataSet);
                    lineChart.setData(lineData);
                    lineChart.setDescription(d);
                    lineChart.animateXY(2000, 2000);
                    lineChart.invalidate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}

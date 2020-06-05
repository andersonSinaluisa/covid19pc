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
import com.today.covid_19puntoscriticos.Model.Sintomas;
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
import static com.today.covid_19puntoscriticos.Preferences.MainPreference.getN_sintomas;
import static com.today.covid_19puntoscriticos.Preferences.MainPreference.getPositionBoolean;
import static com.today.covid_19puntoscriticos.Preferences.MainPreference.id;
import static com.today.covid_19puntoscriticos.Preferences.MainPreference.username;
public class MyDiagnosis extends AppCompatActivity {

    private BarChart lineChart;
    private BarDataSet lineDataSet;
    private ImageView img;
    private FirebaseUser currentUser=null;
    private TextView user;
    private TextView recomendaciones;
    private TextView n_sintomas;
    final Firebase db = new Firebase();
    private int count;//y
    private int mes; //x
    private int anio;
    private int dia;

    private int div;

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
        n_sintomas =(TextView) findViewById(R.id.text_n_sintomas);




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


            if(getN_sintomas(MyDiagnosis.this)!=0){

                n_sintomas.setText(getResources().getString(R.string.start_details_symptoms)+" "+getN_sintomas(MyDiagnosis.this));
                if(getN_sintomas(MyDiagnosis.this)<7){
                    n_sintomas.setTextColor(R.color.colorAccent);
                }else{
                    n_sintomas.setTextColor(R.color.red);

                }
            }else{
                n_sintomas.setTextColor(R.color.colorAccent);
                n_sintomas.setText(getResources().getString(R.string.negative_n_simptoms));
            }


        loadData();


        System.out.println(evaluate+"EVALUACION");
            if(getPositionBoolean(MyDiagnosis.this)){
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
        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String fecha = sfd.format(date);

        final String year = fecha.substring(6,10);
        final String month = fecha.substring(3,5);
        final String day = fecha.substring(0,2);
        //final String hours = fecha.substring(11,13);
       // final String minutes =fecha.substring(14,16);
        //final String seconds = fecha.substring(17,19);

        final int _year = Integer.parseInt(year);
        final int _month = Integer.parseInt(month);
        //final int _hours = Integer.parseInt(hours);
        //final int _minutes = Integer.parseInt(minutes);
        //final int _seconds = Integer.parseInt(seconds);

        
        final DatabaseReference poll = db.getmDatabase("Numero_Sintomas");
        Query q = poll.orderByChild("id_usuario").equalTo(id(MyDiagnosis.this));
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        Sintomas s = obj.getValue(Sintomas.class);

                        assert s != null;
                        String mes = s.getFecha().substring(3,5);
                        String dia = s.getFecha().substring(0,2);
                        String anio = s.getFecha().substring(6,10);
                       // String hours = s.getFecha().substring(12,14);
                        //String minutes =s.getFecha().substring(15,17);
                        //String seconds = s.getFecha().substring(16,18);
                        final int mesInt = Integer.parseInt(mes);
                        final int diaInt = Integer.parseInt(dia);
                        final int anioInt = Integer.parseInt(anio);
                       // final int hoursInt = Integer.parseInt(hours);
                       // final int minutesInt = Integer.parseInt(minutes);

                        if(mesInt==_month && anioInt==_year){

                            lineEntries.add(new BarEntry((float) diaInt,(float)s.getNumero_sintomas()));

                        }
                    }
                    //System.out.println(count+"TOTAL DE SINTOMAS");
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}

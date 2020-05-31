package com.today.covid_19puntoscriticos.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.today.covid_19puntoscriticos.Preferences.MainPreference.email;
import static com.today.covid_19puntoscriticos.Preferences.MainPreference.id;
import static com.today.covid_19puntoscriticos.Preferences.MainPreference.username;

public class MyDiagnosis extends AppCompatActivity {

    private LineChart lineChart;
    private LineDataSet lineDataSet;
    private CircleImageView img;
    private FirebaseUser currentUser=null;
    private TextView user;

    final Firebase db = new Firebase();
    private int count;

    private ArrayList<Entry> lineEntries = new ArrayList<Entry>();
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_diagnosis);
        lineChart = findViewById(R.id.lineChart);
        img = findViewById(R.id.profile_image);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        user = (TextView)findViewById(R.id.username);

            img.setImageResource(R.drawable.ic_account);

            if(username(MyDiagnosis.this).equals("")){
                user.setText(email(MyDiagnosis.this));
            }else {
                user.setText(username(MyDiagnosis.this));

            }



        loadData();


        for (int i = 0; i<11; i++){
            float y = (int) (Math.random() * 8) + 1;
            lineEntries.add(new Entry((float) i,(float)y));
        }

        // Unimos los datos al data set
        lineDataSet = new LineDataSet(lineEntries, "Platzi");

        // Asociamos al gráfico
        LineData lineData = new LineData();
        lineData.addDataSet(lineDataSet);
        lineChart.setData(lineData);
    }

    private void loadData() {

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
                                if(!entry.getKey().equals("id") || !entry.getKey().equals("id_usuario")){
                                    if(entry.getValue().equals(true)){
                                        count++;
                                        System.out.println(count);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}

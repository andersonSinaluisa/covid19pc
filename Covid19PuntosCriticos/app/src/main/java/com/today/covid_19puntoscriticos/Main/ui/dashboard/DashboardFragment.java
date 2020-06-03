package com.today.covid_19puntoscriticos.Main.ui.dashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.today.covid_19puntoscriticos.Main.ui.country.CovidCountry;
import com.today.covid_19puntoscriticos.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DashboardFragment extends Fragment {
    private PieChart pieChart;
    private LineChart lineChart;
    private ProgressBar progressBar;
    private TextView tvTotalConfirmed, tvTotalDeaths, tvTotalRecovered, tvLasUpdated;
    private  String[]months=new String[]{"CONFIRMADOS","MUERTOS","RECUPERADOS",};
    private  int[]sale = new int[]{90,50,70};
    private  int[]colors=new int[]{Color.BLACK,Color.RED,Color.BLUE};
    private DashboardViewModel dashboardViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        tvTotalConfirmed=root.findViewById(R.id.tvLabelTotalConfirmed);
        tvTotalDeaths=root.findViewById(R.id.tvTotalDeaths);
        tvTotalRecovered=root.findViewById(R.id.tvTotalRecovered);
        lineChart = root.findViewById(R.id.lineChart);
        progressBar=root.findViewById(R.id.progress_circular_home);
        pieChart=root.findViewById(R.id.pieChart);

        createCharts();
        getData();
        return root;
    }

    private void getData() {
        RequestQueue queue= Volley.newRequestQueue(getActivity());
        final String url="https://corona.lmao.ninja/v2/all";
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {

                    JSONObject jsonObject = new JSONObject(response.toString());

                    tvTotalConfirmed.setText(jsonObject.getString("cases"));
                    tvTotalDeaths.setText(jsonObject.getString("deaths"));
                    tvTotalRecovered.setText(jsonObject.getString("recovered"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.d("ERROR RESPUESTA",error.toString());
            }
        });

        queue.add(stringRequest);
    }
    private Chart getSameChart(Chart chart, String descripcion, int textColor, int background, int animateY){
        chart.getDescription().setText(descripcion);
        chart.getDescription().setTextColor(textColor);
        chart.getDescription().setTextSize(10);
        chart.setBackgroundColor(background);
        chart.animateY(animateY);
        legend(chart);
        return chart;
    }

    private void legend(Chart chart){
        Legend legend=chart.getLegend();
        legend.setForm(Legend.LegendForm.DEFAULT);
        legend.setTextSize(10);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);


        ArrayList<LegendEntry> entries=new ArrayList<>();
        for (int i=0; i<months.length;i++){
            LegendEntry entry= new LegendEntry();
            entry.formColor=colors[i];
            entry.label= months[i];
            entries.add(entry);
        }
        legend.setCustom(entries);
    }

    private ArrayList<PieEntry>getPieEntries(){
        ArrayList<PieEntry>entries=new ArrayList<>();
        for (int i=0; i<sale.length;i++)
            entries.add(new PieEntry(sale[i]));
        return entries;
    }

    private ArrayList<Entry> getLineEntries() {
        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < sale.length; i++)
            entries.add(new Entry(i, sale[i]));
        return entries;
    }
    //Eje horizontal o eje X
    private void axisX(XAxis axis){
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(months));
    }
    //Eje Vertical o eje Y lado izquierdo
    private void axisLeft(YAxis axis){
        axis.setSpaceTop(30);
        axis.setAxisMinimum(0);
        axis.setGranularity(20);
    }
    //Eje Vertical o eje Y lado Derecho
    private void axisRight(YAxis axis){
        axis.setEnabled(false);
    }
    private void createCharts(){


        pieChart=(PieChart)getSameChart(pieChart,"COVID-19",Color.BLACK,Color.LTGRAY,3000);
        pieChart.setHoleRadius(8);
        pieChart.setTransparentCircleRadius(10);
        pieChart.setData(getPieData());
        pieChart.invalidate();
        //pieChart.getLegend().setEnabled(false);
        //pieChart.setDrawHoleEnabled(false);


        lineChart = (LineChart) getSameChart(lineChart, "COVID-19", Color.BLUE, Color.LTGRAY, 3000);
        lineChart.setData(getLineData());
        //lineChart.getLegend().setEnabled(false);
        lineChart.invalidate();
        axisX(lineChart.getXAxis());
        axisLeft(lineChart.getAxisLeft());
        axisRight(lineChart.getAxisRight());


    }

    private DataSet getDataSame(DataSet dataSet){
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(10);
        return dataSet;
    }
    private DataSet getData(DataSet dataSet){
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(10);
        return dataSet;
    }

    private PieData getPieData(){
        PieDataSet pieDataSet=(PieDataSet)getData(new PieDataSet(getPieEntries(),""));
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueFormatter(new PercentFormatter());
        return new PieData(pieDataSet);

    }
    private LineData getLineData() {
        LineDataSet lineDataSet = (LineDataSet) getDataSame(new LineDataSet(getLineEntries(), ""));
        lineDataSet.setLineWidth(2.5f);
        //Color de los circulos de la grafica
        lineDataSet.setCircleColors(colors);
        //Tamaño de los circulos de la grafica
        lineDataSet.setCircleRadius(5f);
        //Sombra grafica
        lineDataSet.setDrawFilled(true);
        //Estilo de la linea picos(linear) o curveada(cubic) cuadrada(Stepped)
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        return new LineData(lineDataSet);
    }
}

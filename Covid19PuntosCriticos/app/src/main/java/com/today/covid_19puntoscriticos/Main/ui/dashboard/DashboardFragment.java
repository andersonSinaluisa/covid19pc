package com.today.covid_19puntoscriticos.Main.ui.dashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.today.covid_19puntoscriticos.R;

import java.util.ArrayList;


public class DashboardFragment extends Fragment {
    private PieChart pieChart;
    private BarChart barChart;
    private  String[]months=new String[]{"Enero","Febrero","Marzo","Abril","Mayo"};
    private  int[]sale=new int[]{17,14,12,10,15};
    private  int[]colors=new int[]{Color.BLACK,Color.RED,Color.GREEN,Color.BLUE,Color.LTGRAY};

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

        barChart=root.findViewById(R.id.barChart);
        pieChart=root.findViewById(R.id.pieChart);
        createCharts();
        return root;
    }
    private Chart getSameChart(Chart chart, String descripcion, int textColor, int background, int animateY){
        chart.getDescription().setText(descripcion);
        chart.getDescription().setTextColor(textColor);
        chart.getDescription().setTextSize(15);
        chart.setBackgroundColor(background);
        chart.animateY(animateY);
        legend(chart);
        return chart;
    }

    private void legend(Chart chart){
        Legend legend=chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);


        ArrayList<LegendEntry> entries=new ArrayList<>();
        for (int i=0; i<months.length;i++){
            LegendEntry entry= new LegendEntry();
            entry.formColor=colors[i];
            entry.label=months[i];
            entries.add(entry);
        }
        legend.setCustom(entries);
    }
    private ArrayList<BarEntry>getBarEntries(){
        ArrayList<BarEntry> entries =new ArrayList<>();
        for (int i=0; i< sale.length;i++)
            entries.add(new BarEntry(i,sale[i]));
        return entries;
    }
    private ArrayList<PieEntry>getPieEntries(){
        ArrayList<PieEntry>entries=new ArrayList<>();
        for (int i=0; i<sale.length;i++)
            entries.add(new PieEntry(sale[i]));
        return entries;
    }
    private void axisX(XAxis axis){
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(months));
        // axis.setEnabled(false);
    }
    private void axisLeft(YAxis axis){
        axis.setSpaceTop(20);
        axis.setAxisMaximum(0);
        //axis.setGranularity(3);
    }
    private void axisRight(YAxis axis){
        axis.setEnabled(false);
    }
    private void createCharts(){
        barChart=(BarChart)getSameChart(barChart, "Series",Color.RED,Color.CYAN,3000);
        barChart.setDrawGridBackground(true);
        barChart.setDrawBarShadow(true);
        barChart.setData(getBarData());
        barChart.invalidate();
        axisX(barChart.getXAxis());
        axisLeft(barChart.getAxisLeft());
        axisRight(barChart.getAxisRight());
        //barChart.getLegend().setEnabled(false);

        pieChart=(PieChart)getSameChart(pieChart,"Ventas",Color.GRAY,Color.MAGENTA,3000);
        pieChart.setHoleRadius(10);
        pieChart.setTransparentCircleRadius(12);
        pieChart.setData(getPieData());
        pieChart.invalidate();
        //pieChart.setDrawHoleEnabled(false);


    }
    private DataSet getData(DataSet dataSet){
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(8);
        return dataSet;
    }
    private BarData getBarData(){
        BarDataSet barDataSet=(BarDataSet)getData(new BarDataSet(getBarEntries(),""));
        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.40f);
        return barData;
    }
    private PieData getPieData(){
        PieDataSet pieDataSet=(PieDataSet)getData(new PieDataSet(getPieEntries(),""));
        pieDataSet.setSliceSpace(1);
        pieDataSet.setValueFormatter(new PercentFormatter());
        return new PieData(pieDataSet);

    }
}

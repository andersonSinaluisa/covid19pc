package com.today.covid_19puntoscriticos.Main.ui.country;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.today.covid_19puntoscriticos.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CountryFragment extends Fragment {

RecyclerView rvCovidCountry;
ProgressBar progressBar;
TextView tvTotalCountry;
TextView net_work;
CovidCountryAdapter covidCountryAdapter;

LinearLayout linearLayoutTotalCountires;

private static final String TAG = CountryFragment.class.getSimpleName();

        List<CovidCountry> covidCountries;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_country, container, false);
        //set has option menu as true because we have menu
        setHasOptionsMenu(true);

        // call view
        linearLayoutTotalCountires= (LinearLayout) root.findViewById(R.id.linearLayoutTotalCountires);
        rvCovidCountry = root.findViewById(R.id.rvCovidCountry);
        progressBar= root.findViewById(R.id.progress_circular_country);
        tvTotalCountry = root.findViewById(R.id.tvTotalCountries);
        rvCovidCountry.setLayoutManager(new LinearLayoutManager(getActivity()));
        net_work =(TextView) root.findViewById(R.id.net_work_country);
        DividerItemDecoration dividerItemDecoration= new DividerItemDecoration(rvCovidCountry.getContext(), DividerItemDecoration.VERTICAL );
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.line_divider));
        rvCovidCountry.addItemDecoration(dividerItemDecoration);
        net_work.setVisibility(View.INVISIBLE);


        //call list
        covidCountries = new ArrayList<>();


        // call volley method
        getDataFromServer();

        return root;
    }

    private void showRecyclerView(){
         covidCountryAdapter = new CovidCountryAdapter(covidCountries, getActivity());
        rvCovidCountry.setAdapter(covidCountryAdapter);

        ItemClickSupport.addTo(rvCovidCountry).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedCovidCountry(covidCountries.get(position));
            }
        });


    }

    private void showSelectedCovidCountry(CovidCountry covidCountry){
        Intent covidCountryDetail = new Intent(getActivity(),CovidCountryDatail.class);
        covidCountryDetail.putExtra("EXTRA_COVID", covidCountry);
        startActivity(covidCountryDetail);


    }


    private void getDataFromServer() {
        String url= "https://corona.lmao.ninja/v2/countries";

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null) {
                    Log.e(TAG, "onResponse" + response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);

                            JSONObject countryInfo = data.getJSONObject("countryInfo");
                            covidCountries.add(new CovidCountry(data.getString("country"), data.getString("cases"),
                                    data.getString("todayCases"), data.getString("deaths"), data.getString("todayDeaths"),
                                    data.getString("recovered"), data.getString("active"), data.getString("critical"),
                                    countryInfo.getString("flag")
                                    ));
                        }
                        tvTotalCountry.setText(jsonArray.length()+" countries");
                        showRecyclerView();
                        progressBar.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                linearLayoutTotalCountires.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                net_work.setVisibility(View.VISIBLE);
                Log.e(TAG, "onResponse:" + error);
            }
        });
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.adicionar1);
        SearchView searchView = new SearchView(getActivity());
        searchView.setQueryHint("Buscar..");
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (covidCountryAdapter != null){
                    covidCountryAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        searchItem.setActionView(searchView);
        super.onCreateOptionsMenu(menu, inflater);
    }
}

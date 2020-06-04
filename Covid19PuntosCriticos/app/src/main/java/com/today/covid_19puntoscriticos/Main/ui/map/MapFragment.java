package com.today.covid_19puntoscriticos.Main.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.today.covid_19puntoscriticos.Config.Firebase;
import com.today.covid_19puntoscriticos.Model.HistorialPosition;
import com.today.covid_19puntoscriticos.R;
import com.today.covid_19puntoscriticos.Services.ServicesUbication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MapFragment extends Fragment  implements GoogleMap.OnCameraIdleListener,
        OnMapReadyCallback {

    private MapViewModel mapViewModel;
    private MapView mMapView;
    private GoogleMap mMap;

    private HeatmapTileProvider provider;
    private ArrayList<LatLng>  list = new ArrayList<LatLng>();


    View contextView = null;
    private int permissionchecked;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapViewModel =
                ViewModelProviders.of(this).get(MapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        //-------------------variable de permisos a la ubicacion-------------------/
        permissionchecked = ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_FINE_LOCATION);

        LocationManager locationManager = (LocationManager) root.getContext().getSystemService(Context.LOCATION_SERVICE);

        getZones();





        if(!(permissionchecked == PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);


        }else{
            assert locationManager != null;
            if(locationManager.isProviderEnabled("gps")){
                    getActivity().startService(new Intent(root.getContext(), ServicesUbication.class));

                }else{
                    dialogGps();

            }
        }


        return root;
    }

    private void dialogGps() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.dialog_gps, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCancelable(false);
        Button btnyes = (Button) view.findViewById(R.id.btn_gps_enable);
        Button btnno = (Button) view.findViewById(R.id.btn_gps_diseable);
        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Intent gpsOptionsIntent = new Intent(
                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(gpsOptionsIntent);


            }
        });

        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onCameraIdle() {





    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setBuildingsEnabled(false);
        mMap.setMaxZoomPreference(15);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(19.420925, -99.142559), 3));
        mMap.setOnCameraIdleListener(this);
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(contextView.getContext(), R.raw.raw));
        } catch (Resources.NotFoundException e) {
            Log.d("", e.toString());
        }
        addHeatMap();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        contextView = view;
        mMapView = view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(this);
        super.onViewCreated(view, savedInstanceState);


    }

    int[] colors = {
            Color.rgb(102, 225, 0), // green
            Color.rgb(255, 0, 0)    // red
    };

    float[] startPoints = {
            0.2f, 1f
    };

    private void addHeatMap() {


        // Get the data: latitude/longitude positions of police stations.



        Gradient gradient = new Gradient(colors, startPoints);
        // Create a heat map tile provider, passing it the latlngs of the police stations.
        if(list.size()>0){
            // Create the tile provider.
            provider = new HeatmapTileProvider.Builder().data(list).gradient(gradient).build();
            System.out.println(provider+" PROVIDER");
            // Add the tile overlay to the map.
            TileOverlay mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(provider));
            System.out.println(mOverlay+" mOverlay");
        }



    }

    private ArrayList<LatLng> readItems(int resource) throws JSONException {
        ArrayList<LatLng> list = new ArrayList<LatLng>();
        InputStream inputStream = getResources().openRawResource(resource);
        String json = new Scanner(inputStream).useDelimiter("\\A").next();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {

            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("lat");
            double lng = object.getDouble("lng");
            list.add(new LatLng(lat, lng));
        }
        return list;
    }

    private void getZones(){

        final Firebase db = new Firebase();
        final DatabaseReference posicion = db.getmDatabase("Historial");
        posicion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    list.clear();
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        HistorialPosition h = obj.getValue(HistorialPosition.class);

                        assert h != null;
                        System.out.println(h.getLatitud()+"--->"+h.getLongitud());
                        list.add(new LatLng(h.getLatitud(),h.getLongitud()));
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        System.out.println(list.size()+"TAMAÑO LISTA");

    }

}

package com.today.covid_19puntoscriticos.Main.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.today.covid_19puntoscriticos.R;
import com.today.covid_19puntoscriticos.Services.ServicesUbication;


public class MapFragment extends Fragment  implements GoogleMap.OnCameraIdleListener,
        OnMapReadyCallback {

    private MapViewModel mapViewModel;
    private MapView mMapView;
    private GoogleMap mMap;

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




        //si tiene el permiso
        if(permissionchecked== PackageManager.PERMISSION_GRANTED){
            //inicia el servicio
            getActivity().startService(new Intent(root.getContext(), ServicesUbication.class));
        }else{
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }


        return root;
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
            Log.d("EnriqueLC", e.toString());
        }
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
}

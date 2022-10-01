package com.example.queuemanager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;


import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.queuemanager.databinding.ActivityMapsBinding;

public class maps extends FragmentActivity implements OnMapReadyCallback{


    private GoogleMap mMap;
    private Location currentLocation;
    private database.basic_data estData;
    private ActivityMapsBinding binding;
    LatLngBounds.Builder builder = new LatLngBounds.Builder();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        business bus = (business) getApplicationContext();
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database.e est_type = (database.e) getIntent().getSerializableExtra("establishment");
        estData = (database.basic_data) bus.data.est_data.get(est_type);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle));

        for (int i=0; i<estData.instances.size(); i++)
        {
            LatLng loc = new LatLng(estData.instances.get(i).lat,estData.instances.get(i).lon);
            MarkerOptions marker = new MarkerOptions().position(loc).title(estData.instances.get(i).name);
            mMap.addMarker(marker);
            builder.include(marker.getPosition());
        }

    LatLngBounds bounds = builder.build();
    int padding = 200;
    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
    mMap.animateCamera(cu);
    }
}
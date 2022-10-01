package com.example.queuemanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

import java.time.LocalTime;

public class maps extends FragmentActivity implements OnMapReadyCallback{

    private Intent intent;
    private Button btnConfirm;
    private GoogleMap mMap;
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

        btnConfirm = findViewById(R.id.btnConfirm);

        if (btnConfirm != null)
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View view) {
                    boolean open = LocalTime.now().isAfter(estData.start) && LocalTime.now().isBefore(estData.end);
                    open = open || (LocalTime.now().isBefore(estData.end) && estData.end.isBefore(estData.start));

                    if (open) {
                        startActivity(intent);
                    }
                    else{
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(maps.this);
                        dlgAlert.setMessage("This establishment is closed! Working hours: from " + estData.start.toString() + " to " + estData.end.toString() + ".");
                        dlgAlert.setTitle("Error!");
                        dlgAlert.setPositiveButton("OK", null);
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();
                    }
                }
            });

        database.e est_type = (database.e) getIntent().getSerializableExtra("establishment");
        estData = (database.basic_data) bus.data.est_data.get(est_type);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        intent = new Intent(maps.this, queue.class);
        intent.putExtra("establishment", est_type);
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
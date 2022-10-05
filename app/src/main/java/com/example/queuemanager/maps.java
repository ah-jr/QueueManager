package com.example.queuemanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.queuemanager.databinding.ActivityMapsBinding;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class maps extends FragmentActivity implements OnMapReadyCallback{

    private Intent intent;
    private Button btnConfirm;
    private GoogleMap mMap;
    private database.basic_data estData;
    private ActivityMapsBinding binding;
    LatLngBounds.Builder builder = new LatLngBounds.Builder();
    Map<String, Integer> markers = new HashMap<String, Integer>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        business bus = (business) getApplicationContext();
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setBackgroundColor(Color.parseColor("#808080"));

        if (btnConfirm != null)
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View view) {
                    boolean open = LocalTime.now().isAfter(estData.start) && LocalTime.now().isBefore(estData.end);
                    open = open || (LocalTime.now().isBefore(estData.end) && estData.end.isBefore(estData.start));
                    open = open || (LocalTime.now().isAfter(estData.start) && estData.end.isBefore(estData.start));

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
        LatLng loc = null;

        for (int i=0; i<estData.instances.size(); i++)
        {
            loc = new LatLng(estData.instances.get(i).lat,estData.instances.get(i).lon);
            MarkerOptions marker = new MarkerOptions().position(loc).title(estData.instances.get(i).name);
            Marker mkr = mMap.addMarker(marker);
            builder.include(marker.getPosition());

            markers.put(mkr.getId(), i);
        }

        int padding = 0;

        if (markers.size() == 1)
        {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 13));
        }
        else
        {
            padding = 200;
            LatLngBounds bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mMap.animateCamera(cu);
        }


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                btnConfirm.setBackgroundColor(Color.parseColor("#808080"));
            }
        });


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override public boolean onMarkerClick(Marker marker) {
                //int id = markers.get(marker.getId());
                btnConfirm.setBackgroundColor(Color.parseColor("#4CAF50"));
                return false;
            }
        });
    }
}
package com.example.queuemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class establishment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment);

        ImageButton imgE1 = (ImageButton) findViewById(R.id.imgE1);
        ImageButton imgE2 = (ImageButton) findViewById(R.id.imgE2);
        ImageButton imgE3 = (ImageButton) findViewById(R.id.imgE3);
        ImageButton imgE4 = (ImageButton) findViewById(R.id.imgE4);

        Intent i = new Intent(establishment.this, maps.class);

        imgE1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("establishment", database.e.OUTBACK);
                startActivity(i);
            }
        });

        imgE2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("establishment", database.e.CUCKO);
                startActivity(i);
            }
        });

        imgE3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("establishment", database.e.APPLEBEES);
                startActivity(i);
            }
        });

        imgE4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("establishment", database.e.PEPSI);
                startActivity(i);
            }
        });
    }
}
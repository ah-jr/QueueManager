package com.example.queuemanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalTime;


public class mapview extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapview);
        business bus = (business) getApplicationContext();
        TextView txtTest = findViewById(R.id.txtTest);
        Button btnTest = findViewById(R.id.btnTest);

        //database.basic_data estData = (database.basic_data) bus.data.est_data.get(database.e.OUTBACK);
        //LocalTime time = estData.newVacancies.get(0);
        //txtTest.setText(time.toString());


        database.e est_type = (database.e) getIntent().getSerializableExtra("establishment");

        Intent i = new Intent(mapview.this, queue.class);
        i.putExtra("establishment", est_type);


        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
            }
        });
    }
}

package com.example.queuemanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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

        database.e est_type = (database.e) getIntent().getSerializableExtra("establishment");
        database.basic_data estData = (database.basic_data) bus.data.est_data.get(est_type);

        Intent i = new Intent(mapview.this, queue.class);
        i.putExtra("establishment", est_type);


        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean open = LocalTime.now().isAfter(estData.start) && LocalTime.now().isBefore(estData.end);
                open = open || (LocalTime.now().isBefore(estData.end) && estData.end.isBefore(estData.start));

                if (open) {
                    startActivity(i);
                }
                else{
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(mapview.this);
                    dlgAlert.setMessage("This establishment is closed! Working hours: from " + estData.start.toString() + " to " + estData.end.toString() + ".");
                    dlgAlert.setTitle("Error!");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }


            }
        });
    }
}

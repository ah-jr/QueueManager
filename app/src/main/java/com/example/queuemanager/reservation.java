package com.example.queuemanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class reservation extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        Button btnNewReservation = findViewById(R.id.btnNewReservation);

        business bus = (business) getApplicationContext();

        database.e est_type = (database.e) getIntent().getSerializableExtra("establishment");
        database.basic_data estData = (database.basic_data) bus.data.est_data.get(est_type);

        EditText edtPeople = findViewById(R.id.edtPeopleCount);
        EditText edtDate = findViewById(R.id.edtDate);
        EditText edtTime = findViewById(R.id.edtTime);

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);

        List<database.reservation> list = null;
        if (bus.data.reservations.get(est_type) != null) {
            list = (List<database.reservation>) bus.data.reservations.get(est_type);

            for (int i = 0; i < list.size(); i++) {
                TextView textView = new TextView(reservation.this);

                String resStr = "For " + list.get(i).nr.toString() + " People\nDate: " + list.get(i).date.toString() + "\nTime: "+ list.get(i).time.toString();

                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.parseColor("#000000"));
                textView.setTextSize(25);
                textView.setBackground(getResources().getDrawable(R.drawable.round_bg));
                textView.setTypeface(null, Typeface.BOLD);
                textView.setText(resStr);
                layout.addView(textView);
            }
        }

        btnNewReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = new TextView(reservation.this);

                String resStr = "For " + edtPeople.getText().toString() + " People\nDate: " + edtDate.getText().toString() + "\nTime: "+ edtTime.getText().toString();
                textView.setText(resStr);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.parseColor("#000000"));
                textView.setTextSize(25);
                textView.setBackground(getResources().getDrawable(R.drawable.round_bg));
                textView.setTypeface(null, Typeface.BOLD);
                layout.addView(textView);

                bus.data.addReservation(est_type, edtPeople.getText().toString(), edtDate.getText().toString(), edtTime.getText().toString());

            }
        });
    }
}
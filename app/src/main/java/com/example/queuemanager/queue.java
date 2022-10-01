package com.example.queuemanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.time.LocalTime;


public class queue extends AppCompatActivity {

    ImageButton imgEq1, imgEq2, imgEq3, imgEq4;
    public TextView txtPos;
    public int pos = 0;
    public boolean joined;
    public business bus;
    public  database.basic_data estData;
    private final int interval = 2000;
    private Handler handler = new Handler();

    private Runnable runnable = new Runnable(){
        @RequiresApi(api = Build.VERSION_CODES.O)
        public void run() {
            if (joined) {
                if (bus.data.newVacancy())
                    pos--;
            }

            if (pos == 0){
                txtPos.setTextSize(40);
                txtPos.setText("Your turn!");

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(queue.this);
                dlgAlert.setMessage("Your turn has come! Go to check in and enjoy your event!");
                dlgAlert.setTitle("Nice!");
                dlgAlert.setPositiveButton("Ok", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
            }
            else {
                txtPos.setText(Integer.toString(pos));
                handler.postDelayed(runnable, interval);
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);
        bus = (business) getApplicationContext();
        ImageView imgCover = findViewById(R.id.imgCover);
        Button btnJoin = findViewById(R.id.btnJoin);
        TextView txtSizeInfo= findViewById(R.id.txtSizeInfo);
        txtPos = findViewById(R.id.txtPos);
        joined = false;

        imgEq1 = findViewById(R.id.imgEq1);
        imgEq2 = findViewById(R.id.imgEq2);
        imgEq3 = findViewById(R.id.imgEq3);
        imgEq4 = findViewById(R.id.imgEq4);

        database.e est_type = (database.e) getIntent().getSerializableExtra("establishment");
        estData = (database.basic_data) bus.data.est_data.get(est_type);

        Drawable img = getResources().getDrawable(database.getDrawableID(est_type));
        imgCover.setImageDrawable(img);

        pos = bus.data.getQueueSize(estData);
        txtPos.setText(Integer.toString(pos));

        handler.postAtTime(runnable, System.currentTimeMillis()+interval);
        handler.postDelayed(runnable, interval);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!joined) {
                    txtSizeInfo.setText("Your position in queue:");
                    txtSizeInfo.setTypeface(null, Typeface.BOLD);
                    txtSizeInfo.setTextColor(Color.parseColor("#000000"));
                    txtPos.setTextColor(Color.parseColor("#000000"));
                    btnJoin.setText("Leave queue");
                    btnJoin.setBackgroundColor(Color.parseColor("#F44336"));

                    txtPos.setTextSize(150);
                    txtPos.setText(Integer.toString(pos));

                    if (pos == 0)
                        handler.postDelayed(runnable, interval);
                }
                else {
                    txtSizeInfo.setText("Current queue size:");
                    txtSizeInfo.setTypeface(null, Typeface.NORMAL);
                    txtSizeInfo.setTextColor(Color.parseColor("#808080"));
                    txtPos.setTextColor(Color.parseColor("#808080"));
                    btnJoin.setText("Join queue");
                    btnJoin.setBackgroundColor(Color.parseColor("#4CAF50"));

                    if (pos == 0)
                        handler.postDelayed(runnable, interval);

                    pos = Math.max(bus.data.getQueueSize(estData), pos);
                    txtPos.setText(Integer.toString(pos));
                    txtPos.setTextSize(150);
                }

                joined = !joined;
            }
        });

        Intent i = new Intent(queue.this, mapview.class);

        imgEq1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("establishment", database.e.OUTBACK);
                startActivity(i);
            }
        });

        imgEq2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("establishment", database.e.CUCKO);
                startActivity(i);
            }
        });

        imgEq3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("establishment", database.e.APPLEBEES);
                startActivity(i);
            }
        });

        imgEq4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("establishment", database.e.PEPSI);
                startActivity(i);
            }
        });
    }
}
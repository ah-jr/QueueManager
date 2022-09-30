package com.example.queuemanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.time.LocalTime;


public class queue extends AppCompatActivity {

    public TextView txtPos;
    public int pos = 1;
    private final int interval = 1000;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable(){
        public void run() {
            txtPos.setText(Integer.toString(pos));
            pos++;

            handler.postDelayed(runnable, interval);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);
        business bus = (business) getApplicationContext();
        ImageView imgCover = findViewById(R.id.imgCover);
        Button btnJoin = findViewById(R.id.btnJoin);
        TextView txtSizeInfo= findViewById(R.id.txtSizeInfo);
        txtPos = findViewById(R.id.txtPos);
        final boolean[] joined = {false};

        database.e est_type = (database.e) getIntent().getSerializableExtra("establishment");
        database.basic_data estData = (database.basic_data) bus.data.est_data.get(est_type);

        Drawable img = getResources().getDrawable(database.getDrawableID(est_type));
        imgCover.setImageDrawable(img);

        handler.postAtTime(runnable, System.currentTimeMillis()+interval);
        handler.postDelayed(runnable, interval);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!joined[0]) {
                    txtSizeInfo.setText("Your position in queue:");
                    txtSizeInfo.setTypeface(null, Typeface.BOLD);
                    txtSizeInfo.setTextColor(Color.parseColor("#000000"));
                    txtPos.setTextColor(Color.parseColor("#000000"));
                    btnJoin.setText("Leave queue");
                    btnJoin.setBackgroundColor(Color.parseColor("#F44336"));
                }
                else {
                    txtSizeInfo.setText("Current queue size:");
                    txtSizeInfo.setTypeface(null, Typeface.NORMAL);
                    txtSizeInfo.setTextColor(Color.parseColor("#808080"));
                    txtPos.setTextColor(Color.parseColor("#808080"));
                    btnJoin.setText("Join queue");
                    btnJoin.setBackgroundColor(Color.parseColor("#4CAF50"));
                }

                joined[0] = !joined[0];
            }
        });
    }
}
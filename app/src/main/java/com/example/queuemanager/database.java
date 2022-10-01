package com.example.queuemanager;

import android.app.Application;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.time.LocalTime;
import java.util.Random;

public class database extends Application {

    public enum e{
        OUTBACK, APPLEBEES, CUCKO, PEPSI
    }

    public class instance{
        public String name;
        public double lat, lon;
    }

    public class basic_data{
        public LocalTime start, end;
        public int queueMin, queueMax;
        public List<instance> instances = new ArrayList<>();
    }

    public class user_data{
        public String email;
        public String password;
    }

    public Dictionary est_data = new Hashtable();
    public List<user_data> usr_data = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public database(){
        ////////////////////////////////////////////////////////////////////////////////////////////////
        // Outback Data:
        basic_data outback_data = new basic_data();
        outback_data.start = LocalTime.parse("17:00:00");
        outback_data.end = LocalTime.parse("23:00:00");
        outback_data.queueMin = 3;
        outback_data.queueMax = 10;

        instance inst1 = new instance();
        inst1.name = "Outback - Shopping Iguatemi";
        inst1.lat = -30.025255769074885;
        inst1.lon = -51.16278995396791;
        outback_data.instances.add(inst1);

        instance inst2 = new instance();
        inst2.name = "Outback - Barra Shopping Sul";
        inst2.lat = -30.084465444504747;
        inst2.lon = -51.24579548602659;
        outback_data.instances.add(inst2);

        instance inst3 = new instance();
        inst3.name = "Outback - Praia de Belas";
        inst3.lat = -30.049486117702806;
        inst3.lon = -51.22928118601423;
        outback_data.instances.add(inst3);

        est_data.put(e.OUTBACK, outback_data);

        ////////////////////////////////////////////////////////////////////////////////////////////////
        // Applebee's Data:
        basic_data applebees_data = new basic_data();
        applebees_data.start = LocalTime.parse("10:00:00");
        applebees_data.end = LocalTime.parse("16:00:00");
        applebees_data.queueMin = 4;
        applebees_data.queueMax = 9;

        est_data.put(e.APPLEBEES, applebees_data);

        ////////////////////////////////////////////////////////////////////////////////////////////////
        // Cucko Data:
        basic_data cucko_data = new basic_data();
        cucko_data.start = LocalTime.parse("21:00:00");
        cucko_data.end = LocalTime.parse("04:00:00");
        cucko_data.queueMin = 9;
        cucko_data.queueMax = 15;

        est_data.put(e.CUCKO, cucko_data);

        ////////////////////////////////////////////////////////////////////////////////////////////////
        // Pepsi Data:
        basic_data pepsi_data = new basic_data();
        pepsi_data.start = LocalTime.parse("20:00:00");
        pepsi_data.end = LocalTime.parse("03:00:00");
        pepsi_data.queueMin = 50;
        pepsi_data.queueMax = 90;

        est_data.put(e.PEPSI, pepsi_data);

        ////////////////////////////////////////////////////////////////////////////////////////////////
        // User Data:
        user_data admin = new user_data();
        admin.email = "admin@gmail.com";
        admin.password = "admin";
        usr_data.add(admin);

        ////////////////////////////////////////////////////////////////////////////////////////////////
        // Debug Data:
        user_data a = new user_data();
        a.email = "a";
        a.password = "a";
        usr_data.add(a);
    }

    public void addUser(String email, String password){
        user_data user = new user_data();
        user.email = email;
        user.password = password;

        usr_data.add(user);
    }

    public boolean validateUser(String email, String password){
        for (int i = 0; i<usr_data.size(); i++ )
            if (email.equals(usr_data.get(i).email))
                if (password.equals(usr_data.get(i).password))
                    return true;

        return false;
    }

    public int getQueueSize(basic_data data)
    {
        return data.queueMin + (int)(Math.random() * ((data.queueMax - data.queueMin) + 1));
    }

    public boolean newVacancy(){
        return (new Random()).nextBoolean();
    }

    public static int getDrawableID(e type){
        if (type == e.OUTBACK)
            return R.drawable.outback;
        else if (type == e.APPLEBEES)
            return R.drawable.applebees;
        else if (type == e.CUCKO)
            return R.drawable.cucko;
        else if (type == e.PEPSI)
            return R.drawable.pepsi;

        else
            return 0;
    }

}

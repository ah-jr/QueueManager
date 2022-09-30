package com.example.queuemanager;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.time.LocalTime;

public class database extends Application {

    public enum e{
        OUTBACK, APPLEBEES, CUCKO, PEPSI
    }

    public class basic_data{
        public List<LocalTime> newVacancies = new ArrayList<>();
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
        outback_data.newVacancies.add(LocalTime.parse("11:00:00"));
        outback_data.newVacancies.add(LocalTime.parse("12:00:00"));
        outback_data.newVacancies.add(LocalTime.parse("13:00:00"));

        est_data.put(e.OUTBACK, outback_data);

        ////////////////////////////////////////////////////////////////////////////////////////////////
        // Applebee's Data:
        basic_data applebees_data = new basic_data();
        applebees_data.newVacancies.add(LocalTime.parse("11:00:00"));
        applebees_data.newVacancies.add(LocalTime.parse("12:00:00"));
        applebees_data.newVacancies.add(LocalTime.parse("13:00:00"));

        est_data.put(e.APPLEBEES, applebees_data);

        ////////////////////////////////////////////////////////////////////////////////////////////////
        // Cucko Data:
        basic_data cucko_data = new basic_data();
        cucko_data.newVacancies.add(LocalTime.parse("11:00:00"));
        cucko_data.newVacancies.add(LocalTime.parse("12:00:00"));
        cucko_data.newVacancies.add(LocalTime.parse("13:00:00"));

        est_data.put(e.CUCKO, cucko_data);

        ////////////////////////////////////////////////////////////////////////////////////////////////
        // Pepsi Data:
        basic_data pepsi_data = new basic_data();
        pepsi_data.newVacancies.add(LocalTime.parse("11:00:00"));
        pepsi_data.newVacancies.add(LocalTime.parse("12:00:00"));
        pepsi_data.newVacancies.add(LocalTime.parse("13:00:00"));

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

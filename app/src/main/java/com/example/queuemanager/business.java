package com.example.queuemanager;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.regex.Pattern;

@RequiresApi(api = Build.VERSION_CODES.O)
public class business extends Application {
    database data = new database();

    public static boolean isEmailValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}

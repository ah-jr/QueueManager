package com.example.queuemanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        business bus = (business) getApplicationContext();

        EditText edtEmail = findViewById(R.id.edtEmail);
        EditText edtPassword = findViewById(R.id.edtPassword);
        TextView a = findViewById(R.id.txtForgotPW);


        Button btnSignIn = (Button) findViewById(R.id.btnSignIn);
        Button btnCreateAccount = (Button) findViewById(R.id.btnCreateAccount);
        Intent intent = new Intent(login.this, establishment.class);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (bus.data.validateUser(edtEmail.getText().toString(), edtPassword.getText().toString()))
                    startActivity(intent);
                else {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(login.this);
                    dlgAlert.setMessage("Username or password incorrect.");
                    dlgAlert.setTitle("Error!");
                    dlgAlert.setPositiveButton("Try again", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {


                if (bus.isEmailValid(edtEmail.getText().toString())) {
                    bus.data.addUser(edtEmail.getText().toString(), edtPassword.getText().toString());

                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(login.this);
                    dlgAlert.setMessage("Account successfully created. Please press SIGN-IN.");
                    dlgAlert.setTitle("Success!");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }
                else
                {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(login.this);
                    dlgAlert.setMessage("E-mail format is invalid. Please type a valid e-mail.");
                    dlgAlert.setTitle("Error!");
                    dlgAlert.setPositiveButton("Try again", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }

            }
        });
    }
}
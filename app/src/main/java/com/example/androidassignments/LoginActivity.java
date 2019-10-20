package com.example.androidassignments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;


public class LoginActivity extends AppCompatActivity
{

    protected static final String ACTIVITY_NAME = "LoginActivity";

    SharedPreferences.Editor editor;
    SharedPreferences sharedPref;

    String enteredEmail;

    Button loginButton;// = findViewById(R.id.loginButton);
    EditText emailView;// = findViewById(R.id.emailText);


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        loginButton = findViewById(R.id.loginButton);
        emailView = findViewById(R.id.emailText);

        //Print("Hello there");

        loginButton.setOnClickListener(new View.OnClickListener()
                                       {
                                           public void onClick(View v)
                                           {
                                               // Code here executes on main thread after user presses button
                                               enteredEmail = emailView.getText().toString();
                                               //Log.i(ACTIVITY_NAME, enteredEmail);
                                               editor.putString("DefaultEmail", enteredEmail);
                                               //Print(enteredEmail);
                                               editor.apply(); // commit(). apply() handles this in the background, commit writes immediately
                                               Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                               startActivity(intent);
                                           }
                                       });

        emailView.setText(sharedPref.getString("DefaultEmail", "email@domain.com"), TextView.BufferType.EDITABLE);

        Log.i(ACTIVITY_NAME, "In onCreate()");

    }

    // Print a snack bar
    void Print(String message)
    {
        Snackbar snackbar;
        snackbar = Snackbar.make(findViewById(R.id.content), message, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

}
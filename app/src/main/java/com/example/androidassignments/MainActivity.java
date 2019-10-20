package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

// StartActivity from assignments
public class MainActivity extends Activity {

    protected static final String ACTIVITY_NAME = "MainActivity";

    Button mainButton;
    Button startChatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        mainButton = findViewById(R.id.button);
        startChatButton = findViewById(R.id.start_chat_button);

        mainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent = new Intent(MainActivity.this, ListItemsActivity.class);
                startActivityForResult(intent, 10);
            }
        });


        startChatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                Intent intent = new Intent(MainActivity.this, ChatWindow.class);
                startActivity(intent);
            }
        });
    }

    void onStartButtonClick()
    {
        Log.i(ACTIVITY_NAME, "User clicked Start Chat");
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
    protected void onActivityResult(int requestCode, int responseCode, Intent data)
    {
        if (requestCode == 10)
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");

        if (responseCode == Activity.RESULT_OK)
        {
            String messagePassed = data.getStringExtra("Response");
            Toast.makeText(
                    MainActivity.this,
                    "ListItemsActivity passed: " + messagePassed,
                    Toast.LENGTH_SHORT).show();

        }
    }
}

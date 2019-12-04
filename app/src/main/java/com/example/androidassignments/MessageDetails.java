package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

public class MessageDetails extends AppCompatActivity
{
    protected static final String ACTIVITY_NAME = "MessageDetails";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        Bundle b = this.getIntent().getExtras();
        Log.i(ACTIVITY_NAME, "in onCreate(): " + b.getString("text") + b.getLong("id"));

        setContentView(R.layout.activity_message_details);

        MessageDetailFragment firstFragment = new MessageDetailFragment();
        firstFragment.setArguments(b);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.add(R.id.fullscreen_frame, firstFragment);
        ft.commit();
    }
}

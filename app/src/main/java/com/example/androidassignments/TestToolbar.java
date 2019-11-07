package com.example.androidassignments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity
{

    String optionOneString;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        optionOneString = getString(R.string.item1_select);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This is a snackbar initiated by onCreate", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public boolean onCreateOptionsMenu (Menu m)
    {
        getMenuInflater().inflate(R.menu.toolbar_menu, m );
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi)
    {
        int value = mi.getItemId();

        switch(value)
        {
            case R.id.action_one:
                Log.d("Toolbar", "Option 1 selected");
                View parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, optionOneString, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.action_two:
                Log.d("Toolbar", "Option 2 selected");
                createDialog();
                break;
            case R.id.action_three:
                Log.d("Toolbar", "Option 3 selected");
                createCustomDialog();
                break;
            case R.id.about:
                Log.d("ToolBar", "About selected");
                Toast.makeText(TestToolbar.this, R.string.about, Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    void createDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);

        builder.setTitle(R.string.toolbar_dialog_option_2);
        // Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // User pressed the OK button
                Intent intent = new Intent(TestToolbar.this, MainActivity.class);
                startActivityForResult(intent, 10);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // User cancelled the dialog
            }
        });
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void setMessage(String s)
    {
        this.optionOneString = s;
        return;
    }

    void createCustomDialog()
    {
        LayoutInflater inflater = TestToolbar.this.getLayoutInflater();

        final View view = inflater.inflate(R.layout.dialog_signin, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);

        builder.setTitle(R.string.toolbar_dialog_option_2);
        builder.setView(view);
        //builder.setView(R.layout.dialog_signin);
        // Add the buttons
        final EditText theText = view.findViewById(R.id.message_text_field);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {

                setMessage(theText.getText().toString());
                //setMessage("HiHO!!!!!");
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // User cancelled the dialog
            }
        });
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

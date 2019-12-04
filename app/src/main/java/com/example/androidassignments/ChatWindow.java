package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.content.Context;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity implements AdapterView.OnItemClickListener
{

    protected static final String ACTIVITY_NAME = "ChatWindow";

    SQLiteDatabase database;
    ChatDatabaseHelper tempDatabase;
    private String[] allItems = { ChatDatabaseHelper.COLUMN_ID,
            ChatDatabaseHelper.COLUMN_ITEM };

    boolean didFragLoad = false;
    Button sendButton;
    EditText messageEditText;
    ListView listView;
    FrameLayout frameLayout;
    ArrayList<String> messages = new ArrayList<>();
    //ChatAdapter messageAdapter;
    ChatAdapter messageAdapter;
    Cursor cursor;

    public void onItemClick(AdapterView<?> parent, View view,
                               int pos, long id)
    {

        // An item was selected. You can retrieve the selected item using
        String measure = (String) parent.getItemAtPosition(pos);
        Log.i(ACTIVITY_NAME, "Item selected: " + measure + ", pos: " + pos + ", id: " + id);

        // Load Book Detail Fragment
        MessageDetailFragment secondFragment = new MessageDetailFragment();

        Bundle args = new Bundle();
        args.putInt("position", pos);
        args.putString("text", measure);
        args.putLong("id", id/*listView.getAdapter().getItemId(pos)*/);
        secondFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle

        if (findViewById(R.id.frame_layout) != null)
        {
            // Is a tablet
            //  if(isTablet() || getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            args.putBoolean("isTablet", true);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, secondFragment) // replace flContainer
                    .addToBackStack(null)
                    .commit();
        }
        else
        {
            //getSupportFragmentManager()
            //        .beginTransaction()
            //        .replace(R.id.flContainer, secondFragment) // replace flContainer
            //        .addToBackStack(null)
            //        .commit();
            args.putBoolean("isTablet", false);
            Intent intent = new Intent(ChatWindow.this, MessageDetails.class);
            //intent.putExtra("bundle", args);
            intent.putExtras(args);
            startActivityForResult(intent, 10);
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.i("ChatWindow.java", "in OnCreate() of chat window");
        super.onCreate(savedInstanceState);


        messageAdapter = new ChatAdapter( this );
        tempDatabase = new ChatDatabaseHelper(this);
        database = tempDatabase.getWritableDatabase();

        //ContentValues cValues = new ContentValues();
        //cValues.put(ChatDatabaseHelper.COLUMN_ITEM, "I am Good! :)");
//
        //database.insert(ChatDatabaseHelper.TABLE_ITEMS, "NullPlaceholder", cValues);

        cursor = database.query(ChatDatabaseHelper.TABLE_ITEMS,
                 allItems,
                ChatDatabaseHelper.COLUMN_ITEM + " not null",
                null,
                null,
                null,
                null
                );
        cursor.moveToFirst();

        // Read database and add to messages
        while(!cursor.isAfterLast())
        {
            Log.i(ACTIVITY_NAME, "New message found in Database. Details below --");
            Log.i(ACTIVITY_NAME, "SQL column " + cursor.getColumnName(0) + ": " + cursor.getLong(cursor.getColumnIndex(ChatDatabaseHelper.COLUMN_ID)));


            //Log.i(ACTIVITY_NAME, "SQL COLUMN NAME: " + cursor.getColumnName(1));
            String temp = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.COLUMN_ITEM));
            Log.i(ACTIVITY_NAME, "SQL column " + cursor.getColumnName(1) + ": " + temp);
            messages.add(temp);

            Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount());
            cursor.moveToNext();
        }
        cursor.close();


        //for (int i = 0; i < cursor.getColumnCount(); i++)
        //{
        //    cursor.getColumnName(i);
        //}

        setContentView(R.layout.activity_chat_window);
        sendButton = findViewById(R.id.send_button);
        messageEditText = findViewById(R.id.message_text_edit);
        listView = findViewById(R.id.listview1);
        frameLayout = findViewById(R.id.frame_layout);

        if (frameLayout != null)
        {
            // We are using a tablet, fragment loaded
            //setContentView(R.layout.activity_main);


            if (savedInstanceState == null)
            {
                // Instance of first fragment
                MessageDetailFragment firstFragment = new MessageDetailFragment();

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

                ft.add(R.id.frame_layout, firstFragment);
                ft.commit();
            }
        }
        //final ChatAdapter messageAdapter = new ChatAdapter( this );

        //in this case, “this” is the ChatWindow, which is-A Context object
        listView.setAdapter (messageAdapter);
        listView.setOnItemClickListener(this);
        //listView.setOnItemSelectedListener(this);

        sendButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Code here executes on main thread after user presses button
                String temp = messageEditText.getText().toString();
                messages.add(temp);
                messageEditText.getText().clear();
                //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                //startActivity(intent);
                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/getView()

                // Add to database
                ContentValues cValues = new ContentValues();
                cValues.put(ChatDatabaseHelper.COLUMN_ITEM, temp);

                database.insert(ChatDatabaseHelper.TABLE_ITEMS, "NullPlaceholder", cValues);
            }
        });


    }

    private class ChatAdapter extends ArrayAdapter<String>
    {
        public ChatAdapter(Context ctx)
        {
            super(ctx, 0);
        }

        public int getCount()
        {
            return messages.size(); // Return the number of items in array of messages
        }

        public String getItem(int position)
        {
            return messages.get(position);
        }

        public long getItemId(int position)
        {
            cursor = database.query(ChatDatabaseHelper.TABLE_ITEMS,
                    allItems,
                    ChatDatabaseHelper.COLUMN_ITEM + " not null",
                    null,
                    null,
                    null,
                    null
            );
            cursor.moveToPosition(position);
            long temp = cursor.getLong(cursor.getColumnIndex(ChatDatabaseHelper.COLUMN_ID));
            cursor.close();
            return temp;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result;

            //Log.i(ACTIVITY_NAME, Integer.toString(position));
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = result.findViewById(R.id.message_text);
            message.setText(   /*"Hello"*/ getItem(position) ); // get the string at position
            return result;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 10)
        {
            // Make sure the request was successful
            if (resultCode == RESULT_FIRST_USER)
            {
                // The user wanted to delete the message.
                // The Intent's data Uri identifies which contact was selected.
                long idToBeDeleted = data.getLongExtra("id", 0);
                Log.i(ACTIVITY_NAME, "idToBeDeleted: " + idToBeDeleted);
                deleteItemWithId(idToBeDeleted);

                // Do something with the contact here (bigger example below)
            }
        }
    }

    public void updateMessages()
    {
        messages.clear();
        cursor = database.query(ChatDatabaseHelper.TABLE_ITEMS,
                allItems,
                ChatDatabaseHelper.COLUMN_ITEM + " not null",
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        // Read database and add to messages
        while(!cursor.isAfterLast())
        {
            Log.i(ACTIVITY_NAME, "New message found in Database. Details below --");
            Log.i(ACTIVITY_NAME, "SQL column " + cursor.getColumnName(0) + ": " + cursor.getLong(cursor.getColumnIndex(ChatDatabaseHelper.COLUMN_ID)));


            //Log.i(ACTIVITY_NAME, "SQL COLUMN NAME: " + cursor.getColumnName(1));
            String temp = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.COLUMN_ITEM));
            Log.i(ACTIVITY_NAME, "SQL column " + cursor.getColumnName(1) + ": " + temp);
            messages.add(temp);

            Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount());
            cursor.moveToNext();
        }
        cursor.close();
    }

    public void deleteItemWithId(long id)
    {
        database.delete(ChatDatabaseHelper.TABLE_ITEMS, ChatDatabaseHelper.COLUMN_ID
                + " = " + id, null);
        Log.i(ACTIVITY_NAME, "item deleted!");
        updateMessages();
        messageAdapter.notifyDataSetChanged();
    }

}

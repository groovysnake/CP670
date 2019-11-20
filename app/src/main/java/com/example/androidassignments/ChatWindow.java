package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.content.Context;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity
{

    protected static final String ACTIVITY_NAME = "ChatWindow";

    SQLiteDatabase database;
    ChatDatabaseHelper tempDatabase;
    private String[] allItems = { ChatDatabaseHelper.COLUMN_ID,
            ChatDatabaseHelper.COLUMN_ITEM };

    Button sendButton;
    EditText messageEditText;
    ListView listView;
    ArrayList<String> messages = new ArrayList<>();
    //ChatAdapter messageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.i("ChatWindow.java", "in OnCreate() of chat window");
        super.onCreate(savedInstanceState);

        tempDatabase = new ChatDatabaseHelper(this);
        database = tempDatabase.getWritableDatabase();

        //ContentValues cValues = new ContentValues();
        //cValues.put(ChatDatabaseHelper.COLUMN_ITEM, "I am Good! :)");
//
        //database.insert(ChatDatabaseHelper.TABLE_ITEMS, "NullPlaceholder", cValues);

        Cursor cursor = database.query(ChatDatabaseHelper.TABLE_ITEMS,
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
        final ChatAdapter messageAdapter = new ChatAdapter( this );

        //in this case, “this” is the ChatWindow, which is-A Context object
        listView.setAdapter (messageAdapter);

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
}

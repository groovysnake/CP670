package com.example.androidassignments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_FIRST_USER;

public class MessageDetailFragment extends Fragment
{
    protected static final String FRAGMENT_NAME = "MessageDetailFragment";

    TextView messageTextView, idTextView;
    Button deleteButton;

    String s;
    Long d;
    boolean isTablet;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup parent,
                             @Nullable Bundle savedInstanceState)
    {
        Log.i(FRAGMENT_NAME, "in onCreateView()");
        // Inflate the xml file for the fragment
        return inflater.inflate (R.layout.message_detail_fragment, parent, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.i(FRAGMENT_NAME, "in onCreate()");
        //messageTextView = findView
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if (b != null)
        {
            s = getArguments().getString("text", "Error");
            d = getArguments().getLong("id", 0);
            isTablet = b.getBoolean("isTablet");
        }
        else
        {
            s = "Error: no arguments found";
            d = (long) 1001;
            isTablet = false;
        }


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Set values for view here
        Log.i(FRAGMENT_NAME, "in onViewCreated()");
        messageTextView = view.findViewById(R.id.message_textview);
        idTextView = view.findViewById(R.id.id_textview);
        deleteButton = view.findViewById(R.id.delete_button);

        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Log.i(FRAGMENT_NAME, "deleteButton pressed" + RESULT_FIRST_USER);
                Intent data = new Intent();
                data.putExtra("id", d);

                if(isTablet)
                {
                    ChatWindow cw = (ChatWindow) getActivity();
                    cw.deleteItemWithId(d);
                }
                else
                {
                    // Works for phones
                    getActivity().setResult(RESULT_FIRST_USER, data);
                    getActivity().finish();
                }
                //ChatWindow cw = (ChatWindow) getActivity();

                //getActivity().finish();


            }
        });
        // update view
        messageTextView.setText(s);
        idTextView.setText(d.toString());
    }
    public void updateView(int position)
    {
        messageTextView.setText(s);
        idTextView.setText(d.toString());
    }

}

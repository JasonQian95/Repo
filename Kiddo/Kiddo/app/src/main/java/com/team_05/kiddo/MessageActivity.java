package com.team_05.kiddo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

public class MessageActivity extends AppCompatActivity {

    String newMessage = "";

    public ArrayList<String> messages = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


        // Back button
        ImageButton backImageButton = (ImageButton)(findViewById(R.id.backImageButton));
        backImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // Create messages ListView
        final ListView messagesListView = (ListView)findViewById(R.id.messagesListView);

        final ArrayAdapter<String> messagesAdapter;
        messagesAdapter = new ArrayAdapter<String>(MessageActivity.this, android.R.layout.simple_list_item_1, messages);
        messagesListView.setAdapter(messagesAdapter);

        // Send button
        ImageButton sendImageButton = (ImageButton)findViewById(R.id.sendImageButton);
        sendImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set date and time
                Calendar calendar = Calendar.getInstance();
                TimeZone timeZone = calendar.getTimeZone();
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d h:mm a");
                sdf.setTimeZone(timeZone);
                Date date = new Date();
                String dateAndTime = sdf.format(date);

                // Append new message to date and time
                // Send message
                String dateAndTimeMessage = "Me\n" + dateAndTime + "\n" + newMessage;
                messages.add(dateAndTimeMessage);
                messagesAdapter.notifyDataSetChanged();

                // Clear message text field after sending
                EditText messageTextField = (EditText)findViewById(R.id.messageTextField);
                messageTextField.setText("");
            }
        });

        // Message text field
        EditText messageTextField = (EditText)findViewById(R.id.messageTextField);
        messageTextField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                newMessage = v.getText().toString();

                return handled;
            }
        });
    }
}

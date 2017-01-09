package com.team_05.kiddo;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class GroupMessageActivity extends AppCompatActivity {

    public static ArrayList<String> groupMessages;

    String newMessage = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_message);


        // Clear history of group messages
        groupMessages = new ArrayList<String>();


        // Back button
        ImageButton backImageButton = (ImageButton)findViewById(R.id.backImageButton);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // Create group messages ListView
        ListView groupMessagesListView = (ListView)findViewById(R.id.groupMessagesListView);

        final ArrayAdapter<String> groupMessagesAdapter;
        groupMessagesAdapter = new ArrayAdapter<String>(GroupMessageActivity.this, android.R.layout.simple_list_item_1, groupMessages);
        groupMessagesListView.setAdapter(groupMessagesAdapter);

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

                // Create message
                // Send message
                String dateAndTimeMessage = dateAndTime + "\n" + newMessage;
                groupMessages.add(dateAndTimeMessage);
                groupMessagesAdapter.notifyDataSetChanged();

                // Clear message text field after sending
                EditText groupMessagesTextField = (EditText)findViewById(R.id.groupMessageTextField);
                groupMessagesTextField.setText("");
            }
        });

        // Group message text field
        EditText groupMessagesTextField = (EditText)findViewById(R.id.groupMessageTextField);
        groupMessagesTextField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                newMessage = v.getText().toString();

                return handled;
            }
        });
    }
}

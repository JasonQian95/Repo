package com.team_05.kiddo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

public class TabActivity extends AppCompatActivity {

    String newAnnouncement;

    public static ArrayList<String> announcements = new ArrayList<String>();
    public static ArrayList<String> enrolmentRequests = new ArrayList<String>();
    public static ArrayList<String> students = new ArrayList<String>();
    public static ArrayList<String> events = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec1 = tabHost.newTabSpec("announcements");
        spec1.setIndicator("", getResources().getDrawable(R.drawable.ic_announcement_black_24dp_rgb));
        spec1.setContent(R.id.announcements);
        tabHost.addTab(spec1);

        TabHost.TabSpec spec2 = tabHost.newTabSpec("students");
        spec2.setIndicator("", getResources().getDrawable(R.drawable.ic_people_black_24dp_rgb));
        spec2.setContent(R.id.students);
        tabHost.addTab(spec2);

        TabHost.TabSpec spec3 = tabHost.newTabSpec("groupMessage");
        spec3.setIndicator("", getResources().getDrawable(R.drawable.ic_message_black_24dp_rgb));
        spec3.setContent(R.id.groupMessage);
        tabHost.addTab(spec3);

        TabHost.TabSpec spec4 = tabHost.newTabSpec("calendar");
        spec4.setIndicator("", getResources().getDrawable(R.drawable.ic_event_black_24dp_rgb));
        spec4.setContent(R.id.calendar);
        tabHost.addTab(spec4);

        TabHost.TabSpec spec5 = tabHost.newTabSpec("enrolmentRequests");
        spec5.setIndicator("", getResources().getDrawable(R.drawable.ic_note_add_black_24dp_rgb));
        spec5.setContent(R.id.enrolmentRequests);
        tabHost.addTab(spec5);


        // Settings button®
        ImageButton settingsImageButton = (ImageButton)(findViewById(R.id.settingsImageButton));
        settingsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TabActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });


        // Back button
        ImageButton backImageButton = (ImageButton)findViewById(R.id.backImageButton);
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // Create announcements ListView
        final ListView announcementsListView = (ListView)findViewById(R.id.announcementsListView);

        final ArrayAdapter<String> announcementsAdapter;
        announcementsAdapter = new ArrayAdapter<String>(TabActivity.this, android.R.layout.simple_list_item_1, announcements);
        announcementsListView.setAdapter(announcementsAdapter);

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

                // Append new announcement to date and time
                // Send announcement
                String dateAndTimeAnnouncement = dateAndTime + "\n" + newAnnouncement;
                announcements.add(dateAndTimeAnnouncement);
                announcementsAdapter.notifyDataSetChanged();

                // Clear announcements text field after sending
                EditText announcementsEditText = (EditText)findViewById(R.id.announcementsTextField);
                announcementsEditText.setText("");
            }
        });

        // Announcements text field
        EditText announcementsEditText = (EditText)findViewById(R.id.announcementsTextField);
        announcementsEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                newAnnouncement = v.getText().toString();

                return handled;
            }
        });


        // Create students ListView
        ListView studentsListView = (ListView)findViewById(R.id.studentsListView);
        students.add("Parent: Jimmy Sung\nChild: Tony Sung");
        students.add("Parent: Mike Lang\nChild: Laura Lang");
        students.add("Parent: John Smith\nChild Edward Smith");
        students.add("Parent: Alfred Moor\nChild: Will Moor");
        students.add("Parent: Robert Wrench\nChild: Ted Wrench");
        students.add("Parent: John Yu\nChild: Carrie Yu");
        students.add("Parent: Lonnie Putz\nChild: Felcia Putz");
        students.add("Parent: Gerald Ervin\nChild: Naomi Ervin");
        students.add("Parent: Karen Coleman\nChild: Joan Coleman");
        students.add("Parent: Carolyn Harvey\nChild: Marian Harvey");
        students.add("Parent: Samuel Swain\nChild: Keith Swain");
        students.add("Parent: Robert Acosta\nChild: Susan Acosta");
        Collections.sort(students);

        ArrayAdapter<String> studentsAdapter;
        studentsAdapter = new ArrayAdapter<String>(TabActivity.this, android.R.layout.simple_list_item_1, students);
        studentsListView.setAdapter(studentsAdapter);

        studentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TabActivity.this, MessageActivity.class);
                startActivity(intent);
            }
        });


        // Create group message ListView
        final ListView groupMessageListView = (ListView)findViewById(R.id.groupMessageListView);

        ArrayAdapter<String> groupMessageAdapter;
        groupMessageAdapter = new ArrayAdapter<String>(TabActivity.this, android.R.layout.simple_list_item_checked, students);
        groupMessageListView.setAdapter(groupMessageAdapter);
        groupMessageListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        groupMessageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (groupMessageListView.isItemChecked(position) == true) {
                    groupMessageListView.setItemChecked(position, true);
                } else {
                    groupMessageListView.setItemChecked(position, false);
                }

            }
        });


        // Create calendar ListView
        ListView calendarListView = (ListView)findViewById(R.id.calendarListView);

        ArrayAdapter<String> calendarAdapter;
        calendarAdapter = new ArrayAdapter<String>(TabActivity.this, android.R.layout.simple_expandable_list_item_1, events);
        calendarListView.setAdapter(calendarAdapter);

        calendarListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TabActivity.this, EventDescriptionActivity.class);
                startActivity(intent);
            }
        });

        events.add("Sat Nov 12 12:00 PM\nPicnic");


        // Create enrolment requests list view
        ListView enrolmentRequestsListView = (ListView)findViewById(R.id.enrolmentRequestsListView);
        enrolmentRequests.add("Parent: Reginald Rhodes\nChild: Daniel Rhodes");
        enrolmentRequests.add("Parent: Eduardo Leal\nChild: Loretta Leal");

        ArrayAdapter<String> enrolmentRequestsAdapter;
        enrolmentRequestsAdapter = new ArrayAdapter<String>(TabActivity.this, android.R.layout.simple_list_item_1, enrolmentRequests);
        enrolmentRequestsListView.setAdapter(enrolmentRequestsAdapter);

        enrolmentRequestsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TabActivity.this, EnrolmentRequestActivity.class);
                startActivity(intent);
            }
        });


        // Check all button
        ImageButton checkAllImageButton = (ImageButton)findViewById(R.id.checkAllImageButton);
        checkAllImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groupMessageListView.isItemChecked(0)) {
                    for (int i = 0; i <= students.size(); i++) {
                        groupMessageListView.setItemChecked(i, false);
                    }
                } else {
                    for (int i = 0; i <= students.size(); i++) {
                        groupMessageListView.setItemChecked(i, true);
                    }
                }
            }
        });


        // Create group message button
        Button createGroupMessageButton = (Button)findViewById(R.id.createMessageButton);
        createGroupMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TabActivity.this, GroupMessageActivity.class);
                startActivity(intent);
            }
        });


        // Create event button
        Button createEvent = (Button)findViewById(R.id.createEventButton);
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TabActivity.this, CreateEventActivity.class);
                startActivity(intent);
            }
        });
    }


}

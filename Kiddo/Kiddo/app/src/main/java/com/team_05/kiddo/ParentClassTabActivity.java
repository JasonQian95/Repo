package com.team_05.kiddo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.Collections;

public class ParentClassTabActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_class_tab);

        TabHost tabHost = (TabHost) findViewById(R.id.pcTabHost);
        tabHost.setup();

        TabHost.TabSpec spec1 = tabHost.newTabSpec("announcements");
        spec1.setContent(R.id.pannouncementsTab);
        spec1.setIndicator("Announcements");
        tabHost.addTab(spec1);

        TabHost.TabSpec spec2 = tabHost.newTabSpec("pmessages");
        spec2.setContent(R.id.pMessagesTab);
        spec2.setIndicator("Messages");
        tabHost.addTab(spec2);

        // Settings button
        ImageButton settingsImageButton = (ImageButton)(findViewById(R.id.settingsImageButton));
        settingsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParentClassTabActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // Back button
        ImageButton backImageButton = (ImageButton)(findViewById(R.id.backImageButton));
        backImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParentClassTabActivity.this, ParentTabActivity.class);
                finish();
                startActivity(intent);
            }
        });

        // Create parent announcements ListView
        ListView pAnnouncementsListView = (ListView)findViewById(R.id.pAnnouncementsListView);
        ArrayList<String> announcements = new ArrayList<String>();
        announcements.add("Sat Nov 12 2:13 PM\nMatilda Permission Slips");
        announcements.add("Sun Nov 13 10:59 AM\nClass cancelled");
        Collections.sort(announcements);

        ArrayAdapter<String> pAnnouncementsAdapter;
        pAnnouncementsAdapter = new ArrayAdapter<String>(ParentClassTabActivity.this, android.R.layout.simple_list_item_1, announcements);
        pAnnouncementsListView.setAdapter(pAnnouncementsAdapter);

        pAnnouncementsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ParentClassTabActivity.this, AnnouncementDetailsActivity.class);
                startActivity(intent);
            }
        });

        // Create parent messages ListView
        ListView pMessagesListView = (ListView)findViewById(R.id.pMessagesListView);
        ArrayList<String> messages = new ArrayList<String>();
        messages.add("Alfred Moor");
        Collections.sort(messages);

        ArrayAdapter<String> pMessagesAdapter;
        pMessagesAdapter = new ArrayAdapter<String>(ParentClassTabActivity.this, android.R.layout.simple_list_item_1, messages);
        pMessagesListView.setAdapter(pMessagesAdapter);

        pMessagesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ParentClassTabActivity.this, MessageActivity.class);
                startActivity(intent);
            }
        });
    }
}

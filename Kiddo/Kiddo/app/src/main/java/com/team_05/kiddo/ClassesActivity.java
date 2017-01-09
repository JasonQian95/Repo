package com.team_05.kiddo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.SortedList;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class ClassesActivity extends AppCompatActivity {

    ArrayList<String> classes = new ArrayList<String>();

    ListView listView;

    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);

        // Settings button®
        ImageButton settingsImageButton = (ImageButton)(findViewById(R.id.settingsImageButton));
        settingsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassesActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        listView = (ListView)findViewById(R.id.classesListView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, classes);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ClassesActivity.this, TabActivity.class);
                startActivity(intent);
            }
        });

        classes.add("CSC301");
        classes.add("CSC302");
        classes.add("CSC303");
    }


}

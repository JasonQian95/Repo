package com.team_05.kiddo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class ChildrenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_children);

        // Settings button
        ImageButton settingsImageButton = (ImageButton)(findViewById(R.id.settingsImageButton));
        settingsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChildrenActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        // Create children ListView
        ListView childrenListView = (ListView)findViewById(R.id.childrenListView);
        ArrayList<String> children = new ArrayList<String>();
        children.add("Tony Lang");
        children.add("Laura Lang");
        Collections.sort(children);

        ArrayAdapter<String> childrenAdapter;
        childrenAdapter = new ArrayAdapter<String>(ChildrenActivity.this, android.R.layout.simple_list_item_1, children);
        childrenListView.setAdapter(childrenAdapter);

        childrenListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChildrenActivity.this, ParentTabActivity.class);
                startActivity(intent);
            }
        });

        // Add child button
        Button addChildButton = (Button)(findViewById(R.id.addChildButton));
        addChildButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChildrenActivity.this, AddChildActivity.class);
                finish();
                startActivity(intent);
            }
        });

    }
}

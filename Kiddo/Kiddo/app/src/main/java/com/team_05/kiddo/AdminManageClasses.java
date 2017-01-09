package com.team_05.kiddo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class AdminManageClasses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_classes);

        Button backButton = (Button)(findViewById(R.id.mainMenuButton));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminManageClasses.this, AdminMainMenu.class);
                finish();
                startActivity(intent);
            }

        });

        Button addClassButton = (Button)(findViewById(R.id.addClassButton));
        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminManageClasses.this, AdminAddClass.class);
                finish();
                startActivity(intent);
            }

        });

        // Create classes ListView
        ListView classesListView = (ListView)findViewById(R.id.adClassListView);
        ArrayList<String> classes = new ArrayList<String>();
        classes.add("Junior Kindergarten\nRoom 101");
        classes.add("Senior Kindergarten\nRoom 102");
        classes.add("Grade 1\nRoom 103");
        classes.add("Grade 2\nRoom 104");


        ArrayAdapter<String> classesAdapter;
        classesAdapter = new ArrayAdapter<String>(AdminManageClasses.this, android.R.layout.simple_list_item_1, classes);
        classesListView.setAdapter(classesAdapter);

        classesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> classes, View view, int position, long id) {
                Intent intent = new Intent(AdminManageClasses.this, AdminViewClass.class);
                startActivity(intent);
            }
        });
    }
}

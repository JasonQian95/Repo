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

public class AdminViewClass extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_class);

        Button backButton = (Button)(findViewById(R.id.backButton));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminViewClass.this, AdminManageClasses.class);
                finish();
                startActivity(intent);
            }

        });

        Button addTeacherButton = (Button)(findViewById(R.id.addButton));
        addTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminViewClass.this, AdminViewTeacher.class);
                finish();
                startActivity(intent);
            }

        });

        Button deleteClassButton = (Button)(findViewById(R.id.deleteButton));
        deleteClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminViewClass.this, AdminManageClasses.class);
                finish();
                startActivity(intent);
            }

        });

        Button viewClassListButton = (Button)(findViewById(R.id.classListButton));
        viewClassListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminViewClass.this, AdminClassList.class);
                finish();
                startActivity(intent);
            }

        });

        // Create teachers ListView
        ListView teachersListView = (ListView)findViewById(R.id.adTeacherListView);
        ArrayList<String> teachers = new ArrayList<String>();
        teachers.add("Jenny Abrams");

        ArrayAdapter<String> teachersAdapter;
        teachersAdapter = new ArrayAdapter<String>(AdminViewClass.this, android.R.layout.simple_list_item_1, teachers);
        teachersListView.setAdapter(teachersAdapter);

        teachersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> teacher, View view, int position, long id) {
                Intent intent = new Intent(AdminViewClass.this, AdminViewTeacher.class);
                startActivity(intent);
            }
        });

    }
}

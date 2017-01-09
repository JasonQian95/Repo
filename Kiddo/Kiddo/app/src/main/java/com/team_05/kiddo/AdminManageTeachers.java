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

public class AdminManageTeachers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_teachers);

        Button backButton = (Button)(findViewById(R.id.mainMenuButton));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminManageTeachers.this, AdminMainMenu.class);
                finish();
                startActivity(intent);
            }

        });

        Button addTeacherButton = (Button)(findViewById(R.id.addTeacherButton));
        addTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminManageTeachers.this, AdminAddTeacher.class);
                finish();
                startActivity(intent);
            }

        });

        // Create teachers ListView
        ListView teachersListView = (ListView)findViewById(R.id.adTeacherListView);
        ArrayList<String> teachers = new ArrayList<String>();
        teachers.add("Jenny Abrams\nActive Account");
        teachers.add("Tim Charles\nPending Invitation");
        teachers.add("Betty Edwards\nActive Account");
        teachers.add("Janet Ford\nPending Invitation");

        ArrayAdapter<String> teachersAdapter;
        teachersAdapter = new ArrayAdapter<String>(AdminManageTeachers.this, android.R.layout.simple_list_item_1, teachers);
        teachersListView.setAdapter(teachersAdapter);

        teachersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> teacher, View view, int position, long id) {
                Intent intent = new Intent(AdminManageTeachers.this, AdminViewTeacher.class);
                startActivity(intent);
            }
        });
    }
}

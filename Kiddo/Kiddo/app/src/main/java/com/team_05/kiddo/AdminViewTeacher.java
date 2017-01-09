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

public class AdminViewTeacher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_teacher);

        Button backButton = (Button)(findViewById(R.id.backButton));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminViewTeacher.this, AdminManageTeachers.class);
                finish();
                startActivity(intent);
            }

        });

        Button deleteButton = (Button)(findViewById(R.id.deleteButton));
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminViewTeacher.this, AdminTeacherDelete.class);
                finish();
                startActivity(intent);
            }

        });

        // Create teaching ListView
        ListView teachingListView = (ListView)findViewById(R.id.adTeacherListView);
        ArrayList<String> teaching = new ArrayList<String>();
        teaching.add("Junior Kindergarten\nRoom 101");
        Collections.sort(teaching);

        ArrayAdapter<String> teachingAdapter;
        teachingAdapter = new ArrayAdapter<String>(AdminViewTeacher.this, android.R.layout.simple_list_item_1, teaching);
        teachingListView.setAdapter(teachingAdapter);

        teachingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> teacher, View view, int position, long id) {
                Intent intent = new Intent(AdminViewTeacher.this, AdminViewClass.class);
                startActivity(intent);
            }
        });
    }
}


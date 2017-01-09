package com.team_05.kiddo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminTeacherDelete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_teacher_delete);

        Button menuButton = (Button)(findViewById(R.id.menuButton));
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminTeacherDelete.this, AdminMainMenu.class);
                finish();
                startActivity(intent);
            }

        });

        Button teacherButton = (Button)(findViewById(R.id.teacherButton));
        teacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminTeacherDelete.this, AdminManageTeachers.class);
                finish();
                startActivity(intent);
            }

        });
    }
}

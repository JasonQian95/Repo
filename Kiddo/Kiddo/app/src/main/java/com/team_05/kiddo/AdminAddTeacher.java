package com.team_05.kiddo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminAddTeacher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_teacher);

        Button backButton = (Button)(findViewById(R.id.cancelButton));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminAddTeacher.this, AdminManageTeachers.class);
                finish();
                startActivity(intent);
            }

        });

        Button confirmButton = (Button)(findViewById(R.id.confirmButton));
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //username =
                //createTeacher (username, String password, String email, String fName, String lName)
                Intent intent = new Intent(AdminAddTeacher.this, AdminTeacherInvite.class);
                finish();
                startActivity(intent);
            }

        });
    }
}

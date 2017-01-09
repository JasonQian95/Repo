package com.team_05.kiddo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminMainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_menu);

        Button manageClasses = (Button)(findViewById(R.id.manageClassesButton));
        manageClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminMainMenu.this, AdminManageClasses.class);
                finish();
                startActivity(intent);
            }

        });


        Button manageTeachers = (Button)(findViewById(R.id.manageTeachersButton));
        manageTeachers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminMainMenu.this, AdminManageTeachers.class);
                finish();
                startActivity(intent);
            }
        });

        Button updateInfo = (Button)(findViewById(R.id.updateAdminButton));
        manageTeachers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminMainMenu.this, AdminUpdateInfo.class);
                finish();
                startActivity(intent);
            }
        });

    }
}

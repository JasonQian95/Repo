package com.team_05.kiddo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminTeacherInvite extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_teacher_invite);

        Button backButton = (Button) (findViewById(R.id.cancelButton));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminTeacherInvite.this, AdminManageTeachers.class);
                finish();
                startActivity(intent);
            }

        });

        Button inviteButton = (Button) (findViewById(R.id.inviteButton));
        inviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminTeacherInvite.this, AdminTeacherInviteConfirm.class);
                finish();
                startActivity(intent);
            }

        });


    }
}

package com.team_05.kiddo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class AddChildActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        // Back button
        ImageButton backImageButton = (ImageButton)(findViewById(R.id.backImageButton));
        backImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddChildActivity.this, ChildrenActivity.class);
                finish();
                startActivity(intent);
            }
        });

        // Go to course enrolment button
        Button courseEnrolButton = (Button)(findViewById(R.id.courseEnrolButton));
        courseEnrolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddChildActivity.this, CourseEnrolmentActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}

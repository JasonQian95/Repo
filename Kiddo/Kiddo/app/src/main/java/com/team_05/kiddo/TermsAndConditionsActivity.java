package com.team_05.kiddo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class TermsAndConditionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);


        // Back button
        ImageButton backImageButton = (ImageButton)(findViewById(R.id.backImageButton));
        backImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermsAndConditionsActivity.this, ParentInfo.class);
                finish();
                startActivity(intent);
            }
        });

        // Accept button
        Button acceptButton = (Button)(findViewById(R.id.acceptButton));
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermsAndConditionsActivity.this, RegistrationConfirmationActivity.class);
                finish();
                startActivity(intent);
            }
        });

        // Decline button
        Button declineButton = (Button)(findViewById(R.id.declineButton));
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermsAndConditionsActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

    }
}

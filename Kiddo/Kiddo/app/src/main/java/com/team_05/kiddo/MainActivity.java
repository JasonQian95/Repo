package com.team_05.kiddo;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import Users.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //User user = new Admin("user", "pass", "email@email.com");
        // Login button
        Button loginButton = (Button)(findViewById(R.id.loginButton));

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email   = (EditText)findViewById(R.id.emailTextField);

                //Dummy logins for testing UI
                // Teacher
                if(email.getText().toString().equals("teacher@email.com")){
                    Intent intent = new Intent(MainActivity.this, TeacherInitialLoginActivity.class);
                    finish();
                    startActivity(intent);

                }
                // Parent
                else if (email.getText().toString().equals("parent@email.com")){
                    Intent intent = new Intent(MainActivity.this, ChildrenActivity.class);
                    finish();
                    startActivity(intent);
                }

                //admin
                else if (email.getText().toString().equals("admin@email.com")){
                    Intent intent = new Intent(MainActivity.this, AdminMainMenu.class);
                    finish();
                    startActivity(intent);
                }

                // display error message for invalid credentials
                else {
                    Context context = getApplicationContext();
                    CharSequence text = "Invalid username and/or password.";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        });

        // Sign up button
        Button signupButton = (Button)(findViewById(R.id.signupButton));
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ParentInfo.class);
                finish();
                startActivity(intent);
            }
        });
    }


}

package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login_btn = findViewById(R.id.login);

        Button signup_btn = findViewById(R.id.Sign_up);
        login_btn.setOnClickListener(v-> startActivity(new Intent(this,User_Login.class )));
        signup_btn.setOnClickListener(v-> startActivity(new Intent(this,User_registration.class )));

    }



}
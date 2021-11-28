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
        Button Angel_login_btn = findViewById(R.id.Angel_login);
        Button Traveler_login_btn = findViewById(R.id.Traveler_login);
        Button signup_btn = findViewById(R.id.Sign_up);
        Angel_login_btn.setOnClickListener(v-> startActivity(new Intent(this,Angel_login.class )));
        Traveler_login_btn.setOnClickListener(v-> startActivity(new Intent(this,Traveler_login.class )));
        signup_btn.setOnClickListener(v-> startActivity(new Intent(this,User_registration.class )));

    }



}
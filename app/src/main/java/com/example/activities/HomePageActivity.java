package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePageActivity extends AppCompatActivity {
    Button addOffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        addOffer=findViewById(R.id.addOffers);

        addOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent   =new Intent(HomePageActivity.this,Angel_homePage.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
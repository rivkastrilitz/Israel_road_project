package com.example.activities;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Angel_homePage  extends AppCompatActivity {
    private EditText user_name, password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angel_homepage);


        // Wire up the button to do stuff
        //..get the button
        Button post_btn = (Button) findViewById(R.id.post_offer_btn);
        //..set what happens when the user clicks
        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "It's magic", Toast.LENGTH_SHORT)
                .show();

            }
        });
    }

    Button create_new_offer_btn;
}

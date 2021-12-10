package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SearchPostActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseRef;
    private EditText date , location;
    private Button search;
    private String txtDate,txtLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post);
        mAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference();

        date=(EditText)findViewById(R.id.Traveler_date_input);
        location=(EditText) findViewById(R.id.Traveler_location_input);
        search=(Button)findViewById(R.id.search_in_posts);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDate=date.getText().toString();
                txtLocation=location.getText().toString();


            }
        });


    }

    private void SortHostingOffers(String date, String location){
        
    }

    private void readPostFromFirebase(){

    }
}
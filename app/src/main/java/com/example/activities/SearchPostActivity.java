package com.example.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adapters.PostAdapter;
import com.example.model.post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchPostActivity extends AppCompatActivity {

    private EditText date , location;
    private Button search ,back;
    private String txtDate,txtLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post);
        date=(EditText)findViewById(R.id.Traveler_date_input);
        location=(EditText) findViewById(R.id.Traveler_location_input);
        search=(Button)findViewById(R.id.search_in_posts);
        back=(Button)findViewById(R.id.Backbtn);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDate=date.getText().toString();
                txtLocation=location.getText().toString();
                Intent intent =new Intent(SearchPostActivity.this,postsFeedActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SearchPostActivity.this,HomePageActivity.class);
                startActivity(intent);
            }
        });


    }






}
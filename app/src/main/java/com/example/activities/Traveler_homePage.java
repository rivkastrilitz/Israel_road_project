package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.example.model.traveler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class Traveler_homePage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseRef;
    private StorageReference mystorge;
    private String user_id = "";
    private String TravelerName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traveler_home_page);


        databaseRef = FirebaseDatabase.getInstance().getReference();
        getTravelerUid();
        mystorge = FirebaseStorage.getInstance().getReference().child("Users").child(user_id);

        Intent intent=getIntent();
        Bundle nameFromLogin = intent.getExtras();
        if(nameFromLogin != null)
        {
            TravelerName= nameFromLogin.getString("name");
        }
        String testString = String.format(getResources().getString(R.string.welcome), TravelerName);
        Log.i("ARG", "another_string = " + testString);






    }


    public void getTravelerUid(){
        Intent intent=getIntent();
        Bundle UidFromLogin = intent.getExtras();
        if(UidFromLogin != null)
        {
            user_id = UidFromLogin.getString("Uid");
        }

    }
    public void getTravelerName(){
        Intent intent=getIntent();
        Bundle nameFromLogin = intent.getExtras();
        if(nameFromLogin != null)
        {
            TravelerName= nameFromLogin.getString("name");
        }

    }



}
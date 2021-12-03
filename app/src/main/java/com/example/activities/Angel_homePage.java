package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Angel_homePage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseRef;
    private StorageReference mystorge;
    String user_id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angel_home_page);
        databaseRef = FirebaseDatabase.getInstance().getReference();
        getAngelUid();
        mystorge = FirebaseStorage.getInstance().getReference().child("Users").child(user_id);


    }

    public void getAngelUid(){
        Intent intent=getIntent();
        Bundle UidFromLogin = intent.getExtras();
        if(UidFromLogin != null)
        {
            user_id = UidFromLogin.getString("Uid");
        }

    }

}
package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    private EditText AngelAddress,fromDate,toDate,capacity,restrictions;
    Button createOffer,deleteOffer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angel_home_page);
        databaseRef = FirebaseDatabase.getInstance().getReference();
        getAngelUid();
        mystorge = FirebaseStorage.getInstance().getReference().child("Users").child(user_id);
        AngelAddress=findViewById(R.id.post_address);
        fromDate=findViewById(R.id.post_fromDate);
        toDate=findViewById(R.id.post_toDate);
        capacity=findViewById(R.id.post_capacity);
        restrictions=findViewById(R.id.post_restrictions);
        createOffer=findViewById(R.id.createOffer);
        deleteOffer=findViewById(R.id.deleteOffer);

        createOffer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                
            }
        });




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
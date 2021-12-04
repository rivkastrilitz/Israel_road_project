package com.example.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.fragments.AngelHomeFragment;
import com.example.fragments.profileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
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
    ActionBar actionBar;

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

        actionBar = getSupportActionBar();

        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnItemSelectedListener(selectedListener);

        actionBar.setTitle("Home");
        AngelHomeFragment angelHomeFragment = new AngelHomeFragment();
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.content, angelHomeFragment, "");
        ft1.commit();


    }
    private NavigationBarView.OnItemSelectedListener selectedListener =
            new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.nav_home:
                            actionBar.setTitle("Home");
                            AngelHomeFragment angelHomeFragment = new AngelHomeFragment();
                            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                            ft1.replace(R.id.content, angelHomeFragment, "");
                            ft1.commit();
                            return true;
                        case R.id.nav_profile:
                            actionBar.setTitle("Profile");
                            profileFragment profileFragment = new profileFragment();
                            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                            ft2.replace(R.id.content, profileFragment, "");
                            ft2.commit();
                            return true;
//                        case R.id.nav_user:
//                            return true;
                    }
                    return false;
                }
            };

    public void getAngelUid(){
        Intent intent=getIntent();
        Bundle UidFromLogin = intent.getExtras();
        if(UidFromLogin != null)
        {
            user_id = UidFromLogin.getString("Uid");
        }

    }

}
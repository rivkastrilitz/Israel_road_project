package com.example.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.fragments.AngelHomeFragment;
import com.example.fragments.TravelerHomeFragment;
import com.example.fragments.profileFragment;
import com.example.model.traveler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
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
    private String user_id;
    private String TravelerName;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traveler_home_page);

        databaseRef = FirebaseDatabase.getInstance().getReference();
//        user_id=mAuth.getCurrentUser().getUid();
//        mystorge = FirebaseStorage.getInstance().getReference().child("Users").child(user_id);
//        getTravelerName();
//        String testString = String.format(getResources().getString(R.string.welcome), TravelerName);




//        actionBar = getSupportActionBar();
//
//        BottomNavigationView navigationView = findViewById(R.id.navigation);
//        navigationView.setOnItemSelectedListener(selectedListener);
//
//        actionBar.setTitle("Search Hosting Offer");
//        TravelerHomeFragment Travelerhomefrag = new TravelerHomeFragment();
//        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
//        ft1.replace(R.id.content, Travelerhomefrag, "");
//        ft1.commit();





    }

//    private NavigationBarView.OnItemSelectedListener selectedListener =
//            new NavigationBarView.OnItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    switch (item.getItemId()){
//                        case R.id.nav_home:
//                            actionBar.setTitle("Home");
//                            TravelerHomeFragment Travelerhomefrag = new TravelerHomeFragment();
//                            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
//                            ft1.replace(R.id.content, Travelerhomefrag, "");
//                            ft1.commit();
//                            return true;
//                        case R.id.nav_profile:
//                            actionBar.setTitle("Profile");
//                            profileFragment profileFragment = new profileFragment();
//                            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
//                            ft2.replace(R.id.content, profileFragment, "");
//                            ft2.commit();
//                            return true;
////                        case R.id.nav_user:
////                            return true;
//                    }
//                    return false;
//                }
//            };




}
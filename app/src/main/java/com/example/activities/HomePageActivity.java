package com.example.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fragments.AngelHomeFragment;
import com.example.fragments.profileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomePageActivity extends AppCompatActivity {
    private Button addOffer,searchOffer;
    private ActionBar actionBar;
    private TextView welcome;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        addOffer=findViewById(R.id.addOffers);
        searchOffer=findViewById(R.id.serchOffers);
        welcome=(TextView)findViewById(R.id.TextWelcome);
        getUserName();
        welcome.setText( "ברוך הבא "+userName);

        addOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent   =new Intent(HomePageActivity.this,Angel_homePage.class);
                startActivity(intent);
                finish();
            }
        });
        searchOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent   =new Intent(HomePageActivity.this, SearchPostActivity.class);
                startActivity(intent);
                finish();

            }
        });



//        actionBar = getSupportActionBar();
//
//        BottomNavigationView navigationView = findViewById(R.id.navigation);
//        navigationView.setOnItemSelectedListener(selectedListener);
//
//        actionBar.setTitle("Home");
//        AngelHomeFragment angelHomeFragment = new AngelHomeFragment();
//        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
//        ft1.replace(R.id.content, angelHomeFragment, "");
//        ft1.commit();



    }

//    private NavigationBarView.OnItemSelectedListener selectedListener =
//            new NavigationBarView.OnItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    switch (item.getItemId()){
//                        case R.id.nav_home:
//                            actionBar.setTitle("Home");
//                            AngelHomeFragment angelHomeFragment = new AngelHomeFragment();
//                            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
//                            ft1.replace(R.id.content, angelHomeFragment, "");
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

    public void getUserName(){
        Intent intent=getIntent();
        Bundle nameFromLogin = intent.getExtras();
        if(nameFromLogin != null)
        {
            userName= nameFromLogin.getString("name");
        }

    }

}
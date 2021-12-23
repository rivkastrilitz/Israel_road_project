package com.example.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.model.AirplaneModeChangeReceiver;
import com.example.model.post;

import com.google.firebase.auth.FirebaseAuth;
import com.mapbox.mapboxsdk.maps.MapView;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    private Button addOffer,searchOffer ,map;
    private ActionBar actionBar;
    private TextView welcome;
    private String userName;
    private String usertype;
    private RecyclerView postsRecycle;
    private FirebaseAuth mAuth;
    private String uid;
    private List<post> postList=new ArrayList<>();

    AirplaneModeChangeReceiver airplaneModeChangeReceiver = new AirplaneModeChangeReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ////////////////////////////////////////////////
        addOffer = findViewById(R.id.addOffers);
        searchOffer = findViewById(R.id.serchOffers);
        map = findViewById(R.id.Mymap);
        welcome = (TextView) findViewById(R.id.TextWelcome);
        getUserName();
        welcome.setText("welcome " + userName);

        postsRecycle = findViewById(R.id.recyclePosts);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();

        addOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, Angel_homePage.class);
                getUserType();
                getUserId();
                intent.putExtra("type", usertype);
                intent.putExtra("uid", uid);
                startActivity(intent);
                finish();
            }
        });
        searchOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, SearchPostActivity.class);
                startActivity(intent);
                finish();

            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, Mapactivity.class);
                startActivity(intent);
                finish();
            }
        });

        //////////////////////////////////////


//
//
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
//
//
//
//    }
//
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
//                        case R.id.nav_chat:
//                            actionBar.setTitle("Chats");
//                            ChatListFragment chatListFragment = new ChatListFragment();
//                            FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
//                            ft3.replace(R.id.content, chatListFragment, "");
//                            ft3.commit();
//                            return true;
////                        case R.id.nav_user:
////                            return true;
//                    }
//                    return false;
//                }
//            };

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_log_out).setVisible(true);
        menu.findItem(R.id.action_profile).setVisible(true);
        menu.findItem(R.id.action_chat).setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intentMain = new Intent(this, MainActivity.class);
        Intent intentChat = new Intent(this, ChatActivity.class);
        Intent intentProfile = new Intent(this, profileActivity.class);

        switch (item.getItemId()) {
            case R.id.action_log_out:
                startActivity(intentMain);
                return true;
            case R.id.action_profile:
                startActivity(intentProfile);
                return true;
            case R.id.action_chat:
                startActivity(intentChat);
                return true;
        }
        return true;
    }

    public void getUserName(){
        Intent intent=getIntent();
        Bundle nameFromLogin = intent.getExtras();
        if(nameFromLogin != null)
        {
            userName= nameFromLogin.getString("name");
        }

    }

    public void getUserType(){
        Intent intent=getIntent();
        Bundle getUserTypeLogin = intent.getExtras();
        if(getUserTypeLogin != null)
        {
            usertype = getUserTypeLogin.getString("type");
        }

    }

    public void getUserId(){
        Intent intent=getIntent();
        Bundle UserIdFromLogin = intent.getExtras();
        if(UserIdFromLogin != null)
        {
            uid= UserIdFromLogin.getString("Uid");
        }

    }

    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(airplaneModeChangeReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(airplaneModeChangeReceiver);
    }

}
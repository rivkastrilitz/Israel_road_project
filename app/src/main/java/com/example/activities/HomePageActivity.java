package com.example.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.adapters.PostAdapter;
import com.example.fragments.AngelHomeFragment;
import com.example.fragments.ChatListFragment;
import com.example.fragments.profileFragment;
import com.example.model.post;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    private Button addOffer,searchOffer;
    private ActionBar actionBar;
    private TextView welcome;
    private String userName;
    private String usertype;
    private RecyclerView postsRecycle;
    private FirebaseAuth mAuth;
    private String uid;
    private List<post> postList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ////////////////////////////////////////////////
        addOffer=findViewById(R.id.addOffers);
        searchOffer=findViewById(R.id.serchOffers);
        welcome=(TextView)findViewById(R.id.TextWelcome);
        getUserName();
        welcome.setText( "welcome "+userName);

        postsRecycle = findViewById(R.id.recyclePosts);
        mAuth = FirebaseAuth.getInstance();
        uid=mAuth.getCurrentUser().getUid();

        addOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent   =new Intent(HomePageActivity.this,Angel_homePage.class);
                getUserType();
                intent.putExtra("type",usertype);
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

        //////////////////////////////////////





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
                        case R.id.nav_chat:
                            actionBar.setTitle("Chats");
                            ChatListFragment chatListFragment = new ChatListFragment();
                            FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                            ft3.replace(R.id.content, chatListFragment, "");
                            ft3.commit();
                            return true;
//                        case R.id.nav_user:
//                            return true;
                    }
                    return false;
                }
            };

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

}
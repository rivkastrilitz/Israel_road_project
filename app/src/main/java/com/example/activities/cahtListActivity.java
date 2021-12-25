package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class cahtListActivity extends AppCompatActivity {

    private String uid ,email ;
    private String usertype ,userName ,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caht_list);
        getUserType();
        getUserId();
        getUserName();
        getEmail();
        getPhoneNum();
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
        Intent intentChat = new Intent(this, cahtListActivity.class);
        Intent intentProfile = new Intent(this, profileActivity.class);

        switch (item.getItemId()) {
            case R.id.action_log_out:
                startActivity(intentMain);
                return true;
            case R.id.action_profile:
                intentProfile.putExtra("uid",uid);
                intentProfile.putExtra("type",usertype);
                intentProfile.putExtra("name",userName);
                intentProfile.putExtra("email",email);
                intentProfile.putExtra("phone",phone);
                startActivity(intentProfile);
                return true;
            case R.id.action_chat:
                startActivity(intentChat);
                return true;
        }
        return true;
    }


    public void getUserId(){
        Intent intent=getIntent();
        Bundle UserId = intent.getExtras();
        if(UserId != null)
        {
            uid= UserId.getString("uid");
        }

    }

    public void getUserName(){
        Intent intent=getIntent();
        Bundle name = intent.getExtras();
        if(name != null)
        {
            userName= name.getString("name");
        }

    }

    public void getEmail(){
        Intent intent=getIntent();
        Bundle Email = intent.getExtras();
        if(Email != null)
        {
            email= Email.getString("email");
        }

    }
    public void getPhoneNum(){
        Intent intent=getIntent();
        Bundle PhoneNum = intent.getExtras();
        if(PhoneNum != null)
        {
            phone= PhoneNum.getString("phone");
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
package com.example.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.adapters.PostAdapter;
import com.example.comperators.compareDate;
import com.example.comperators.sortByFromDate;
import com.example.model.AirplaneModeChangeReceiver;
import com.example.model.post;
import com.example.model.user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class postsFeedActivity extends AppCompatActivity {
    private RecyclerView postsRecycle;
    private DatabaseReference databaseRef;
    private FirebaseAuth mAuth;
    private List<post> postList;
    private List<String> postIdsList;
    private List<post>sortedPostList;
    private String uid ,fromDate_search;
    private String userName ,email ,phoneNum;
    private String usertype;
    AirplaneModeChangeReceiver airplaneModeChangeReceiver = new AirplaneModeChangeReceiver();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts_feed);
        mAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference();
        postsRecycle = findViewById(R.id.recyclePosts);
        uid=mAuth.getCurrentUser().getUid();
        postList=new ArrayList<>();
        postIdsList=new ArrayList<>();
        sortedPostList=new ArrayList<>();
        getUserName();
        getUserType();
        getUserId();
        getEmail();
        getPhoneNum();

        readPostFromFirebase();
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


    //todo remember to delete from firebase post that their data is dew
    private void readPostFromFirebase() {
        databaseRef.child("HostingOffer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    postList.clear();
                    sortedPostList.clear();
                    postIdsList.clear();

                    for (DataSnapshot currPost : snapshot.getChildren()) {
                        String post_id = currPost.getKey();
                        postIdsList.add(post_id);
                        post tempPost1 = currPost.getValue(post.class);
                        assert tempPost1 != null;
                        post tempPost2 = new post(tempPost1.getAddress(), tempPost1.getFromDate(), tempPost1.getToDate(),
                                tempPost1.getCapacity(), tempPost1.getRestrictions(), tempPost1.getpublisherUid(), tempPost1.getPostid(), tempPost1.getPhoneNum());
                        postList.add(tempPost2);

                    }

                    PostAdapter adapter = new PostAdapter(postsFeedActivity.this, uid);
                    getDatefromSearch();
                    SortOffersByFromDate(fromDate_search);
                    adapter.setPosts(sortedPostList);
                    adapter.setPostsIdList(postIdsList);
                    postsRecycle.setAdapter(adapter);
                    postsRecycle.setLayoutManager(new GridLayoutManager(postsFeedActivity.this, 1));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

    }


    private void SortOffersByFromDate(String from_date) {

        for (post p:postList) {
            Comparator<String> comp=new compareDate();
            int compareAns=comp.compare(p.getFromDate(),from_date);
            if(compareAns>=0){
                sortedPostList.add(p);
            }

        }
        sortedPostList.sort(new sortByFromDate());
    }


    public void getDatefromSearch(){
        Intent intent=getIntent();
        Bundle DatefromSearch = intent.getExtras();
        if(DatefromSearch != null)
        {
            fromDate_search= DatefromSearch.getString("fromdate");
        }
    }

    //todo
    private void SortOfferByLocation(List<post>list){

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
                intentProfile.putExtra("phone",phoneNum);

                startActivity(intentProfile);
                return true;
            case R.id.action_chat:
                intentChat.putExtra("uid",uid);
                intentChat.putExtra("type",usertype);
                intentChat.putExtra("name",userName);
                intentChat.putExtra("email",email);
                intentChat.putExtra("phone",phoneNum);
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
            uid= UserIdFromLogin.getString("uid");
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
            phoneNum= PhoneNum.getString("phone");
        }

    }




}
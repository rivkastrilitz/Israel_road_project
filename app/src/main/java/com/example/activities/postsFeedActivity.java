package com.example.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.adapters.PostAdapter;
import com.example.comperators.compareDate;
import com.example.comperators.sortByFromDate;
import com.example.model.post;
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
    private List<String> publisherNamesList;
    private List<post>sortedPostList;
    private String uid ,fromDate_search;




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
        publisherNamesList=new ArrayList<>();
        sortedPostList=new ArrayList<>();

        readPostFromFirebase();
    }


    //todo remember to delete from firebase post that their data is dew
    private void readPostFromFirebase(){
        databaseRef.child("HostingOffer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    postList.clear();
                    for (DataSnapshot currPost:snapshot.getChildren() ) {
                        String post_id = currPost.getKey();
                        postIdsList.add(post_id);
                        post tempPost1 =currPost.getValue(post.class);
                        assert tempPost1 != null;
                        post tempPost2=new post(tempPost1.getAddress(),tempPost1.getFromDate(),tempPost1.getToDate(),
                                tempPost1.getCapacity(),tempPost1.getRestrictions(), tempPost1.getpublisherUid(), tempPost1.getPostid(),tempPost1.getPhoneNum());
                        postList.add(tempPost2);
                        String publisherName = databaseRef.child("Users").child(tempPost2.getpublisherUid()).child("name").get().toString();
                        publisherNamesList.add(publisherName);
                    }

                    PostAdapter adapter = new PostAdapter(postsFeedActivity.this,uid);
                    //todo
//                    getDatefromSearch();
//                    SortOffersByFromDate(fromDate_search);
                    adapter.setPosts(postList);
                    adapter.setPostsIdList(postIdsList);
                    adapter.setPublishersNameList(publisherNamesList);
                    postsRecycle.setAdapter(adapter);
                    postsRecycle.setLayoutManager(new GridLayoutManager(postsFeedActivity.this,1));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("firebase"," can't read posts from firebase");
            }
        });

    }


    private void SortOffersByFromDate(String date) {
        DateFormat dateFormat =new SimpleDateFormat("dd/mm/yyyy ");
       // Collections.sort(postList,new sortByFromDate());
        Date d1 =null;
        Date d2 =null;
        for (post p:postList) {
            try {
                d1=dateFormat.parse(p.getFromDate());
                d2=dateFormat.parse(date);
                Comparator<Date> comp=new compareDate();
                int compareAns=comp.compare(d1,d2);
                if(compareAns==1 || compareAns==0){
                    sortedPostList.add(p);
                }

            }catch (ParseException e){
                e.printStackTrace();
            }


        }
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





}
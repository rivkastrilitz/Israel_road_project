package com.example.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.adapters.PostAdapter;
import com.example.model.post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class postsFeedActivity extends AppCompatActivity {
    private RecyclerView postsRecycle;
    private DatabaseReference databaseRef;
    private FirebaseAuth mAuth;
    private List<post> postList;
    private List<String> postIdsList;
    private String uid;


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

        //todo sort the post befor showing them in home page
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
                        post tempPost1 =currPost.getValue(post.class);
                        String post_id = currPost.getKey();
                        postIdsList.add(post_id);
                        post tempPost2=new post(tempPost1.getAddress(),tempPost1.getFromDate(),tempPost1.getToDate(),
                                tempPost1.getCapacity(),tempPost1.getRestrictions(), tempPost1.getpublisherUid(), tempPost1.getPostid());
                        postList.add(tempPost2);

                    }

                    PostAdapter adapter = new PostAdapter(postsFeedActivity.this,uid);
                    adapter.setPosts(postList);
                    adapter.setPostsIdList(postIdsList);
                    postsRecycle.setAdapter(adapter);
                    postsRecycle.setLayoutManager(new GridLayoutManager(postsFeedActivity.this, 1));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("firebase"," can't read posts from firebase");
            }
        });

    }

    private void SortOffersByDate(List<post> list){


    }
    private void SortOfferByLocation(List<post>list){

    }

}
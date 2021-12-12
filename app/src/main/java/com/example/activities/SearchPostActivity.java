package com.example.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.model.post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchPostActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseRef;
    private EditText date , location;
    private Button search;
    private String txtDate,txtLocation;
    private String uid;
    private List<post> postList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post);
        mAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference("HostingOffers");

        date=(EditText)findViewById(R.id.Traveler_date_input);
        location=(EditText) findViewById(R.id.Traveler_location_input);
        search=(Button)findViewById(R.id.search_in_posts);
        uid=mAuth.getCurrentUser().getUid();
        postList=new ArrayList<>();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDate=date.getText().toString();
                txtLocation=location.getText().toString();
                readPostFromFirebase();
                post p1=postList.get(0);
                Toast.makeText(SearchPostActivity.this,p1.getAddress(),Toast.LENGTH_SHORT).show();


            }
        });


    }

    private void SortOffersByDate(List<post> list){
        for (int i = 0; i < list.size(); i++) {

        }

    }
    private void SortOfferByLocation(List<post>list){

    }

    private void Swap(int i, int j,List<post>list){
        post tmppost=list.get(i);
//        list.get(i)= list.get(j);
//        list.get(j)=tmppost;

    }
    //todo remember to delete from firebase post that their data is dew
    //todo chek wat best local list or global one

    private void readPostFromFirebase(){
      databaseRef.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
              if(snapshot.exists()){
                  postList.clear();
                  for (DataSnapshot currPost:snapshot.getChildren() ) {
                      postList.add(currPost.getValue(post.class));
                  }
              }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {
            Log.e("firebase"," can't read posts from firebase");
          }
      });

    }
}
package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adapters.PostAdapter;
import com.example.model.post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ReservationsPopUpActivity extends AppCompatActivity {

    EditText reservationNum;
    Button reserve;
    int capacity=0;
    private DatabaseReference databaseRef;
    private FirebaseAuth mAuth;
    List<post> PostList;
    List<String> postIdsList;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations_pop_up);
        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width= dm.widthPixels;
        int height =dm.heightPixels;
        getWindow().setLayout((int)(width*.5),(int)(height*.5));
        reservationNum=findViewById(R.id.txtReservation);
        reserve=findViewById(R.id.reserve);
        databaseRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        getCapacity();
        getPostList();
        getPostIdList();
        getPosition();

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String reservation=reservationNum.getText().toString();
               if(ChekValidation(reservation)){
                   Intent intent =new Intent(ReservationsPopUpActivity.this,postsFeedActivity.class);
                   databaseRef.child("Reservations").child(mAuth.getCurrentUser().getUid()).setValue(reservation);

                    int numOfReservation=Integer.parseInt(reservation);
                    int updateCapacity=capacity-numOfReservation;
                    //update capacity in list
                    PostList.get(position).setCapacity(updateCapacity);
                    if(updateCapacity>=0){
                    databaseRef.child("HostingOffer").child(postIdsList.get(position)).child("capacity").setValue(updateCapacity);
                    }else{
                    Toast.makeText(ReservationsPopUpActivity.this,"sorry we are full,you may search for a different Angel", Toast.LENGTH_SHORT).show();
                    }

                   startActivity(intent);
                   finish();
               }

            }
        });


    }


    public boolean ChekValidation(String reservation){

        if(Integer.parseInt(reservation)>capacity){
            reservationNum.setError("cant reserve more then the capacity");
            reservationNum.requestFocus();
            return false;
        }

        return true;
    }

    public void getCapacity(){
        Intent intent=getIntent();
        Bundle capacityFromPostFeed = intent.getExtras();
        if(capacityFromPostFeed != null)
        {
            capacity= capacityFromPostFeed.getInt("capacity");
        }

    }
    public void getPostList(){
        Intent intent=getIntent();
        Bundle PostListFromPostFeed = intent.getExtras();
        if(PostListFromPostFeed != null)
        {
            PostList= (List<post>) PostListFromPostFeed.get("postList");
        }

    }

    public void getPostIdList(){
        Intent intent=getIntent();
        Bundle PostIdListFromPostFeed = intent.getExtras();
        if(PostIdListFromPostFeed != null)
        {
            postIdsList= (List<String>) PostIdListFromPostFeed.get("postIdList");
        }

    }

    public void getPosition(){
        Intent intent=getIntent();
        Bundle PositionFromPostFeed = intent.getExtras();
        if(PositionFromPostFeed != null)
        {
            position= PositionFromPostFeed.getInt("position");
        }

    }
}
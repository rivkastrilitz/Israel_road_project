package com.example.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReservationsPopUpActivity extends AppCompatActivity {

    EditText reservationNum;
    Button reserve;
    int capacity=0;
    private DatabaseReference databaseRef;
    private FirebaseAuth mAuth;
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

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ChekValidation(reservation);
               String reservation=reservationNum.getText().toString();
               Intent intent =new Intent(ReservationsPopUpActivity.this,postsFeedActivity.class);
                databaseRef.child("Reservations").child(mAuth.getCurrentUser().getUid()).setValue(reservation);
                startActivity(intent);
            }
        });


    }

    //todo check why not working
    public void ChekValidation(String reservation){

        if(Integer.parseInt(reservation)>capacity){
            reservationNum.setError("cant reserve more then the capacity");
            reservationNum.requestFocus();
            return;
        }



    }

    public void getCapacity(){
        Intent intent=getIntent();
        Bundle capacityFromPostFeed = intent.getExtras();
        if(capacityFromPostFeed != null)
        {
            capacity= capacityFromPostFeed.getInt("capacity");
        }

    }
}
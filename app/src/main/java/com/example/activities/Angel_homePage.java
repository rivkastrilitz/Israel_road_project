package com.example.activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.Toast;

import com.example.fragments.AngelHomeFragment;
import com.example.fragments.profileFragment;
import com.example.model.AirplaneModeChangeReceiver;
import com.example.model.post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.time.Year;
import java.util.Calendar;
import java.util.HashMap;

public class Angel_homePage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseRef;

    private String uid ,email ;
    private String usertype ,userName ,phone;
    private EditText AngelAddress,capacity,restrictions,phoneNum;
    private Button createOffer ,fromDate,toDate ,back ;
    private DatePickerDialog datePickerDialog;
    private ActionBar actionBar;
    AirplaneModeChangeReceiver airplaneModeChangeReceiver = new AirplaneModeChangeReceiver();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angel_home_page);
        mAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference();


        AngelAddress = (EditText) findViewById(R.id.post_address);
        fromDate = (Button) findViewById(R.id.fromdatePickerButton);
        toDate = (Button) findViewById(R.id.todatePickerButton);
        capacity = (EditText) findViewById(R.id.post_capacity);
        restrictions = (EditText) findViewById(R.id.post_restrictions);
        phoneNum = (EditText) findViewById(R.id.post_phoneNum);
        createOffer = (Button) findViewById(R.id.createOffer);
        back=(Button)findViewById(R.id.back);

        initDatePicker();
        fromDate.setText(getTodaysDate());
        toDate.setText(getTodaysDate());
        getUserType();
        getUserId();
        getUserName();
        getEmail();
        getPhoneNum();


        createOffer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String txtAngelAddress = AngelAddress.getText().toString().trim();

                String txtFromDate = fromDate.getText().toString();
                String txtToDate = toDate.getText().toString();
                int txtCapacity = Integer.parseInt(capacity.getText().toString());
                String txtRestrictions = restrictions.getText().toString();
                String txtPhoneNum = phoneNum.getText().toString();

                if (checkValidation(txtAngelAddress, txtFromDate, txtToDate, txtCapacity, txtPhoneNum)) {

                    if (usertype.equals("Angel")) {
                        addOffer(txtAngelAddress, txtFromDate, txtToDate, txtCapacity, txtRestrictions, txtPhoneNum);
                    } else {
                        Toast.makeText(Angel_homePage.this, "Traveler cant add Hosting offer", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Angel_homePage.this,HomePageActivity.class);
                intent.putExtra("uid",uid);
                intent.putExtra("type",usertype);
                intent.putExtra("name",userName);
                intent.putExtra("email",email);
                intent.putExtra("phone",phone);
                startActivity(intent);

            }
        });


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


    private void addOffer(final String address, final String fromdate, String todate,final int capacity,final String restrictions,final  String phoneNum) {
        getUserId();
        post newpost=new post(address,fromdate,todate,capacity,restrictions,uid,"",phoneNum);
        databaseRef.child("HostingOffer").push().setValue(newpost).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(Angel_homePage.this, HomePageActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(Angel_homePage.this, "Your offer successfully Registered", Toast.LENGTH_SHORT).show();
                    intent.putExtra("uid",uid);
                    intent.putExtra("type",usertype);
                    intent.putExtra("name",userName);
                    intent.putExtra("email",email);
                    intent.putExtra("phone",phone);
                    startActivity(intent);
                    finish();

                }
            }

        });


    }

    private boolean checkValidation(final String txtAngelAddress,final String txtFromDate,final String txtToDate,final int txtCapacity ,final String txtPhoneNum){

        if(txtAngelAddress.isEmpty())
        {
            AngelAddress.setError("address is empty");
            AngelAddress.requestFocus();
            return false;

        }
        if(txtCapacity==0)
        {
            capacity.setError("capacity is empty");
            capacity.requestFocus();
            return false;

        }
        if(txtFromDate.isEmpty() || txtToDate.isEmpty())
        {
            fromDate.setError("date is empty");
            fromDate.requestFocus();
            return false;

        }

        if(txtPhoneNum.isEmpty()) {
            phoneNum.setError("you must add contact");
            fromDate.requestFocus();
            return false;
        }
        if(txtPhoneNum.length()<11){
            phoneNum.setError("phone number must be a 10 digits number");
            fromDate.requestFocus();
            return false;
        }



        String [] arrFromDate=txtFromDate.split("/");
        String [] arrtoDate=txtFromDate.split("/");
        Year curryear = Year.now();
        if(Integer.parseInt(arrFromDate[2])<curryear.getValue() || Integer.parseInt(arrtoDate[2])<curryear.getValue()){
            fromDate.setError("this year past");
            fromDate.requestFocus();
            return false;
        }

        return true;

    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private String makeDateString(int day, int month, int year) {
        return day + "/" + month + "/" + year;
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                fromDate.setText(date);
                toDate.setText(date);
            }
        };


        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

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
                intentChat.putExtra("uid",uid);
                intentChat.putExtra("type",usertype);
                intentChat.putExtra("name",userName);
                intentChat.putExtra("email",email);
                intentChat.putExtra("phone",phone);
                startActivity(intentChat);
                return true;
        }
        return true;
    }

}
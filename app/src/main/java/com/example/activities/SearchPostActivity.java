package com.example.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adapters.PostAdapter;
import com.example.model.AirplaneModeChangeReceiver;
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
import java.util.Calendar;
import java.util.List;

public class SearchPostActivity extends AppCompatActivity {

    private EditText location;
    private Button search ,back,fromdate;
    private String txtFromDate,txtLocation;
    private DatePickerDialog datePickerDialog;
    private String userName ,email ,phoneNum;
    private String usertype ,uid;
    AirplaneModeChangeReceiver airplaneModeChangeReceiver = new AirplaneModeChangeReceiver();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post);
        fromdate=(Button) findViewById(R.id.fromdatePickerButton);

        location=(EditText) findViewById(R.id.Traveler_location_input);
        search=(Button)findViewById(R.id.search_in_posts);
        back=(Button)findViewById(R.id.Backbtn);

        initDatePicker();
        fromdate.setText(getTodaysDate());

        getUserName();
        getUserType();
        getUserId();
        getEmail();
        getPhoneNum();



        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtFromDate=fromdate.getText().toString();
                txtLocation=location.getText().toString();
                Intent intent =new Intent(SearchPostActivity.this,postsFeedActivity.class);
                intent.putExtra("fromdate",txtFromDate);
                intent.putExtra("type", usertype);
                intent.putExtra("uid", uid);
                intent.putExtra("email", email);
                intent.putExtra("name",userName);
                intent.putExtra("phone",phoneNum);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SearchPostActivity.this,HomePageActivity.class);
                intent.putExtra("type", usertype);
                intent.putExtra("uid", uid);
                intent.putExtra("email", email);
                intent.putExtra("name",userName);
                intent.putExtra("phone",phoneNum);
                startActivity(intent);
            }
        });


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
                fromdate.setText(date);

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
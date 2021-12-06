package com.example.activities;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragments.AngelHomeFragment;
import com.example.fragments.profileFragment;
import com.example.model.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.time.Year;
import java.util.HashMap;

public class Angel_homePage extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseRef;

    private String uid = "";
    private EditText AngelAddress,fromDate,toDate,capacity,restrictions;
    private Button createOffer,deleteOffer ;
    private ActionBar actionBar;

    static int offerKey=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_angel_home_page);
        mAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference();


        AngelAddress=(EditText)findViewById(R.id.post_address);
        fromDate=(EditText)findViewById(R.id.post_fromDate);
        toDate=(EditText)findViewById(R.id.post_toDate);
        capacity=(EditText)findViewById(R.id.post_capacity);
        restrictions=(EditText)findViewById(R.id.post_restrictions);
        createOffer=(Button)findViewById(R.id.createOffer);
        deleteOffer=(Button)findViewById(R.id.deleteOffer);


        createOffer.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String txtAngelAddress = AngelAddress.getText().toString().trim();
                String txtFromDate=fromDate.getText().toString();
                String txtToDate=toDate.getText().toString();
                String txtCapacity=capacity.getText().toString();
                String txtRestrictions=restrictions.getText().toString();

                if(checkValidation(txtAngelAddress,txtFromDate,txtToDate,txtCapacity)) {
                    addOffer(txtAngelAddress, txtFromDate, txtToDate, txtCapacity, txtRestrictions);
                }

            }
        });

        //todo override the back button
//        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
//            @Override
//            public void handleOnBackPressed() {
//                Intent intent = new Intent(Angel_homePage.this, HomePageActivity.class);
//                startActivity(intent);
//                finish();
//
//            }
//        };




        actionBar = getSupportActionBar();

        BottomNavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setOnItemSelectedListener(selectedListener);

        actionBar.setTitle("Create Hosting Offer");
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
//                        case R.id.nav_user:
//                            return true;
                    }
                    return false;
                }
            };

    //todo delete if redundant
    public void getAngelUid(){
        Intent intent=getIntent();
        Bundle UidFromLogin = intent.getExtras();
        if(UidFromLogin != null)
        {
            uid = UidFromLogin.getString("Uid");
        }

    }

    private void addOffer(final String address, final String fromdate, String todate,final String capacity,final String restrictions) {


        HashMap<String, Object> map = new HashMap<>();
        map.put("address", address);
        map.put("fromDate", fromdate);
        map.put("toDate", todate);
        map.put("capacity",capacity );
        map.put("restrictions and notes",restrictions );

        String offkey =Integer.toString(offerKey);
        databaseRef.child("HostingOffer").child(mAuth.getCurrentUser().getUid()).child("offer"+offkey).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    offerKey++;
                    Intent intent = new Intent(Angel_homePage.this, HomePageActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(Angel_homePage.this, "Your offer successfully Registered", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();

                }
            }

        });


    }

    private boolean checkValidation(final String txtAngelAddress,final String txtFromDate,final String txtToDate,final String txtCapacity){

        if(txtAngelAddress.isEmpty())
        {
            AngelAddress.setError("address is empty");
            AngelAddress.requestFocus();
            return false;

        }
        if(txtCapacity.isEmpty())
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

}
package com.example.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.model.post;
import com.example.model.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditUserDetailsActivity extends AppCompatActivity {

    private Button edit ;
    private EditText new_name,new_phone;
    private Spinner new_type;
    private DatabaseReference databaseRef;
    private String uid ,email;
    private String txtNewType,txtNewName,txtNewPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_details);
        edit =findViewById(R.id.edit);
        new_name=findViewById(R.id.edit_name);
        new_phone=findViewById(R.id.edit_phone);
        new_type=findViewById(R.id.edit_UserType);
        databaseRef = FirebaseDatabase.getInstance().getReference();
        getEmail();
        getUserId();
        getPhoneNum();
        getUserName();
        getUserType();
        ArrayAdapter<String> myAdapter=new ArrayAdapter<String>(EditUserDetailsActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Type));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        new_type.setAdapter(myAdapter);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtNewName=new_name.getText().toString().trim();
                txtNewPhone=new_phone.getText().toString().trim();
                txtNewType=new_type.getSelectedItem().toString().trim();

                editInFireBase(txtNewName,txtNewPhone,txtNewType);

                Intent intent=new Intent(EditUserDetailsActivity.this,profileActivity.class);
                intent.putExtra("type", txtNewType);
                intent.putExtra("uid", uid);
                intent.putExtra("email", email);
                intent.putExtra("name",txtNewName);
                intent.putExtra("phone",txtNewPhone);
                startActivity(intent);



            }
        });
    }

    private void editInFireBase(final String newName,final String newPhone,final String newType){
        databaseRef.child("Users").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    user cure_user =  task.getResult().getValue(user.class);
                    //change the details in user
                    user updatedUser=new user(uid,newName,email,newType,newPhone);

                    databaseRef.child("Users").child(uid).setValue(updatedUser).addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                            }
                        }
                    });

                }
            }
        });
    }





    public void getUserName(){
        Intent intent=getIntent();
        Bundle nameFromLogin = intent.getExtras();
        if(nameFromLogin != null)
        {
            txtNewName= nameFromLogin.getString("name");
        }

    }

    public void getUserType(){
        Intent intent=getIntent();
        Bundle getUserTypeLogin = intent.getExtras();
        if(getUserTypeLogin != null)
        {
            txtNewType = getUserTypeLogin.getString("type");
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
            txtNewPhone= PhoneNum.getString("phone");
        }

    }


}
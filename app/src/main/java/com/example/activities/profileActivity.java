package com.example.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.model.user;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class profileActivity extends AppCompatActivity {


    private String userName;
    private String usertype;
    private String uid;
    private TextView txtVname ,txtVphone,txtVemail;
    private DatabaseReference databaseRef;
    private List<user> usersList;
    private StorageReference mStorgeRef;
    private ImageView img;
    public Uri imguri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        txtVname=findViewById(R.id.username);
        txtVphone=findViewById(R.id.phoneToOverride);
        txtVemail=findViewById(R.id.emailToOverride);
        img =findViewById(R.id.avatarIv);
        getUserId();
        getUserName();
        databaseRef = FirebaseDatabase.getInstance().getReference();
        mStorgeRef = FirebaseStorage.getInstance().getReference().child("profile_pic/"+uid);
        usersList=new ArrayList<>();

        txtVname.setText(userName);

//        ReadNamesFromFirebase();
//
//        for (user u:usersList) {
//           if(u.getUid()==uid){
//              txtVemail.setText(u.getEmail());
//
//           }
//        }


        mStorgeRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    final File localFile = File.createTempFile(uid,"");
                    mStorgeRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    final Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                    img.setImageBitmap(bitmap);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
                catch (IOException e){

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // File not found

            }
        });




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
        Intent intentChat = new Intent(this, ChatActivity.class);
        Intent intentProfile = new Intent(this, profileActivity.class);

        switch (item.getItemId()) {
            case R.id.action_log_out:
                startActivity(intentMain);
                return true;
            case R.id.action_profile:
                startActivity(intentProfile);
                return true;
            case R.id.action_chat:
                startActivity(intentChat);
                return true;
        }
        return true;
    }

    public void getUserName(){
        Intent intent=getIntent();
        Bundle nameFromHomePage = intent.getExtras();
        if(nameFromHomePage != null)
        {
            userName= nameFromHomePage.getString("name");
        }

    }

    public void getUserId(){
        Intent intent=getIntent();
        Bundle UserIdHomePage = intent.getExtras();
        if(UserIdHomePage != null)
        {
            uid= UserIdHomePage.getString("uid");
        }

    }


    private void ReadNamesFromFirebase(){

        databaseRef.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot currUser:snapshot.getChildren()) {
                    user tempUser=currUser.getValue(user.class);
                    assert tempUser!=null;
                    user tempUserToList=new user(tempUser.getUid(),tempUser.getName(),tempUser.getEmail(),tempUser.getType());
                    usersList.add(tempUserToList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void imageClick(View view) {
        Intent intent =new Intent(profileActivity.this,EditProfileActiviry.class);
        intent.putExtra("uid",uid);
        intent.putExtra("name",userName);
        startActivity(intent);
    }


}
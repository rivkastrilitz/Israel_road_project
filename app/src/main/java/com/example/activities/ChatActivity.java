package com.example.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapters.ChatAdapter;
import com.example.model.chat;
import com.example.model.post;
import com.example.model.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ChatActivity extends AppCompatActivity {


    Toolbar toolbar;
    RecyclerView recyclerView;
    ImageView profileIv;
    TextView nameTv, userStatusTv;
    EditText messegeEt;
    ImageButton sendBtn;


    FirebaseDatabase firebaseDatabase;


    ValueEventListener seenListener;
    DatabaseReference userRefForSeen;

    List<chat> chatList;
    List<String> chatIdList;
    ChatAdapter chatAdapter;

    String hisUid;
    String myUid;
    String image;
    String userName ,email ,phoneNum ,usertype;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        recyclerView = findViewById(R.id.chat_recyclerView);
        profileIv = findViewById(R.id.profileIv);
        nameTv =  findViewById(R.id.nameTv);
        userStatusTv =  findViewById(R.id.userStatusTv);
        messegeEt =  findViewById(R.id.messegeEt);
        sendBtn =  findViewById(R.id.sendBtn);

        getPublisherUid();
        getUserId();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);


//        Intent intent = getIntent();
//        hisUid = intent.getStringExtra("hisUid");


        firebaseDatabase = FirebaseDatabase.getInstance();



       //read publisher name
        firebaseDatabase.getReference().child("Users").child(hisUid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>(){

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {

                    user cure_user = task.getResult().getValue(user.class);
                        nameTv.setText(cure_user.getName());


                }
            }
        });


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messegeEt.getText().toString().trim();
                if(TextUtils.isEmpty(message)){
                    Toast.makeText(ChatActivity.this,"cant send empty message",Toast.LENGTH_SHORT).show();
                }
                else {
                    sendMessage(message);
                }

                readMessages();
                seenMessage();

            }

            private void seenMessage() {
              //  userRefForSeen = FirebaseDatabase.getInstance().getReference("Chats");
                firebaseDatabase.getReference().child("Chats").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                chat c = ds.getValue(chat.class);
                                chat newc=new chat(c.getMessage(),c.getReciver(),c.getSender(),c.getTimestamp(),c.isSeen(),c.getChatId());
                                assert newc != null;
                                if (newc.getReciver().equals(myUid) && newc.getSender().equals(hisUid)) {

                                    int i;
                                    for (i = 0; i < chatList.size(); i++) {
                                        if(chatList.get(i).getTimestamp().equals(newc.getTimestamp())){
                                           break;
                                        }
                                    }
                                    newc.setSeen(true);
                                    firebaseDatabase.getReference().child("Chats").child(chatIdList.get(i)).setValue(newc).addOnCompleteListener(new OnCompleteListener<Void>() {

                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                            }
                                        }
                                    });

                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            private void readMessages() {
                chatList = new ArrayList<>();
                chatIdList= new ArrayList<>();

                firebaseDatabase.getReference().child("Chats").addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            chatList.clear();
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                chat c = ds.getValue(chat.class);
                                //todo

                                chat newc=new chat(c.getMessage(),c.getReciver(),c.getSender(),c.getTimestamp(),c.isSeen(),c.getChatId());
                                assert newc != null;
                                if (newc.getReciver().equals(myUid) && newc.getSender().equals(hisUid) ||
                                        newc.getReciver().equals(hisUid) && newc.getSender().equals(myUid)) {
                                    chatList.add(newc);
                                    chatIdList.add(ds.getKey());

                                }

                            }

                            chatAdapter = new ChatAdapter(ChatActivity.this, chatList, image);
                            chatAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(chatAdapter);
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            private void sendMessage(String message) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                String timestamp = String.valueOf(System.currentTimeMillis());
                chat newChat= new chat(message,hisUid,myUid,timestamp,false,"");
                databaseReference.child("Chats").push().setValue(newChat);
                //reset after sending
                messegeEt.setText("");

            }
        });



    }



    @Override
    protected void onStart() {
        //checkUserStatus();
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        userRefForSeen.removeEventListener(seenListener);
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
            myUid= UserIdFromLogin.getString("uid");
        }

    }

    public void getPublisherUid(){
        Intent intent=getIntent();
        Bundle PublisherUid = intent.getExtras();
        if(PublisherUid != null)
        {
            hisUid= PublisherUid.getString("publisheruid");
        }

    }


}
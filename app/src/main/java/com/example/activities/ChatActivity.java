package com.example.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference userDbRef;

    ValueEventListener seenListener;
    DatabaseReference userRefForSeen;

    List<chat> chatList;
    ChatAdapter chatAdapter;

    String hisUid;
    String myUid;
    String image;



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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        Intent intent = getIntent();
        hisUid = intent.getStringExtra("hisUid");

        firebaseAuth =FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        userDbRef = firebaseDatabase.getReference("Users");

        Query userQuery = userDbRef.orderByChild("uid").equalTo(hisUid);

        //get user pic and name
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren() ){
                    String name = ""+ds.child("name").getValue();
                    image = ""+ds.child("image").getValue();


                    nameTv.setText(name);
                    try {
                        Picasso.get().load(image).placeholder(R.drawable.ic_default_img).into(profileIv);
                    }
                    catch (Exception e){
                        Picasso.get().load(R.drawable.ic_default_img).into(profileIv);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                userRefForSeen = FirebaseDatabase.getInstance().getReference("Chats");
                seenListener = userRefForSeen.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()){
                                    chat c = ds.getValue(chat.class);
                                    assert c!= null;
                                    if(c.getReciver().equals(myUid) && c.getSender().equals(hisUid)){
                                        HashMap<String, Object> hasSeenHashMap = new HashMap<>();
                                hasSeenHashMap.put("isSeen", true);
                                ds.getRef().updateChildren(hasSeenHashMap);
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
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        chatList.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            chat c = ds.getValue(chat.class);
                            assert c!= null;
                            if(c.getReciver().equals(myUid) && c.getSender().equals(hisUid) ||
                                    c.getReciver().equals(hisUid) && c.getSender().equals(myUid)){
                                chatList.add(c);
                            }

                            chatAdapter = new ChatAdapter(ChatActivity.this, chatList,image);
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
                HashMap<String,Object> hashMap = new HashMap<>();

                hashMap.put("sender", myUid);
                hashMap.put("reciver", hisUid);
                hashMap.put("message",message);
                hashMap.put("timestamp",timestamp);
                hashMap.put("isSeen",false);

                databaseReference.child("Chats").push().setValue(hashMap);


                //reset after sending
                messegeEt.setText("");

            }
        });



    }
    private void checkUserStatus() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {

            myUid = user.getUid();

        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }


    @Override
    protected void onStart() {
        checkUserStatus();
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        userRefForSeen.removeEventListener(seenListener);
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
}
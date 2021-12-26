package com.example.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.adapters.ChatListAdapter;
import com.example.model.chatList;
import com.example.model.user;
import com.example.model.chat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class cahtListActivity extends AppCompatActivity {

    private String uid ,email ;
    private String usertype ,userName ,phone;
    RecyclerView recyclerView;
    List<chatList> chatlistList;
    List<user> userList;
    DatabaseReference reference;
    FirebaseDatabase firebaseDatabase;
    ChatListAdapter chatListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caht_list);
        recyclerView=findViewById(R.id.recyclerView);
        getUserType();
        getUserId();
        getUserName();
        getEmail();
        getPhoneNum();
        firebaseDatabase = FirebaseDatabase.getInstance();
        chatlistList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(uid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    chatlistList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        chatList cl = ds.getValue(chatList.class);
                        chatList newcl = new chatList(cl.getId());
                        assert newcl != null;
                        chatlistList.add(newcl);

                    }

                    loadChats();
                }
            }

            private void loadChats() {
                userList = new ArrayList<>();
                firebaseDatabase.getReference().child("Users").addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            userList.clear();
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                user user = ds.getValue(user.class);
                                user newuser = new user(user.getUid(),user.getName(),user.getEmail(),user.getType(),user.getPhoneNum());
                                for (chatList chatList: chatlistList){
                                    if(newuser.getUid() != null && newuser.getUid().equals(chatList.getId())){
                                        userList.add(newuser);
                                        break;
                                    }
                                }

                            chatListAdapter = new ChatListAdapter(cahtListActivity.this,userList);
                            recyclerView.setAdapter(chatListAdapter);
                                for (int i = 0; i < userList.size(); i++) {
                                    lastMessage(userList.get(i).getUid());

                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void lastMessage(String userid) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String theLastMessage = "default";
                for (DataSnapshot ds : snapshot.getChildren()) {
                    chat chat = ds.getValue(chat.class);
                    if (chat == null){
                        continue;
                    }
                    String sender = chat.getSender();
                    String reciver = chat.getReciver();
                    if (sender == null || reciver == null){
                        continue;
                    }
                    if (chat.getReciver().equals(uid) && chat.getSender().equals(userid)
                    ||chat.getReciver().equals(userid) && chat.getSender().equals(uid) ){
                        theLastMessage = chat.getMessage();
                    }

                }

                chatListAdapter.setLastMessageMap(userid, theLastMessage);
                chatListAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
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
                startActivity(intentChat);
                return true;
        }
        return true;
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
}
package com.example.model;

import com.google.firebase.auth.FirebaseAuth;

public class user {

    private String name;
    private String email;
    private String Uid;
    private String type;
    private String phoneNum;

    public user(String id , String name , String email , String type,String phoneNum){
        this.Uid=id;
        this.name=name;
        this.email=email;
        this.type=type;
        this.phoneNum=phoneNum;


    }
    public user(){

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) { this.name=name; }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) { this.email=email; }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return Uid;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }
}

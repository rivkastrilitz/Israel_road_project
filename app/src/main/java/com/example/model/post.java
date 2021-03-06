package com.example.model;

import java.util.HashMap;

public class post {


    private String description;
    private String imageurl;

    private String postid,address,fromDate,toDate,restrictions,publisherUid,phoneNum;
    private int capacity;

    public post() {
    }

//    public post(String description, String imageurl, String postid, String publisher) {
//        this.description = description;
//        this.imageurl = imageurl;
//        this.postid = postid;
//        this.publisherUid = publisher;
//    }

    public post(String Address,String fromDate,String toDate,int capacity,String restrictions,String userId,String postid,String phoneNum){
        this.address=Address;
        this.fromDate=fromDate;
        this.toDate=toDate;
        this.capacity=capacity;
        this.restrictions=restrictions;
        this.publisherUid=userId;
        this.postid=postid;
        this.phoneNum=phoneNum;

    }
    //copy constructor
    public post(post p){
        this.address=p.getAddress();
        this.fromDate=p.getFromDate();
        this.toDate=p.getToDate();
        this.capacity=p.getCapacity();
        this.restrictions=p.getRestrictions();
        this.publisherUid=p.getpublisherUid();
        this.phoneNum= p.phoneNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getPostid() {
        return postid;
    }

    public String getAddress() {
        return address;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public String getToDate() {
        return toDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getpublisherUid() {
        return this.publisherUid;
    }

    public String getPhoneNum(){return this.phoneNum;}

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
